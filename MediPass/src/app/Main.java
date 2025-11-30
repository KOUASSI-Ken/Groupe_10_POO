package app;

import ui.*;
import medipass.models.*;

public class Main {

    public static void main(String[] args) {

        MenuConnexion connexion = new MenuConnexion();
        ProfessionnelSante user = connexion.afficherMenuConnexion();

        if (user == null) {
            System.out.println("Fin du programme.");
            return;
        }

        if (user instanceof Medecin) {
            new MenuMedecin().afficher((Medecin) user);
        }
        else if (user instanceof Infirmier || user instanceof Pharmacien) {
            new MenuProfessionnel().afficher(user);
        }
        else {
            new MenuAdministrateur().afficher();
        }
    }
}
