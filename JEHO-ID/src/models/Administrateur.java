package models;

public class Administrateur {
    private String nom;
    private String prenom;
    private int age;
    private int id;

    public Administrateur() {}

    public Administrateur(String nom, String prenom, int age, int id) {
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
    public void setNom(String nom) { 
        if (nom != null && !nom.trim().isEmpty()) {
            this.nom = nom;
        }
    }
    
    public void setPrenom(String prenom) { 
        if (prenom != null && !prenom.trim().isEmpty()) {
            this.prenom = prenom;
        }
    }
    
    public void setId(int id) { 
        if (id > 0) this.id = id; 
    }
    
    public void setAge(int age) { 
        if (age > 0) this.age = age; 
    }

    // Validation
    public boolean estValide() {
        return nom != null && !nom.isEmpty() 
            && prenom != null && !prenom.isEmpty()
            && age > 0 
            && id > 0;
    }

    @Override
    public String toString() {
        return "Administrateur:\n" +
               "  Nom: " + nom + "\n" +
               "  Prénom: " + prenom + "\n" +
               "  Âge: " + age + "\n" +
               "  ID: " + id;
    }
}