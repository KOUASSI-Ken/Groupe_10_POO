package models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ExamenMedical {
    private String nom;
    private String type;
    private LocalTime heure;
    private LocalDate date;
    private String resultat;
    private boolean effectue;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public ExamenMedical() {}

    public ExamenMedical(String nom, String type, LocalDate date, LocalTime heure, String resultat, boolean effectue) {
        this.nom = nom;
        this.type = type;
        this.date = date;
        this.heure = heure;
        this.resultat = resultat;
        this.effectue = effectue;
    }

    // Getters
    public String getNom() { return nom; }
    public String getType() { return type; }
    public LocalTime getHeure() { return heure; }
    public LocalDate getDate() { return date; }
    public String getResultat() { return resultat; }
    public boolean isEffectue() { return effectue; }
    
    public String getDateFormatee() { 
        return date != null ? date.format(DATE_FORMATTER) : "N/A"; 
    }
    public String getHeureFormatee() { 
        return heure != null ? heure.format(TIME_FORMATTER) : "N/A"; 
    }

    // Setters
    public void setNom(String nom) { this.nom = nom; }
    public void setType(String type) { this.type = type; }
    public void setHeure(LocalTime heure) { this.heure = heure; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setResultat(String resultat) { this.resultat = resultat; }
    public void setEffectue(boolean effectue) { this.effectue = effectue; }

    @Override
    public String toString() {
        return "Examen Médical:\n" +
               "  Nom: " + nom + "\n" +
               "  Type: " + type + "\n" +
               "  Date: " + getDateFormatee() + "\n" +
               "  Heure: " + getHeureFormatee() + "\n" +
               "  Résultat: " + (resultat != null ? resultat : "En attente") + "\n" +
               "  Statut: " + (effectue ? "Effectué" : "À faire");
    }
}
