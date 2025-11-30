package models;

public class Pharmacien extends ProfessionnelSante {

    public Pharmacien(String string, String string2, int i, int j, String string3) {}

    public Pharmacien(String nom, String prenom, int age, int id) {
        super(nom, prenom, age, id);
    }

    @Override
    public String getRole() {
        return "Pharmacien(ne)";
    }
}