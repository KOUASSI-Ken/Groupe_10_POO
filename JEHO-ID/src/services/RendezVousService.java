package services;

import models.RendezVous;
import models.Patient;
import models.Medecin;
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RendezVousService {
    private List<RendezVous> rendezVous;
    private int prochainId;
    
    // Heures de travail : 8h-18h du lundi au vendredi
    private static final LocalTime HEURE_DEBUT = LocalTime.of(8, 0);
    private static final LocalTime HEURE_FIN = LocalTime.of(18, 0);
    private static final int DUREE_RDV_MINUTES = 30; // 30 minutes par rendez-vous
    
    public RendezVousService() {
        this.rendezVous = new ArrayList<>();
        this.prochainId = 1;
    }
    
    // Créer un rendez-vous avec vérification des disponibilités
    public RendezVous creerRendezVous(Patient patient, Medecin medecin, LocalDateTime dateHeure, String motif) {
        if (!verifierDisponibilite(medecin, dateHeure)) {
            throw new IllegalArgumentException("Le créneau n'est pas disponible pour ce médecin.");
        }
        
        RendezVous rdv = new RendezVous(prochainId++, patient, medecin, dateHeure, motif);
        rendezVous.add(rdv);
        return rdv;
    }
    
    // Vérifier la disponibilité d'un médecin à une date/heure donnée
    public boolean verifierDisponibilite(Medecin medecin, LocalDateTime dateHeure) {
        // Vérifier si c'est un jour ouvrable
        if (!estJourOuvrable(dateHeure)) {
            return false;
        }
        
        // Vérifier si c'est pendant les heures de travail
        if (!estHeuresDeTravail(dateHeure)) {
            return false;
        }
        
        // Vérifier si le médecin n'a pas déjà un rendez-vous à cette heure
        for (RendezVous rdv : rendezVous) {
            if (rdv.getMedecin().getId() == medecin.getId() && rdv.estDisponible()) {
                if (sontChevauchants(dateHeure, rdv.getDateHeure())) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    // Obtenir les créneaux disponibles pour un médecin à une date donnée
    public List<LocalDateTime> getCreneauxDisponibles(Medecin medecin, LocalDateTime date) {
        List<LocalDateTime> creneaux = new ArrayList<>();
        LocalDateTime debut = date.withHour(8).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime fin = date.withHour(18).withMinute(0).withSecond(0).withNano(0);
        
        LocalDateTime courant = debut;
        while (courant.isBefore(fin)) {
            if (verifierDisponibilite(medecin, courant)) {
                creneaux.add(courant);
            }
            courant = courant.plusMinutes(DUREE_RDV_MINUTES);
        }
        
        return creneaux;
    }
    
    // Obtenir les rendez-vous d'un médecin
    public List<RendezVous> getRendezVousMedecin(Medecin medecin) {
        List<RendezVous> rdvs = new ArrayList<>();
        for (RendezVous rdv : rendezVous) {
            if (rdv.getMedecin().getId() == medecin.getId()) {
                rdvs.add(rdv);
            }
        }
        return rdvs;
    }
    
    // Obtenir les rendez-vous d'un patient
    public List<RendezVous> getRendezVousPatient(Patient patient) {
        List<RendezVous> rdvs = new ArrayList<>();
        for (RendezVous rdv : rendezVous) {
            if (rdv.getPatient().getId() == patient.getId()) {
                rdvs.add(rdv);
            }
        }
        return rdvs;
    }
    
    // Annuler un rendez-vous
    public boolean annulerRendezVous(int id) {
        RendezVous rdv = rechercherRendezVous(id);
        if (rdv != null && rdv.estDisponible()) {
            rdv.annuler();
            return true;
        }
        return false;
    }
    
    // Confirmer un rendez-vous
    public boolean confirmerRendezVous(int id) {
        RendezVous rdv = rechercherRendezVous(id);
        if (rdv != null && "PROGRAMMÉ".equals(rdv.getStatut())) {
            rdv.confirmer();
            return true;
        }
        return false;
    }
    
    // Rechercher un rendez-vous par ID
    public RendezVous rechercherRendezVous(int id) {
        for (RendezVous rdv : rendezVous) {
            if (rdv.getId() == id) {
                return rdv;
            }
        }
        return null;
    }
    
    // Obtenir tous les rendez-vous
    public List<RendezVous> getTousLesRendezVous() {
        return new ArrayList<>(rendezVous);
    }
    
    // Méthodes utilitaires privées
    private boolean estJourOuvrable(LocalDateTime date) {
        DayOfWeek jour = date.getDayOfWeek();
        return jour != DayOfWeek.SATURDAY && jour != DayOfWeek.SUNDAY;
    }
    
    private boolean estHeuresDeTravail(LocalDateTime date) {
        LocalTime heure = date.toLocalTime();
        return !heure.isBefore(HEURE_DEBUT) && !heure.isAfter(HEURE_FIN.minusMinutes(DUREE_RDV_MINUTES));
    }
    
    private boolean sontChevauchants(LocalDateTime rdv1, LocalDateTime rdv2) {
        LocalDateTime fin1 = rdv1.plusMinutes(DUREE_RDV_MINUTES);
        LocalDateTime fin2 = rdv2.plusMinutes(DUREE_RDV_MINUTES);
        
        return rdv1.isBefore(fin2) && rdv2.isBefore(fin1);
    }
    
    // Statistiques
    public int getNombreRendezVous() {
        return rendezVous.size();
    }
    
    public int getRendezVousDuJour() {
        LocalDateTime aujourdHui = LocalDateTime.now();
        int count = 0;
        for (RendezVous rdv : rendezVous) {
            if (rdv.getDateHeure().toLocalDate().equals(aujourdHui.toLocalDate())) {
                count++;
            }
        }
        return count;
    }
}
