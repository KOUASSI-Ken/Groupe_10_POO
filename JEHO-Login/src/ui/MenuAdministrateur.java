package ui;

import services.AdministrateurService;
import services.ProfessionnelService;
import services.StatistiqueService;
import java.util.Scanner;

public class MenuAdministrateur {

    private Scanner scanner = new Scanner(System.in);
    private AdministrateurService adminService;
    private ProfessionnelService professionnelService;
    private StatistiqueService statService;

    public MenuAdministrateur(AdministrateurService adminService,
                              ProfessionnelService professionnelService,
                              StatistiqueService statService) {
        this.adminService = adminService;
        this.professionnelService = professionnelService;
        this.statService = statService;
    }

    public void afficherMenuAdmin() {
        boolean resterMenu = true;

        while (resterMenu) {
            System.out.println("\n===== MENU ADMINISTRATEUR =====");
            System.out.println("1. Créer un utilisateur");
            System.out.println("2. Supprimer un utilisateur");
            System.out.println("3. Afficher les statistiques");
            System.out.println("0. Déconnexion");
            System.out.print("Choix : ");

            try {
                int choix = Integer.parseInt(scanner.nextLine());

                switch (choix) {
                    case 1:
                        new MenuCreationUtilisateur(professionnelService).afficher();
                        break;

                    case 2:
                        new MenuSuppressionUtilisateur(professionnelService).afficher();
                        break;

                    case 3:
                        new MenuStatistiques(statService).afficher();
                        break;

                    case 0:
                        resterMenu = false;
                        System.out.println(" Déconnexion réussie.");
                        break;

                    default:
                        System.out.println(" Choix invalide !");
                }
            } catch (Exception e) {
                System.out.println(" Erreur de saisie !");
            }
        }
    }
}
