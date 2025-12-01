package models;

import java.time.LocalDate;
import java.time.Period; // period permet de déterminer l'écart entre deux dates
import java.time.format.DateTimeFormatter;

public class Patient {
    private int id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String sexe;
    private DossierMedical dossier;
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Patient() {
        this.dossier = new DossierMedical();
    }

    public Patient(int id, String nom, String prenom, String dateNaissanceStr, String sexe) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = LocalDate.parse(dateNaissanceStr, FORMATTER);
        this.sexe = sexe;
        this.dossier = new DossierMedical();
    }

    // Getters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public String getDateNaissanceFormatee() { 
        return dateNaissance != null ? dateNaissance.format(FORMATTER) : "N/A"; 
    }
    public String getSexe() { return sexe; }
    public DossierMedical getDossier() { return dossier; }
    
    // Calcul automatique de l'âge à travers l'écart entre la naissance et la date actuelle
    public int getAge() {
        if (dateNaissance == null) return 0;
        return Period.between(dateNaissance, LocalDate.now()).getYears();
    }

    // Setters
    public void setId(int id) { 
        if (id > 0) this.id = id; 
    }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setDateNaissance(String dateStr) { 
        this.dateNaissance = LocalDate.parse(dateStr, FORMATTER); 
    }
    public void setDateNaissance(LocalDate date) { 
        this.dateNaissance = date; 
    }
    public void setSexe(String sexe) { 
        if ("M".equalsIgnoreCase(sexe) || "F".equalsIgnoreCase(sexe)) {
            this.sexe = sexe.toUpperCase();
        }
    }
    public void setAge(int age) {
        // Met à jour la date de naissance en fonction de l'âge fourni
        if (age > 0 && age <= 120) {
            this.dateNaissance = LocalDate.now().minusYears(age);
        }
    }

    @Override
    public String toString() {
        return "Patient ID: " + id + "\n" +
               "  Nom: " + nom + " " + prenom + "\n" +
               "  Date de naissance: " + getDateNaissanceFormatee() + "\n" +
               "  Âge: " + getAge() + " ans\n" +
               "  Sexe: " + sexe;
    }
}