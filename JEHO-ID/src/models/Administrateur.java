package models;

public class Administrateur extends ProfessionnelSante {

    public Administrateur() {}

    public Administrateur(String nom, String prenom, int age, int id) {
        super(nom, prenom, age, id);
    }

    @Override
    public String getRole() {
        return "admin";
    }

    // Validation
    public boolean estValide() {
        return getNom() != null && !getNom().isEmpty() 
            && getPrenom() != null && !getPrenom().isEmpty()
            && getAge() > 0 
            && getId() > 0;
    }

    @Override
    public String toString() {
        return "Administrateur:\n" +
               "  Nom: " + getNom() + "\n" +
               "  Prénom: " + getPrenom() + "\n" +
               "  Âge: " + getAge() + "\n" +
               "  ID: " + getId();
    }
}