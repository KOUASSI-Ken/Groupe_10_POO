package ui;

import services.ProfessionnelService;
import models.ProfessionnelSante;
import models.Medecin;
import models.Infirmier;
import models.Pharmacien;
import java.util.Scanner;

public class MenuCreationUtilisateur {

    private final Scanner scanner = new Scanner(System.in);
    private final ProfessionnelService professionnelService;

    public MenuCreationUtilisateur(ProfessionnelService professionnelService) {
        this.professionnelService = professionnelService;
    }

    public void afficher() {
        System.out.println("\n--- CRÉATION D'UTILISATEUR ---");

        System.out.print("Nom : ");
        String nom = scanner.nextLine();

        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();

        System.out.print("Âge : ");
        int age = Integer.parseInt(scanner.nextLine());

        System.out.print("ID : ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.println("Type de professionnel : 1. Médecin 2. Infirmier 3. Pharmacien");
        System.out.print("Choix : ");
        int type = Integer.parseInt(scanner.nextLine());

        System.out.print("Spécialité : ");
        String specialite = scanner.nextLine();

        ProfessionnelSante pro = null;
        switch (type) {
            case 1 -> pro = new Medecin(nom, prenom, age, id, specialite);
            case 2 -> pro = new Infirmier(nom, prenom, age, id, specialite);
            case 3 -> pro = new Pharmacien(nom, prenom, age, id, specialite);
            default -> {
                System.out.println("❌ Type invalide !");
                return;
            }
        }

        try {
            professionnelService.creerProfessionnel(pro);
            System.out.println("✅ Utilisateur créé : " + prenom + " " + nom + " (" + pro.getRole() + ")");
        } catch (Exception e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
    }
}
