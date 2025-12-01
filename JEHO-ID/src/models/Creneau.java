package models;

import java.time.LocalDateTime;

public class Creneau {
    private LocalDateTime debut;
    private LocalDateTime fin;
    private boolean occupe;
    private String motif;
    private Patient patient;
    
    public Creneau(LocalDateTime debut, LocalDateTime fin, boolean occupe) {
        this.debut = debut;
        this.fin = fin;
        this.occupe = occupe;
    }
    
    public Creneau(LocalDateTime debut, LocalDateTime fin, boolean occupe, String motif, Patient patient) {
        this.debut = debut;
        this.fin = fin;
        this.occupe = occupe;
        this.motif = motif;
        this.patient = patient;
    }
    
    // Getters et Setters
    public LocalDateTime getDebut() { return debut; }
    public LocalDateTime getFin() { return fin; }
    public boolean isOccupe() { return occupe; }
    public String getMotif() { return motif; }
    public Patient getPatient() { return patient; }
    
    public void setDebut(LocalDateTime debut) { this.debut = debut; }
    public void setFin(LocalDateTime fin) { this.fin = fin; }
    public void setOccupe(boolean occupe) { this.occupe = occupe; }
    public void setMotif(String motif) { this.motif = motif; }
    public void setPatient(Patient patient) { this.patient = patient; }
    
    public void assignerRendezVous(Patient patient, String motif) {
        this.occupe = true;
        this.patient = patient;
        this.motif = motif;
    }
    
    public void liberer() {
        this.occupe = false;
        this.patient = null;
        this.motif = null;
    }
    
    @Override
    public String toString() {
        if (occupe && patient != null) {
            return String.format("%s - %s : %s (%s %s) - %s",
                debut.toLocalTime(), fin.toLocalTime(),
                patient.getNom() + " " + patient.getPrenom(),
                patient.getId(), motif != null ? motif : "Rendez-vous");
        } else {
            return String.format("%s - %s : %s",
                debut.toLocalTime(), fin.toLocalTime(),
                occupe ? "Indisponible" : "Disponible");
        }
    }
}
