package services;

import models.Antecedent;
import models.Medecin;
import models.Patient;
import java.time.LocalDate;

public class AntecedentService {

    // Ajouter un antécédent
    public void ajouterAntecedent(Patient patient, Antecedent antecedent) {
        if (patient == null || antecedent == null) {
            throw new IllegalArgumentException("Patient et antécédent ne peuvent pas être null");
        }
        patient.getDossier().ajouterAntecedent(antecedent);
    }

    // Supprimer un antécédent
    public boolean supprimerAntecedent(Patient patient, Antecedent antecedent) {
        if (patient == null || antecedent == null) {
            return false;
        }
        return patient.getDossier().supprimerAntecedent(antecedent);
    }

    // Modifier un antécédent (AVEC CONTRÔLE MÉDECIN)
    public boolean modifierAntecedent(
            Medecin medecin,
            Patient patient,
            Antecedent antecedent,
            String nouvelleDescription,
            LocalDate nouvelleDate) {

        if (medecin == null) {
            throw new SecurityException("Seul un médecin peut modifier les antécédents");
        }

        if (patient == null || antecedent == null) {
            return false;
        }

        // Vérifier que l'antécédent appartient au patient
        if (!patient.getDossier().getAntecedents().contains(antecedent)) {
            return false;
        }

        // Modification
        if (nouvelleDescription != null) antecedent.setDescription(nouvelleDescription);
        if (nouvelleDate != null) antecedent.setDate(nouvelleDate);

        return true;
    }

    // Obtenir tous les antécédents
    public String getHistoriqueAntecedents(Patient patient) {
        if (patient == null || patient.getDossier().getAntecedents().isEmpty()) {
            return "Aucun antécédent pour ce patient.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== Antécédents de ").append(patient.getNom()).append(" ===\n");
        for (Antecedent a : patient.getDossier().getAntecedents()) {
            sb.append(a.toString()).append("\n---\n");
        }
        return sb.toString();
    }
}