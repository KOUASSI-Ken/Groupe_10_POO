package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DossierMedical {
    private List<Consultation> consultations;
    private List<Antecedent> antecedents;
    private List<Prescription> prescriptions;
    private List<ExamenMedical> examens;

    public DossierMedical() {
        consultations = new ArrayList<>();
        antecedents = new ArrayList<>();
        prescriptions = new ArrayList<>();
        examens = new ArrayList<>();
    }

    
    public void ajouterConsultation(Consultation c) { consultations.add(c); }
    public void ajouterAntecedent(Antecedent a) { antecedents.add(a); }
    public void ajouterPrescription(Prescription p) { prescriptions.add(p); }
    public void ajouterExamen(ExamenMedical e) { examens.add(e); }

    
    public boolean supprimerConsultation(Consultation c) { 
        return consultations.remove(c); 
    }
    public boolean supprimerAntecedent(Antecedent a) { 
        return antecedents.remove(a); 
    }
    public boolean supprimerPrescription(Prescription p) { 
        return prescriptions.remove(p); 
    }
    public boolean supprimerExamen(ExamenMedical e) { 
        return examens.remove(e); 
    }

   
    public Consultation rechercherConsultation(LocalDate date) {
        for (Consultation c : consultations) {
            if (c.getDate().equals(date)) return c;
        }
        return null;
    }

    public Prescription rechercherPrescription(String nomProduit) {
        for (Prescription p : prescriptions) {
            if (p.getNomProduit().equalsIgnoreCase(nomProduit)) return p;
        }
        return null;
    }

    // Getters
    public List<Consultation> getConsultations() { return consultations; }
    public List<Antecedent> getAntecedents() { return antecedents; }
    public List<Prescription> getPrescriptions() { return prescriptions; }
    public List<ExamenMedical> getExamens() { return examens; }
    
    // Méthode pour vider tout le dossier
    public void viderDossier() {
        consultations.clear();
        antecedents.clear();
        prescriptions.clear();
        examens.clear();
    }
}