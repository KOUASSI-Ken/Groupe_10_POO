package services;

import models.Prescription;
import models.Patient;
import models.Medecin;
import models.Pharmacien;
import models.Infirmier;
import java.time.LocalDate;

public class PrescriptionService {

    // Ajouter une prescription (réservé aux médecins)
    public void ajouterPrescription(Medecin medecin, Patient patient, Prescription prescription) {
        if (medecin == null) {
            throw new SecurityException("Seul un médecin peut prescrire");
        }
        if (patient == null || prescription == null) {
            throw new IllegalArgumentException("Patient et prescription ne peuvent pas être null");
        }
        patient.getDossier().ajouterPrescription(prescription);
    }

    // Rechercher une prescription
    public Prescription rechercherPrescription(Patient patient, String nomProduit) {
        if (patient == null || nomProduit == null) {
            return null;
        }
        return patient.getDossier().rechercherPrescription(nomProduit);
    }

    // Modifier une prescription (médecin, pharmacien ou infirmier)
    public boolean modifierPrescription(
            Object professionnel,  // Peut être Medecin, Pharmacien ou Infirmier
            Patient patient,
            String nomProduit,
            String nouveauNomProduit,
            String nouvellePosologie,
            LocalDate nouvelleDate) {

        // Vérification des droits
        if (!(professionnel instanceof Medecin) && 
            !(professionnel instanceof Pharmacien) && 
            !(professionnel instanceof Infirmier)) {
            throw new SecurityException("Seuls médecins, pharmaciens et infirmiers peuvent modifier les prescriptions");
        }

        if (patient == null || nomProduit == null) {
            return false;
        }

        Prescription prescription = rechercherPrescription(patient, nomProduit);
        if (prescription == null) {
            return false;
        }

        // Modification
        if (nouveauNomProduit != null) prescription.setNomProduit(nouveauNomProduit);
        if (nouvellePosologie != null) prescription.setPosologie(nouvellePosologie);
        if (nouvelleDate != null) prescription.setDate(nouvelleDate);

        return true;
    }

    // Supprimer une prescription
    public boolean supprimerPrescription(Patient patient, Prescription prescription) {
        if (patient == null || prescription == null) {
            return false;
        }
        return patient.getDossier().supprimerPrescription(prescription);
    }

    // Obtenir toutes les prescriptions
    public String getHistoriquePrescriptions(Patient patient) {
        if (patient == null || patient.getDossier().getPrescriptions().isEmpty()) {
            return "Aucune prescription pour ce patient.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== Prescriptions de ").append(patient.getNom()).append(" ===\n");
        for (Prescription p : patient.getDossier().getPrescriptions()) {
            sb.append(p.toString()).append("\n---\n");
        }
        return sb.toString();
    }
}