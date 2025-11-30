package models;

public abstract class ProfessionnelSante {
    private String nom;
    private String prenom;
    private int age;
    private int id;
    private String login;
    private String motDePasse;

    public ProfessionnelSante(String nom, String prenom, int age, int id, String login, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.id = id;
        this.login = login;
        this.motDePasse = motDePasse;
    }

    public abstract String getRole();

    // Getters et Setters
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public int getAge() { return age; }
    public int getId() { return id; }
    public String getLogin() { return login; }
    public String getMotDePasse() { return motDePasse; }

    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setAge(int age) { if (age > 0) this.age = age; }
    public void setId(int id) { if (id > 0) this.id = id; }
    public void setLogin(String login) { this.login = login; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    @Override
    public String toString() {
        return getRole() + " : " + nom + " " + prenom + " | ID : " + id;
    }
}
