package models;

public class Infirmier extends ProfessionnelSante {

    public Infirmier(String nom, String prenom, int age, int id, String login, String motDePasse) {
        super(nom, prenom, age, id, login, motDePasse);
    }

    @Override
    public String getRole() {
        return "Infirmier";
    }
}
