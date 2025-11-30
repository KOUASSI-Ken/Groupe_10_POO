package models;

public class Medecin extends ProfessionnelSante {
    private String specialite;

    public Medecin(String nom, String prenom, int age, int id, String login, String motDePasse, String specialite) {
        super(nom, prenom, age, id, login, motDePasse);
        this.specialite = specialite;
    }

    @Override
    public String getRole() {
        return "medecin";
    }
    
    @Override
    public String toString() {
        return getRole() + " - " + specialite + " : " + getNom() + " " + getPrenom();
    }
}
