package medipass.models;

public class Pharmacien extends ProfessionnelSante {

    public Pharmacien() {}

    public Pharmacien(String nom, String prenom, int age, int id) {
        super(nom, prenom, age, id);
    }

    @Override
    public String getRole() {
        return "Pharmacien(ne)";
    }
}