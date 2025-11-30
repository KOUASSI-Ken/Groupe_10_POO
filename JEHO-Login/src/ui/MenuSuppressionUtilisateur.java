package ui;

import services.ProfessionnelService;
import models.ProfessionnelSante;
import java.util.Scanner;

public class MenuSuppressionUtilisateur {

    private final Scanner scanner = new Scanner(System.in);
    private final ProfessionnelService professionnelService;

    public MenuSuppressionUtilisateur(ProfessionnelService professionnelService) {
        this.professionnelService = professionnelService;
    }

    public void afficher() {
        System.out.println("\n--- SUPPRESSION D'UTILISATEUR ---");
        System.out.println("1. Supprimer par ID");
        System.out.println("2. Supprimer par nom");
        System.out.println("0. Retour");
        System.out.print("Choix : ");

        int choix = Integer.parseInt(scanner.nextLine());

        switch (choix) {
            case 1 -> {
                System.out.print("ID : ");
                int id = Integer.parseInt(scanner.nextLine());
                boolean ok = professionnelService.supprimerProfessionnel(id);
                System.out.println(ok ? "✅ Utilisateur supprimé" : "❌ Aucun utilisateur trouvé");
            }
            case 2 -> {
                System.out.print("Nom : ");
                String nom = scanner.nextLine();
                boolean trouve = false;
                for (ProfessionnelSante p : professionnelService.getTousProfessionnels()) {
                    if (p.getNom().equalsIgnoreCase(nom)) {
                        professionnelService.supprimerProfessionnel(p);
                        trouve = true;
                        System.out.println("✅ Utilisateur supprimé : " + p.getNom());
                        break;
                    }
                }
                if (!trouve) System.out.println("❌ Aucun utilisateur trouvé avec ce nom");
            }
            case 0 -> System.out.println("Retour au menu précédent...");
            default -> System.out.println("❌ Choix invalide");
        }
    }
}
