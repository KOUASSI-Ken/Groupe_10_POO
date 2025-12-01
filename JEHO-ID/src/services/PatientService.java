package services;

import models.Patient;
import java.util.ArrayList;
import java.util.List;

public class PatientService {
    private List<Patient> patients;  // ✅ AJOUT de la liste manquante !

    public PatientService() {
        this.patients = new ArrayList<>();
        
        // Création de 6 patients prédéfinis
        patients.add(new Patient(101, "Dupont", "Jean", "15/03/1985", "M"));
        patients.add(new Patient(102, "Martin", "Sophie", "22/07/1992", "F"));
        patients.add(new Patient(103, "Bernard", "Pierre", "08/11/1978", "M"));
        patients.add(new Patient(104, "Robert", "Marie", "30/04/1995", "F"));
        patients.add(new Patient(105, "Durand", "Michel", "12/09/1980", "M"));
        patients.add(new Patient(106, "Lefebvre", "Isabelle", "25/02/1988", "F"));
    }

    // Ajouter un patient
    public void ajouterPatient(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Le patient que vous voulez créer ne contient aucune information");
        }
        if (rechercherPatient(patient.getId()) != null) {
            throw new IllegalArgumentException("Un patient avec l'ID " + patient.getId() + " existe déjà");
        }
        patients.add(patient);
    }

    // Rechercher un patient en utilisant son  ID
    public Patient rechercherPatient(int id) {
        for (Patient p : patients) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    // Supprimer un patient
    public boolean supprimerPatient(int id) {
        Patient patient = rechercherPatient(id);
        if (patient != null) {
            return patients.remove(patient);
        }
        return false;
    }

    // Obtenir tous les patients
    public List<Patient> getTousLesPatients() {
        return new ArrayList<>(patients);  
    }

    // Statistiques
    public int getNombrePatients() {
        return patients.size();
    }
    
    // Mettre à jour un patient
    public boolean mettreAJourPatient(Patient patient) {
        if (patient == null) return false;
        
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getId() == patient.getId()) {
                patients.set(i, patient);
                return true;
            }
        }
        return false;
    }
    
    // Archiver un patient (marquer comme inactif)
    public boolean archiverPatient(int id) {
        Patient patient = rechercherPatient(id);
        if (patient != null) {
            // Ajouter un préfixe au nom pour indiquer l'archivage
            patient.setNom("[ARCHIVÉ] " + patient.getNom());
            return true;
        }
        return false;
    }
    
    // Rechercher des patients par nom
    public List<Patient> rechercherParNom(String nom) {
        List<Patient> resultats = new ArrayList<>();
        for (Patient p : patients) {
            if (p.getNom().toLowerCase().contains(nom.toLowerCase())) {
                resultats.add(p);
            }
        }
        return resultats;
    }
    
    // Obtenir les patients actifs (non archivés)
    public List<Patient> getPatientsActifs() {
        List<Patient> actifs = new ArrayList<>();
        for (Patient p : patients) {
            if (!p.getNom().startsWith("[ARCHIVÉ]")) {
                actifs.add(p);
            }
        }
        return actifs;
    }
}
