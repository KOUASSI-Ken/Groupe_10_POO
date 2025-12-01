package models;

public class Infirmier extends ProfessionnelSante {

    public Infirmier(String nom, String prenom, int age, int id, String role) {
        super(nom, prenom, age, id);
    }

    public Infirmier(String nom, String prenom, int age, int id) {
        super(nom, prenom, age, id);
    }

    @Override
    public String getRole() {
        return "infirmier";
    }
}