package models;

import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class EmploiDuTempsInfirmier {
    private Infirmier infirmier;
    private List<Shift> shifts;
    
    public EmploiDuTempsInfirmier(Infirmier infirmier) {
        this.infirmier = infirmier;
        this.shifts = new ArrayList<>();
        genererShiftsParDefaut();
    }
    
    private void genererShiftsParDefaut() {
        // Générer des shifts pour la semaine (matin/soir, rotation)
        String[] typesShift = {"Matin", "Soir", "Nuit"};
        LocalDateTime debutSemaine = LocalDateTime.now().with(DayOfWeek.MONDAY).withHour(0).withMinute(0);
        
        for (int i = 0; i < 7; i++) { // 7 jours
            LocalDateTime jour = debutSemaine.plusDays(i);
            
            if (jour.getDayOfWeek() == DayOfWeek.SATURDAY || jour.getDayOfWeek() == DayOfWeek.SUNDAY) {
                // Week-end : moins de personnel
                if (i % 2 == 0) { // Un jour sur deux le week-end
                    shifts.add(new Shift(jour.withHour(8).withMinute(0), jour.withHour(20).withMinute(0), "Week-end"));
                }
            } else {
                // Semaine : rotation matin/soir/nuit
                String typeShift = typesShift[i % 3];
                LocalDateTime debut, fin;
                
                switch (typeShift) {
                    case "Matin":
                        debut = jour.withHour(7).withMinute(0);
                        fin = jour.withHour(15).withMinute(0);
                        break;
                    case "Soir":
                        debut = jour.withHour(15).withMinute(0);
                        fin = jour.withHour(23).withMinute(0);
                        break;
                    case "Nuit":
                        debut = jour.withHour(23).withMinute(0);
                        fin = jour.plusDays(1).withHour(7).withMinute(0);
                        break;
                    default:
                        debut = jour.withHour(8).withMinute(0);
                        fin = jour.withHour(16).withMinute(0);
                }
                
                shifts.add(new Shift(debut, fin, typeShift));
            }
        }
    }
    
    public List<Shift> getShiftsJour(LocalDateTime date) {
        List<Shift> shiftsJour = new ArrayList<>();
        for (Shift shift : shifts) {
            if (estDansLeJour(shift, date)) {
                shiftsJour.add(shift);
            }
        }
        return shiftsJour;
    }
    
    public boolean estEnService(LocalDateTime moment) {
        for (Shift shift : shifts) {
            if (!moment.isBefore(shift.getDebut()) && moment.isBefore(shift.getFin())) {
                return true;
            }
        }
        return false;
    }
    
    public String getPosteActuel(LocalDateTime moment) {
        for (Shift shift : shifts) {
            if (!moment.isBefore(shift.getDebut()) && moment.isBefore(shift.getFin())) {
                return shift.getType();
            }
        }
        return "Hors service";
    }
    
    private boolean estDansLeJour(Shift shift, LocalDateTime date) {
        LocalDateTime debutJour = date.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime finJour = debutJour.plusDays(1);
        
        return !shift.getDebut().isBefore(debutJour) && shift.getDebut().isBefore(finJour);
    }
    
    // Getters
    public Infirmier getInfirmier() { return infirmier; }
    public List<Shift> getShifts() { return new ArrayList<>(shifts); }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Emploi du temps de l'infirmier(ère) ").append(infirmier.getNom()).append(" ").append(infirmier.getPrenom()).append("\n");
        
        for (DayOfWeek jour : DayOfWeek.values()) {
            if (jour == DayOfWeek.SATURDAY || jour == DayOfWeek.SUNDAY) continue;
            
            sb.append("\n").append(jour).append(" :\n");
            List<Shift> shiftsJour = getShiftsJour(LocalDateTime.now().with(jour));
            
            for (Shift shift : shiftsJour) {
                sb.append("  ").append(shift.getType())
                  .append(" : ").append(shift.getDebut().toLocalTime())
                  .append(" - ").append(shift.getFin().toLocalTime())
                  .append("\n");
            }
        }
        
        return sb.toString();
    }
}
