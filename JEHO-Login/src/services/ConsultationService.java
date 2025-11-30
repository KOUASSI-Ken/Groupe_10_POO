package services;

import models.Consultation;
import models.Medecin;
import models.Patient;
import java.time.LocalDate;

public class ConsultationService {

    // Ajouter une consultation
    public void ajouterConsultation(Patient patient, Consultation consultation) {
        if (patient == null || consultation == null) {
            throw new IllegalArgumentException("Patient et consultation ne peuvent pas être null");
        }
        patient.getDossier().ajouterConsultation(consultation);
    }

    // Supprimer une consultation
    public boolean supprimerConsultation(Patient patient, Consultation consultation) {
        if (patient == null || consultation == null) {
            return false;
        }
        return patient.getDossier().supprimerConsultation(consultation);
    }

    // Rechercher une consultation par date
    public Consultation rechercherConsultation(Patient patient, LocalDate date) {
        if (patient == null || date == null) {
            return null;
        }
        return patient.getDossier().rechercherConsultation(date);
    }

    // Modifier une consultation (AVEC CONTRÔLE MÉDECIN)
    public boolean modifierConsultation(
            Medecin medecin,
            Patient patient,
            LocalDate date,
            String nouveauMotif,
            String nouveauDiagnostic,
            String nouveauMedecin) {
        
        if (medecin == null) {
            throw new SecurityException("Seul un médecin peut modifier une consultation");
        }

        if (patient == null || date == null) {
            return false;
        }

        Consultation consultation = rechercherConsultation(patient, date);
        if (consultation == null) {
            return false;
        }

        // Modification
        if (nouveauMotif != null) consultation.setMotif(nouveauMotif);
        if (nouveauDiagnostic != null) consultation.setDiagnostic(nouveauDiagnostic);
        if (nouveauMedecin != null) consultation.setMedecin(nouveauMedecin);

        return true;
    }

    // Obtenir l'historique complet
    public String getHistoriqueConsultations(Patient patient) {
        if (patient == null || patient.getDossier().getConsultations().isEmpty()) {
            return "Aucune consultation pour ce patient.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== Consultations de ").append(patient.getNom()).append(" ===\n");
        for (Consultation c : patient.getDossier().getConsultations()) {
            sb.append(c.toString()).append("\n---\n");
        }
        return sb.toString();
    }
}