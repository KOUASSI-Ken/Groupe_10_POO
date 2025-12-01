package ui;

import models.Administrateur;
import services.AdministrateurService;
import services.ProfessionnelService;
import services.StatistiqueService;
import services.PatientService;
import services.EmploiDuTempsService;
import services.CSVService;
import services.RoleService;
import java.util.Scanner;

public class MenuAdministrateur {

    private Scanner scanner = new Scanner(System.in);
    private AdministrateurService adminService;
    private ProfessionnelService professionnelService;
    private StatistiqueService statService;
    private PatientService patientService;
    private EmploiDuTempsService emploiDuTempsService;
    private CSVService csvService;
    private RoleService roleService;

    public MenuAdministrateur(Administrateur admin,
                              ProfessionnelService professionnelService,
                              AdministrateurService administrateurService,
                              StatistiqueService statistiqueService,
                              PatientService patientService,
                              EmploiDuTempsService emploiDuTempsService,
                              CSVService csvService,
                              RoleService roleService) {
        this.adminService = administrateurService;
        this.professionnelService = professionnelService;
        this.statService = statistiqueService;
        this.patientService = patientService;
        this.emploiDuTempsService = emploiDuTempsService;
        this.csvService = csvService;
        this.roleService = roleService;
    }

    public void afficherMenuAdmin() {
        boolean resterMenu = true;

        while (resterMenu) {
            System.out.println("\n===== MENU ADMINISTRATEUR =====");
            System.out.println("1. Créer un utilisateur");
            System.out.println("2. Supprimer un utilisateur");
            System.out.println("3. Afficher les statistiques");
            System.out.println("4. Gestion des emplois du temps");
            System.out.println("5. Import/Export CSV");
            System.out.println("6. Gestion des droits d'accès");
            System.out.println("0. Retour");
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
                        
                    case 4:
                        MenuEmploiDuTemps menuEDT = new MenuEmploiDuTemps(emploiDuTempsService, professionnelService);
                        menuEDT.afficherMenu();
                        break;
                        
                    case 5:
                        MenuCSV menuCSV = new MenuCSV(csvService, patientService, professionnelService);
                        menuCSV.afficherMenu();
                        break;
                        
                    case 6:
                        MenuDroitsAcces menuDroits = new MenuDroitsAcces(roleService, professionnelService);
                        menuDroits.afficherMenu();
                        break;

                    case 0:
                        resterMenu = false;
                        System.out.println("Retour au menu principal...");
                        break;

                    default:
                        System.out.println("Choix invalide !");
                }
            } catch (Exception e) {
                System.out.println("Erreur de saisie !");
            }
        }
    }
}
