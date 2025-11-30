package services;

import models.Patient;
import java.time.LocalDate;

public class StatistiqueService {
    private PatientService patientService;
    private ProfessionnelService professionnelService;

    public StatistiqueService() {
       
    }

    // Nombre total de patients
    public int getNombreTotalPatients() {
        return patientService.getNombrePatients();
    }

    // Nombre de consultations dans une période
    public int getNombreConsultationsPeriode(LocalDate debut, LocalDate fin) {
        int count = 0;
        for (Patient p : patientService.getTousLesPatients()) {
            count += p.getDossier().getConsultations().stream()
                    .filter(c -> !c.getDate().isBefore(debut) && !c.getDate().isAfter(fin))
                    .count();
        }
        return count;
    }
    
    // Nombre d'examens  dans une période
    public int getNombreExamensPeriode(LocalDate debut, LocalDate fin) {
        int count = 0;
        for (Patient p : patientService.getTousLesPatients()) {
            count += p.getDossier().getExamens().stream()
                    .filter(c -> !c.getDate().isBefore(debut) && !c.getDate().isAfter(fin))
                    .count();
        }
        return count;
    }

    // Rapport complet
    public String genererRapportComplet() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔═══════════════════════════════════════════╗\n");
        sb.append("║     STATISTIQUES SYSTÈME MEDIPASS         ║\n");
        sb.append("╚═══════════════════════════════════════════╝\n\n");

        sb.append("PATIENTS\n");
        sb.append("  Total: ").append(getNombreTotalPatients()).append("\n\n");

        sb.append("PROFESSIONNELS DE SANTÉ\n");
        sb.append("  Médecins: ").append(professionnelService.getNombreMedecins()).append("\n");
        sb.append("  Infirmiers: ").append(professionnelService.getNombreInfirmiers()).append("\n");
        sb.append("  Pharmaciens: ").append(professionnelService.getNombrePharmaciens()).append("\n");
        sb.append("  Total: ").append(professionnelService.getTousProfessionnels().size()).append("\n");

        return sb.toString();
    }
}