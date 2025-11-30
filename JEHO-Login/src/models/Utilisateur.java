package models;

public class Utilisateur extends ProfessionnelSante {

    private String role;

    public Utilisateur(String nom, String prenom, int age, int id, String role) {
        super(nom, prenom, age, id);
        this.role = role;
    }

    @Override
    public String getRole() {
        return role;
    }
}
