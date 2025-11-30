package models;

public class Administrateur extends ProfessionnelSante {

    public Administrateur(String nom, String prenom, int age, int id, String login, String motDePasse) {
        super(nom, prenom, age, id, login, motDePasse);
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}
