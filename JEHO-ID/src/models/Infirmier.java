package models;

public class Infirmier extends ProfessionnelSante {

    public Infirmier(String string, String string2, int i, int j, String string3) {}

    public Infirmier(String nom, String prenom, int age, int id) {
        super(nom, prenom, age, id);
    }

    @Override
    public String getRole() {
        return "Infirmier(ère)";
    }
}