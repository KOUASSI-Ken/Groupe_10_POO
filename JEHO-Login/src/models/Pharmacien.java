package models;

public class Pharmacien extends ProfessionnelSante {

    public Pharmacien(String nom, String prenom, int age, int id, String login, String motDePasse) {
        super(nom, prenom, age, id, login, motDePasse);
    }

    @Override
    public String getRole() {
        return "Pharmacien";
    }
}
