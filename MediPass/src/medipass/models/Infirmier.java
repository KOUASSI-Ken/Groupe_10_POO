package medipass.models;

public class Infirmier extends ProfessionnelSante {

    public Infirmier() {}

    public Infirmier(String nom, String prenom, int age, int id) {
        super(nom, prenom, age, id);
    }

    @Override
    public String getRole() {
        return "Infirmier(ère)";
    }
}