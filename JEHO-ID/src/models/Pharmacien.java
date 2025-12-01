package models;

public class Pharmacien extends ProfessionnelSante {

    public Pharmacien(String nom, String prenom, int age, int id, String role) {
        super(nom, prenom, age, id);
    }

    public Pharmacien(String nom, String prenom, int age, int id) {
        super(nom, prenom, age, id);
    }

    @Override
    public String getRole() {
        return "pharmacien";
    }
}