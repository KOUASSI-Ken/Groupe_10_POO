package services;

import models.Patient;
import java.util.ArrayList;
import java.util.List;

public class PatientService {
    private List<Patient> patients;  // ✅ AJOUT de la liste manquante !

    public PatientService() {
        this.patients = new ArrayList<>();
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
}
