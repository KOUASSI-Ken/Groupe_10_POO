package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Antecedent {
    private String description;
    private LocalDate date;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Antecedent() {}

    public Antecedent(String description, String dateStr) {
        this.description = description;
        this.date = LocalDate.parse(dateStr, FORMATTER);
    }

    // Getters
    public String getDescription() { return description; }
    public LocalDate getDate() { return date; }
    public String getDateFormatee() { 
        return date != null ? date.format(FORMATTER) : "Date Pas disponible "; 
    }

    // Setters
    public void setDescription(String description) { 
        this.description = description; 
    }
    
    public void setDate(String dateStr) { 
        this.date = LocalDate.parse(dateStr, FORMATTER); 
    }
    
    public void setDate(LocalDate date) { 
        this.date = date; 
    }

    @Override
    public String toString() {
        return "Antécédent:\n" +
               "  Description: " + description + "\n" +
               "  Date: " + getDateFormatee();
    }
}