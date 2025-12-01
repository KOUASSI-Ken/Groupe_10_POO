package models;

import java.time.LocalDateTime;

public class Shift {
    private LocalDateTime debut;
    private LocalDateTime fin;
    private String type; // Matin, Soir, Nuit, Week-end
    private String service; // Urgence, Chirurgie, Pédiatrie, etc.
    
    public Shift(LocalDateTime debut, LocalDateTime fin, String type) {
        this.debut = debut;
        this.fin = fin;
        this.type = type;
        assignerServiceParDefaut();
    }
    
    private void assignerServiceParDefaut() {
        // Assigner un service selon le type de shift
        switch (type) {
            case "Matin":
                this.service = "Chirurgie";
                break;
            case "Soir":
                this.service = "Médecine générale";
                break;
            case "Nuit":
                this.service = "Urgence";
                break;
            case "Week-end":
                this.service = "Urgence";
                break;
            default:
                this.service = "Non assigné";
        }
    }
    
    // Getters et Setters
    public LocalDateTime getDebut() { return debut; }
    public LocalDateTime getFin() { return fin; }
    public String getType() { return type; }
    public String getService() { return service; }
    
    public void setDebut(LocalDateTime debut) { this.debut = debut; }
    public void setFin(LocalDateTime fin) { this.fin = fin; }
    public void setType(String type) { 
        this.type = type;
        assignerServiceParDefaut();
    }
    public void setService(String service) { this.service = service; }
    
    public long getDureeHeures() {
        return java.time.Duration.between(debut, fin).toHours();
    }
    
    public boolean estEnCours(LocalDateTime moment) {
        return !moment.isBefore(debut) && moment.isBefore(fin);
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s) : %s - %s (%dh) - Service: %s",
            type, debut.getDayOfWeek(),
            debut.toLocalTime(), fin.toLocalTime(),
            getDureeHeures(), service);
    }
}
