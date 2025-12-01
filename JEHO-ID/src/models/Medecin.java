package models;

public class Medecin extends ProfessionnelSante {
    private String specialite;

    public Medecin() {}

    public Medecin(String nom, String prenom, int age, int id, String specialite) {
        super(nom, prenom, age, id);
        this.specialite = specialite;
    }

    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }

    @Override
    public String getRole() {
        return "medecin";
    }
}