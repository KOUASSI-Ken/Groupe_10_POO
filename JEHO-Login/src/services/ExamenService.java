package services;

import models.ExamenMedical;
import models.Medecin;
import models.Patient;
import java.time.LocalDate;
import java.time.LocalTime;

public class ExamenService {

    // Ajouter un examen
    public void ajouterExamen(Patient patient, ExamenMedical examen) {
        if (patient == null || examen == null) {
            throw new IllegalArgumentException("Patient et examen ne peuvent pas être null");
        }
        patient.getDossier().ajouterExamen(examen);
    }

    // Supprimer un examen
    public boolean supprimerExamen(Patient patient, ExamenMedical examen) {
        if (patient == null || examen == null) {
            return false;
        }
        return patient.getDossier().supprimerExamen(examen);
    }

    // Rechercher un examen par nom
    public ExamenMedical rechercherExamen(Patient patient, String nom) {
        if (patient == null || nom == null) {
            return null;
        }
        for (ExamenMedical e : patient.getDossier().getExamens()) {
            if (e.getNom().equalsIgnoreCase(nom)) {
                return e;
            }
        }
        return null;
    }

    // Modifier un examen (AVEC CONTRÔLE MÉDECIN)
    public boolean modifierExamen(
            Medecin medecin,
            Patient patient,
            String nomExamen,
            String nouveauNom,
            String nouveauType,
            LocalDate nouvelleDate,
            LocalTime nouvelleHeure,
            String nouveauResultat,
            Boolean effectue) {

        if (medecin == null) {
            throw new SecurityException("Seul un médecin peut modifier un examen");
        }

        if (patient == null || nomExamen == null) {
            return false;
        }

        ExamenMedical examen = rechercherExamen(patient, nomExamen);
        if (examen == null) {
            return false;
        }

        // Modification
        if (nouveauNom != null) examen.setNom(nouveauNom);
        if (nouveauType != null) examen.setType(nouveauType);
        if (nouvelleDate != null) examen.setDate(nouvelleDate);
        if (nouvelleHeure != null) examen.setHeure(nouvelleHeure);
        if (nouveauResultat != null) examen.setResultat(nouveauResultat);
        if (effectue != null) examen.setEffectue(effectue);

        return true;
    }

    // Obtenir tous les examens
    public String getHistoriqueExamens(Patient patient) {
        if (patient == null || patient.getDossier().getExamens().isEmpty()) {
            return "Aucun examen pour ce patient.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== Examens de ").append(patient.getNom()).append(" ===\n");
        for (ExamenMedical e : patient.getDossier().getExamens()) {
            sb.append(e.toString()).append("\n---\n");
        }
        return sb.toString();
    }
}