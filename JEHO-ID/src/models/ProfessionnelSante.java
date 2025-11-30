package models;

public abstract class ProfessionnelSante {
    private String nom;
    private String prenom;
    private int age;
    private int id;

    public ProfessionnelSante() {}

    public ProfessionnelSante(String nom, String prenom, int age, int id) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.id = id;
    }

    // Getters
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public int getAge() { return age; }
    public int getId() { return id; }

    // Setters
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setId(int id) { 
        if (id > 0) this.id = id; 
    }
    public void setAge(int age) { 
        if (age > 0 ) this.age = age; 
    }

    
    public abstract String getRole();

    @Override
    public String toString() {
        return getRole() + ":\n" +
               "  Nom: " + nom + "\n" +
               "  Prénom: " + prenom + "\n" +
               "  Âge: " + age + "\n" +
               "  ID: " + id;
    }
}