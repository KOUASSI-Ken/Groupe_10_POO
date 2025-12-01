package app;

import models.*;
import services.*;
import ui.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // ===========================
        // INITIALISATION DES SERVICES
        // ===========================
        PatientService patientService = new PatientService();
        ConsultationService consultationService = new ConsultationService();
        PrescriptionService prescriptionService = new PrescriptionService();
        ExamenService examenService = new ExamenService();
        AntecedentService antecedentService = new AntecedentService();
        RendezVousService rendezVousService = new RendezVousService();
        EmploiDuTempsService emploiDuTempsService = new EmploiDuTempsService();
        CSVService csvService = new CSVService();
        RoleService roleService = new RoleService();

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

        // ===========================
        // CONNEXION
        // ===========================
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n===== CONNEXION PAR ID =====");
        System.out.print("Entrez votre ID : ");

        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("❌ ID invalide.");
            return;
        }

        ProfessionnelSante user = authService.connecterParId(id);

        if (user == null) {
            System.out.println("❌ Aucun utilisateur trouvé.");
            return;
        }

        // ===========================
        // AFFICHAGE UTILISATEUR
        // ===========================
        System.out.println("\n✅ Connexion réussie !");
        System.out.println(user.getRole() + " : " +
                user.getNom() + " " + user.getPrenom() + " | ID : " + user.getId());

        String role = user.getRole().toLowerCase().trim();

        // ===========================
        // REDIRECTION
        // ===========================
        switch (role) {

            case "medecin":
                new MenuMedecin((Medecin) user, patientService, dossierService, consultationService, rendezVousService).afficherMenu();
                break;

            case "infirmier":
                new MenuInfirmier((Infirmier) user, patientService, professionnelService).afficherMenu();
                break;

            case "pharmacien":
                new MenuPharmacien((Pharmacien) user, patientService, dossierService, prescriptionService).afficherMenu();
                break;

            case "admin":
                new MenuAdministrateur((Administrateur) user, professionnelService, administrateurService, 
                    statistiqueService, patientService, emploiDuTempsService, csvService, roleService).afficherMenuAdmin();
                break;

            default:
                System.out.println("❌ Rôle inconnu : " + user.getRole());
        }

        System.out.println("\n✅ Déconnexion réussie.");
        scanner.close();
    }
}
