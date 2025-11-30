package ui;

import java.util.Scanner;
import services.AuthService;
import models.ProfessionnelSante;

public class MenuConnexion {

    private final Scanner scanner = new Scanner(System.in);
    private final AuthService authService;

    public MenuConnexion(AuthService authService) {
        this.authService = authService;
    }

    public ProfessionnelSante afficherMenuConnexion() {
        System.out.println("===== CONNEXION PAR ID =====");
        System.out.print("Entrez votre ID : ");

        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("ID invalide !");
            return null;
        }

        ProfessionnelSante user = authService.connecterParId(id);

        if (user == null) {
            System.out.println("Aucun utilisateur trouvé.");
            return null;
        }

        System.out.println("\nConnexion réussie !");
        System.out.println(user);
        return user;
    }
}
