package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Prescription {
    private String nomProduit;
    private String posologie;
    private LocalDate date;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Prescription() {}

    public Prescription(String nomProduit, String posologie, LocalDate date) {
        this.nomProduit = nomProduit;
        this.posologie = posologie;
        this.date = date;
    }

    public Prescription(String nomProduit, String posologie, String dateStr) {
        this.nomProduit = nomProduit;
        this.posologie = posologie;
        this.date = LocalDate.parse(dateStr, FORMATTER);
    }

    // Getters
    public String getNomProduit() { return nomProduit; }
    public String getPosologie() { return posologie; }
    public LocalDate getDate() { return date; }
    public String getDateFormatee() { 
        return date != null ? date.format(FORMATTER) : "Date Indisponible"; 
    }

    // Setters
    public void setNomProduit(String nomProduit) { this.nomProduit = nomProduit; }
    public void setPosologie(String posologie) { this.posologie = posologie; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setDate(String dateStr) { 
        this.date = LocalDate.parse(dateStr, FORMATTER); 
    }

    @Override
    public String toString() {
        return nomProduit + " - Prendre " + posologie + "\n" +
               "  Prescrit le: " + getDateFormatee();
    }
}