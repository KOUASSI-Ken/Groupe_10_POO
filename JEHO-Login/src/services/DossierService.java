package services;

import models.Patient;

public class DossierService {
    private ConsultationService consultationService;
    private PrescriptionService prescriptionService;
    private ExamenService examenService;
    private AntecedentService antecedentService;

    public DossierService(
            ConsultationService consultationService,
            PrescriptionService prescriptionService,
            ExamenService examenService,
            AntecedentService antecedentService) {

        this.consultationService = consultationService;
        this.prescriptionService = prescriptionService;
        this.examenService = examenService;
        this.antecedentService = antecedentService;
    }

    // Afficher le dossier complet (retourne String au lieu de println)
    public String afficherDossierComplet(Patient patient) {
        if (patient == null) {
            return "Patient invalide.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("╔════════════════════════════════════════════╗\n");
        sb.append("║       DOSSIER MÉDICAL COMPLET              ║\n");
        sb.append("╚════════════════════════════════════════════╝\n\n");

        sb.append(patient.toString()).append("\n\n");

        sb.append("--- CONSULTATIONS ---\n");
        sb.append(consultationService.getHistoriqueConsultations(patient)).append("\n");

        sb.append("--- PRESCRIPTIONS ---\n");
        sb.append(prescriptionService.getHistoriquePrescriptions(patient)).append("\n");

        sb.append("--- EXAMENS MÉDICAUX ---\n");
        sb.append(examenService.getHistoriqueExamens(patient)).append("\n");

        sb.append("--- ANTÉCÉDENTS ---\n");
        sb.append(antecedentService.getHistoriqueAntecedents(patient)).append("\n");

        sb.append("╔════════════════════════════════════════════╗\n");
        sb.append("║            FIN DU DOSSIER                  ║\n");
        sb.append("╚════════════════════════════════════════════╝\n");

        return sb.toString();
    }

    // Supprimer le dossier complet
    public boolean supprimerDossierComplet(Patient patient) {
        if (patient == null || patient.getDossier() == null) {
            return false;
        }

        patient.getDossier().viderDossier();
        return true;
    }
}