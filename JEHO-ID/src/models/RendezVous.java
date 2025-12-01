package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RendezVous {
    private int id;
    private Patient patient;
    private Medecin medecin;
    private LocalDateTime dateHeure;
    private String motif;
    private String statut; // "PROGRAMMÉ", "CONFIRMÉ", "ANNULÉ", "TERMINÉ"
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    public RendezVous() {}
    
    public RendezVous(int id, Patient patient, Medecin medecin, LocalDateTime dateHeure, String motif) {
        this.id = id;
        this.patient = patient;
        this.medecin = medecin;
        this.dateHeure = dateHeure;
        this.motif = motif;
        this.statut = "PROGRAMMÉ";
    }
    
    // Getters
    public int getId() { return id; }
    public Patient getPatient() { return patient; }
    public Medecin getMedecin() { return medecin; }
    public LocalDateTime getDateHeure() { return dateHeure; }
    public String getMotif() { return motif; }
    public String getStatut() { return statut; }
    
    // Setters
    public void setId(int id) { this.id = id; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public void setMedecin(Medecin medecin) { this.medecin = medecin; }
    public void setDateHeure(LocalDateTime dateHeure) { this.dateHeure = dateHeure; }
    public void setMotif(String motif) { this.motif = motif; }
    public void setStatut(String statut) { this.statut = statut; }
    
    // Méthodes utilitaires
    public String getDateHeureFormatee() {
        return dateHeure != null ? dateHeure.format(FORMATTER) : "N/A";
    }
    
    public boolean estDisponible() {
        return "PROGRAMMÉ".equals(statut) || "CONFIRMÉ".equals(statut);
    }
    
    public void confirmer() {
        this.statut = "CONFIRMÉ";
    }
    
    public void annuler() {
        this.statut = "ANNULÉ";
    }
    
    public void terminer() {
        this.statut = "TERMINÉ";
    }
    
    @Override
    public String toString() {
        return "Rendez-vous #" + id + "\n" +
               "  Patient : " + patient.getNom() + " " + patient.getPrenom() + " (ID: " + patient.getId() + ")\n" +
               "  Médecin : " + medecin.getNom() + " " + medecin.getPrenom() + "\n" +
               "  Date/Heure : " + getDateHeureFormatee() + "\n" +
               "  Motif : " + motif + "\n" +
               "  Statut : " + statut;
    }
}
