package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Consultation {
    private LocalDate date;
    private String motif;
    private String diagnostic;
    private String medecin;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Consultation() {}

    public Consultation(String dateStr, String motif, String diagnostic, String medecin) {
        this.date = LocalDate.parse(dateStr, FORMATTER);
        this.motif = motif;
        this.diagnostic = diagnostic;
        this.medecin = medecin;
    }

    // Getters
    public LocalDate getDate() { return date; }
    public String getDateFormatee() { 
        return date != null ? date.format(FORMATTER) : "N/A"; 
    }
    public String getMotif() { return motif; }
    public String getDiagnostic() { return diagnostic; }
    public String getMedecin() { return medecin; }

    // Setters
    public void setDate(String dateStr) { 
        this.date = LocalDate.parse(dateStr, FORMATTER); 
    }
    public void setDate(LocalDate date) { this.date = date; }
    public void setMotif(String motif) { this.motif = motif; }
    public void setDiagnostic(String diagnostic) { this.diagnostic = diagnostic; }
    public void setMedecin(String medecin) { this.medecin = medecin; }

    @Override
    public String toString() {
        return "CONSULTATION DU " + getDateFormatee() + "\n" +
               "  Médecin: " + medecin + "\n" +
               "  Motif: " + motif + "\n" +
               "  Diagnostic: " + diagnostic;
    }
}