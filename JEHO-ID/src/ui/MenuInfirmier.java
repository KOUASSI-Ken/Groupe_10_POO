package ui;

import models.Infirmier;
import services.PatientService;
import services.ProfessionnelService;

import java.util.Scanner;

public class MenuInfirmier {

    private final Infirmier infirmier;
    private final PatientService patientService;
    private final ProfessionnelService proService;
    private final Scanner scanner = new Scanner(System.in);

    public MenuInfirmier(Infirmier infirmier,
                         PatientService patientService,
                         ProfessionnelService proService) {

        this.infirmier = infirmier;
        this.patientService = patientService;
        this.proService = proService;
    }

    public void afficherMenu() {

        boolean actif = true;

        while (actif) {

            System.out.println("\n===== MENU INFIRMIER =====");
            System.out.println("1. 📋 Gestion des patients");
            System.out.println("2. 👨‍⚕️ Médecins disponibles");
            System.out.println("0. 🔒 Déconnexion");

            System.out.print("Choix : ");
            String choix = scanner.nextLine();

            switch (choix) {
                case "1" -> new MenuGestionPatient(patientService).afficherMenu();
                case "2" -> afficherMedecins();
                case "0" -> actif = false;
                default -> System.out.println("❌ Choix invalide !");
            }
        }
    }

    // ---------------------
    // LISTE DES MÉDECINS
    // ---------------------
    private void afficherMedecins() {

        System.out.println("\n--- MÉDECINS DISPONIBLES ---");

        proService.getTousMedecins().forEach(m ->
            System.out.println(m.getId() + " | " + m.getNom() + " " + m.getPrenom())
        );
    }
}
