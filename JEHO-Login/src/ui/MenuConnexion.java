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
        System.out.println("===== CONNEXION =====");

        System.out.print("Login : ");
        String login = scanner.nextLine();

        System.out.print("Mot de passe : ");
        String mdp = scanner.nextLine();

        ProfessionnelSante user = authService.connecterParLogin(login, mdp);

        if (user == null) {
            System.out.println(" Login ou mot de passe incorrect !");
            return null;
        }

        System.out.println("\n Connexion réussie !");
        System.out.println(user.getRole() + " : " + user.getNom() + " " + user.getPrenom());
        return user;
    }
}
