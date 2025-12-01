package models;

import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class EmploiDuTemps {
    private Medecin medecin;
    private List<Creneau> creneaux;
    
    public EmploiDuTemps(Medecin medecin) {
        this.medecin = medecin;
        this.creneaux = new ArrayList<>();
        genererEmploiDuTempsParDefaut();
    }
    
    private void genererEmploiDuTempsParDefaut() {
        // Générer des créneaux pour la semaine (lundi-vendredi, 8h-18h)
        for (DayOfWeek jour : DayOfWeek.values()) {
            if (jour == DayOfWeek.SATURDAY || jour == DayOfWeek.SUNDAY) continue;
            
            for (int heure = 8; heure < 18; heure++) {
                for (int minute = 0; minute < 60; minute += 30) {
                    LocalDateTime debut = LocalDateTime.now()
                        .with(jour)
                        .withHour(heure)
                        .withMinute(minute)
                        .withSecond(0)
                        .withNano(0);
                    
                    creneaux.add(new Creneau(debut, debut.plusMinutes(30), false));
                }
            }
        }
    }
    
    public List<Creneau> getCreneauxDisponibles() {
        List<Creneau> disponibles = new ArrayList<>();
        for (Creneau creneau : creneaux) {
            if (!creneau.isOccupe()) {
                disponibles.add(creneau);
            }
        }
        return disponibles;
    }
    
    public List<Creneau> getCreneauxJour(LocalDateTime date) {
        List<Creneau> creneauxJour = new ArrayList<>();
        for (Creneau creneau : creneaux) {
            if (creneau.getDebut().toLocalDate().equals(date.toLocalDate())) {
                creneauxJour.add(creneau);
            }
        }
        return creneauxJour;
    }
    
    public boolean reserverCreneau(LocalDateTime dateHeure) {
        for (Creneau creneau : creneaux) {
            if (creneau.getDebut().equals(dateHeure) && !creneau.isOccupe()) {
                creneau.setOccupe(true);
                return true;
            }
        }
        return false;
    }
    
    public void libererCreneau(LocalDateTime dateHeure) {
        for (Creneau creneau : creneaux) {
            if (creneau.getDebut().equals(dateHeure)) {
                creneau.setOccupe(false);
                break;
            }
        }
    }
    
    // Getters
    public Medecin getMedecin() { return medecin; }
    public List<Creneau> getCreneaux() { return new ArrayList<>(creneaux); }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Emploi du temps du Dr ").append(medecin.getNom()).append(" ").append(medecin.getPrenom()).append("\n");
        
        for (DayOfWeek jour : DayOfWeek.values()) {
            if (jour == DayOfWeek.SATURDAY || jour == DayOfWeek.SUNDAY) continue;
            
            sb.append("\n").append(jour).append(" :\n");
            List<Creneau> creneauxJour = getCreneauxJour(LocalDateTime.now().with(jour));
            
            for (Creneau creneau : creneauxJour) {
                String statut = creneau.isOccupe() ? " [OCCUPÉ]" : " [LIBRE]";
                sb.append("  ").append(creneau.getDebut().toLocalTime())
                  .append(" - ").append(creneau.getFin().toLocalTime())
                  .append(statut).append("\n");
            }
        }
        
        return sb.toString();
    }
}
