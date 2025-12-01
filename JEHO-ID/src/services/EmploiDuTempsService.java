package services;

import models.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmploiDuTempsService {
    private Map<Integer, EmploiDuTemps> emploiDuTempsMedecins;
    private Map<Integer, EmploiDuTempsInfirmier> emploiDuTempsInfirmiers;
    
    public EmploiDuTempsService() {
        this.emploiDuTempsMedecins = new HashMap<>();
        this.emploiDuTempsInfirmiers = new HashMap<>();
    }
    
    public void creerEmploiDuTempsMedecin(Medecin medecin) {
        emploiDuTempsMedecins.put(medecin.getId(), new EmploiDuTemps(medecin));
    }
    
    public void creerEmploiDuTempsInfirmier(Infirmier infirmier) {
        emploiDuTempsInfirmiers.put(infirmier.getId(), new EmploiDuTempsInfirmier(infirmier));
    }
    
    public EmploiDuTemps getEmploiDuTempsMedecin(int medecinId) {
        return emploiDuTempsMedecins.get(medecinId);
    }
    
    public EmploiDuTempsInfirmier getEmploiDuTempsInfirmier(int infirmierId) {
        return emploiDuTempsInfirmiers.get(infirmierId);
    }
    
    public List<Creneau> getCreneauxDisponiblesMedecin(int medecinId, LocalDateTime date) {
        EmploiDuTemps edt = emploiDuTempsMedecins.get(medecinId);
        if (edt != null) {
            List<Creneau> creneauxJour = edt.getCreneauxJour(date);
            List<Creneau> disponibles = new ArrayList<>();
            for (Creneau creneau : creneauxJour) {
                if (!creneau.isOccupe()) {
                    disponibles.add(creneau);
                }
            }
            return disponibles;
        }
        return new ArrayList<>();
    }
    
    public boolean reserverCreneauMedecin(int medecinId, LocalDateTime dateHeure, Patient patient, String motif) {
        EmploiDuTemps edt = emploiDuTempsMedecins.get(medecinId);
        if (edt != null) {
            List<Creneau> creneaux = edt.getCreneaux();
            for (Creneau creneau : creneaux) {
                if (creneau.getDebut().equals(dateHeure) && !creneau.isOccupe()) {
                    creneau.assignerRendezVous(patient, motif);
                    return true;
                }
            }
        }
        return false;
    }
    
    public List<Infirmier> getInfirmiersEnService(LocalDateTime moment) {
        List<Infirmier> infirmiersEnService = new ArrayList<>();
        for (EmploiDuTempsInfirmier edt : emploiDuTempsInfirmiers.values()) {
            if (edt.estEnService(moment)) {
                infirmiersEnService.add(edt.getInfirmier());
            }
        }
        return infirmiersEnService;
    }
    
    public List<Infirmier> getInfirmiersParService(String service, LocalDateTime moment) {
        List<Infirmier> infirmiersService = new ArrayList<>();
        for (EmploiDuTempsInfirmier edt : emploiDuTempsInfirmiers.values()) {
            if (edt.estEnService(moment)) {
                List<Shift> shiftsJour = edt.getShiftsJour(moment);
                for (Shift shift : shiftsJour) {
                    if (shift.estEnCours(moment) && service.equals(shift.getService())) {
                        infirmiersService.add(edt.getInfirmier());
                        break;
                    }
                }
            }
        }
        return infirmiersService;
    }
    
    public void initialiserEmploisDuTemps(List<Medecin> medecins, List<Infirmier> infirmiers) {
        // Créer les emplois du temps pour tous les médecins
        for (Medecin medecin : medecins) {
            creerEmploiDuTempsMedecin(medecin);
        }
        
        // Créer les emplois du temps pour tous les infirmiers
        for (Infirmier infirmier : infirmiers) {
            creerEmploiDuTempsInfirmier(infirmier);
        }
    }
    
    public String afficherEmploiDuTempsMedecin(Medecin medecin) {
        EmploiDuTemps edt = emploiDuTempsMedecins.get(medecin.getId());
        return edt != null ? edt.toString() : "Emploi du temps non trouvé";
    }
    
    public String afficherEmploiDuTempsInfirmier(Infirmier infirmier) {
        EmploiDuTempsInfirmier edt = emploiDuTempsInfirmiers.get(infirmier.getId());
        return edt != null ? edt.toString() : "Emploi du temps non trouvé";
    }
    
    public String afficherStatistiquesService() {
        LocalDateTime maintenant = LocalDateTime.now();
        StringBuilder sb = new StringBuilder();
        sb.append("=== STATISTIQUES DES SERVICES ===\n\n");
        
        Map<String, Integer> effectifsParService = new HashMap<>();
        
        for (EmploiDuTempsInfirmier edt : emploiDuTempsInfirmiers.values()) {
            if (edt.estEnService(maintenant)) {
                String poste = edt.getPosteActuel(maintenant);
                List<Shift> shiftsJour = edt.getShiftsJour(maintenant);
                for (Shift shift : shiftsJour) {
                    if (shift.estEnCours(maintenant)) {
                        String service = shift.getService();
                        effectifsParService.put(service, effectifsParService.getOrDefault(service, 0) + 1);
                        break;
                    }
                }
            }
        }
        
        for (Map.Entry<String, Integer> entry : effectifsParService.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(" infirmier(s)\n");
        }
        
        return sb.toString();
    }
}
