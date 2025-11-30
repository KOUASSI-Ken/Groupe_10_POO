package app;

import models.*;
import services.*;
import ui.*;

public class Main {

    public static void main(String[] args) {

        // Initialisation des services
        PatientService patientService = new PatientService();
        ConsultationService consultationService = new ConsultationService();
        PrescriptionService prescriptionService = new PrescriptionService();
        ExamenService examenService = new ExamenService();
        AntecedentService antecedentService = new AntecedentService();

        DossierService dossierService = new DossierService(
                consultationService,
                prescriptionService,
                examenService,
                antecedentService
        );

        ProfessionnelService professionnelService = new ProfessionnelService();
        AdministrateurService administrateurService = new AdministrateurService();
        StatistiqueService statistiqueService = new StatistiqueService();

        AuthService authService = new AuthService();
        MenuConnexion menuConnexion = new MenuConnexion(authService);

        ProfessionnelSante user = menuConnexion.afficherMenuConnexion();
        if (user == null) return;

        String role = user.getRole().toLowerCase();

        switch (role) {
            case "medecin":
                new MenuMedecin((Medecin) user, patientService, dossierService, consultationService).afficherMenu();
                break;
            case "infirmier":
                new MenuInfirmier((Infirmier) user, patientService, professionnelService).afficherMenu();
                break;
            case "pharmacien":
                new MenuPharmacien((Pharmacien) user, patientService, dossierService, prescriptionService).afficherMenu();
                break;
            case "admin":
                new MenuAdministrateur(administrateurService, professionnelService, statistiqueService).afficherMenuAdmin();
                break;
            default:
                System.out.println(" Rôle inconnu : " + user.getRole());
        }

    }
}
