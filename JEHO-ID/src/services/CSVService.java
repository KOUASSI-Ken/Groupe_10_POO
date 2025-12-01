package services;

import models.Patient;
import models.Consultation;
import models.Medecin;
import models.Infirmier;
import models.Pharmacien;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CSVService {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String SEPARATOR = ";";
    
    // Exporter les patients vers CSV
    public boolean exporterPatients(List<Patient> patients, String fichier) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fichier))) {
            
            // En-tête CSV
            writer.println("ID;Nom;Prénom;DateNaissance;Sexe;Age;Statut");
            
            // Données patients
            for (Patient patient : patients) {
                String ligne = String.join(SEPARATOR,
                    String.valueOf(patient.getId()),
                    patient.getNom(),
                    patient.getPrenom(),
                    patient.getDateNaissanceFormatee(),
                    patient.getSexe(),
                    String.valueOf(patient.getAge()),
                    patient.getNom().startsWith("[ARCHIVÉ]") ? "Archivé" : "Actif"
                );
                writer.println(ligne);
            }
            
            return true;
        } catch (IOException e) {
            System.err.println("Erreur lors de l'export des patients : " + e.getMessage());
            return false;
        }
    }
    
    // Importer les patients depuis CSV
    public List<Patient> importerPatients(String fichier) {
        List<Patient> patients = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            boolean premiereLigne = true;
            
            while ((ligne = reader.readLine()) != null) {
                if (premiereLigne) {
                    premiereLigne = false; // Sauter l'en-tête
                    continue;
                }
                
                String[] donnees = ligne.split(SEPARATOR);
                if (donnees.length >= 5) {
                    try {
                        int id = Integer.parseInt(donnees[0].trim());
                        String nom = donnees[1].trim();
                        String prenom = donnees[2].trim();
                        String dateNaissance = donnees[3].trim();
                        String sexe = donnees[4].trim();
                        
                        Patient patient = new Patient(id, nom, prenom, dateNaissance, sexe);
                        patients.add(patient);
                    } catch (NumberFormatException e) {
                        System.err.println("Erreur de format pour la ligne : " + ligne);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'import des patients : " + e.getMessage());
        }
        
        return patients;
    }
    
    // Exporter les consultations vers CSV
    public boolean exporterConsultations(List<Consultation> consultations, String fichier) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fichier))) {
            
            // En-tête CSV
            writer.println("IDConsultation;Date;Motif;Diagnostic;Medecin;IDPatient;NomPatient;PrenomPatient");
            
            // Données consultations
            for (Consultation consultation : consultations) {
                String ligne = String.join(SEPARATOR,
                    "CONSULTATION", // ID simulé
                    consultation.getDate().toString(),
                    consultation.getMotif(),
                    consultation.getDiagnostic(),
                    consultation.getMedecin(),
                    "", // IDPatient (serait ajouté si disponible)
                    "", // NomPatient
                    ""  // PrenomPatient
                );
                writer.println(ligne);
            }
            
            return true;
        } catch (IOException e) {
            System.err.println("Erreur lors de l'export des consultations : " + e.getMessage());
            return false;
        }
    }
    
    // Exporter les médecins vers CSV
    public boolean exporterMedecins(List<Medecin> medecins, String fichier) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fichier))) {
            
            // En-tête CSV
            writer.println("ID;Nom;Prénom;Age;Specialite;Role");
            
            // Données médecins
            for (Medecin medecin : medecins) {
                String ligne = String.join(SEPARATOR,
                    String.valueOf(medecin.getId()),
                    medecin.getNom(),
                    medecin.getPrenom(),
                    String.valueOf(medecin.getAge()),
                    medecin.getSpecialite(),
                    medecin.getRole()
                );
                writer.println(ligne);
            }
            
            return true;
        } catch (IOException e) {
            System.err.println("Erreur lors de l'export des médecins : " + e.getMessage());
            return false;
        }
    }
    
    // Exporter les infirmiers vers CSV
    public boolean exporterInfirmiers(List<Infirmier> infirmiers, String fichier) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fichier))) {
            
            // En-tête CSV
            writer.println("ID;Nom;Prénom;Age;Role");
            
            // Données infirmiers
            for (Infirmier infirmier : infirmiers) {
                String ligne = String.join(SEPARATOR,
                    String.valueOf(infirmier.getId()),
                    infirmier.getNom(),
                    infirmier.getPrenom(),
                    String.valueOf(infirmier.getAge()),
                    infirmier.getRole()
                );
                writer.println(ligne);
            }
            
            return true;
        } catch (IOException e) {
            System.err.println("Erreur lors de l'export des infirmiers : " + e.getMessage());
            return false;
        }
    }
    
    // Exporter les pharmaciens vers CSV
    public boolean exporterPharmaciens(List<Pharmacien> pharmaciens, String fichier) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fichier))) {
            
            // En-tête CSV
            writer.println("ID;Nom;Prénom;Age;Role");
            
            // Données pharmaciens
            for (Pharmacien pharmacien : pharmaciens) {
                String ligne = String.join(SEPARATOR,
                    String.valueOf(pharmacien.getId()),
                    pharmacien.getNom(),
                    pharmacien.getPrenom(),
                    String.valueOf(pharmacien.getAge()),
                    pharmacien.getRole()
                );
                writer.println(ligne);
            }
            
            return true;
        } catch (IOException e) {
            System.err.println("Erreur lors de l'export des pharmaciens : " + e.getMessage());
            return false;
        }
    }
    
    // Exporter les statistiques globales vers CSV
    public boolean exporterStatistiques(int nbPatients, int nbPatientsActifs, int nbPatientsArchives,
                                       int nbMedecins, int nbInfirmiers, int nbPharmaciens, 
                                       int nbConsultations, String fichier) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fichier))) {
            
            // En-tête CSV
            writer.println("Type;Nombre;Pourcentage;DateExport");
            String dateExport = LocalDate.now().format(DATE_FORMATTER);
            
            // Statistiques patients
            writer.println("Patients Total;" + nbPatients + ";100%;" + dateExport);
            writer.println("Patients Actifs;" + nbPatientsActifs + ";" + 
                (nbPatients > 0 ? String.format("%.1f", (double) nbPatientsActifs / nbPatients * 100) : "0") + 
                "%;" + dateExport);
            writer.println("Patients Archivés;" + nbPatientsArchives + ";" + 
                (nbPatients > 0 ? String.format("%.1f", (double) nbPatientsArchives / nbPatients * 100) : "0") + 
                "%;" + dateExport);
            
            // Statistiques professionnels
            int totalPro = nbMedecins + nbInfirmiers + nbPharmaciens;
            writer.println("Médecins;" + nbMedecins + ";" + 
                (totalPro > 0 ? String.format("%.1f", (double) nbMedecins / totalPro * 100) : "0") + 
                "%;" + dateExport);
            writer.println("Infirmiers;" + nbInfirmiers + ";" + 
                (totalPro > 0 ? String.format("%.1f", (double) nbInfirmiers / totalPro * 100) : "0") + 
                "%;" + dateExport);
            writer.println("Pharmaciens;" + nbPharmaciens + ";" + 
                (totalPro > 0 ? String.format("%.1f", (double) nbPharmaciens / totalPro * 100) : "0") + 
                "%;" + dateExport);
            
            // Statistiques consultations
            writer.println("Consultations Total;" + nbConsultations + ";100%;" + dateExport);
            
            return true;
        } catch (IOException e) {
            System.err.println("Erreur lors de l'export des statistiques : " + e.getMessage());
            return false;
        }
    }
    
    // Créer un rapport complet en CSV
    public boolean exporterRapportComplet(PatientService patientService, 
                                        ConsultationService consultationService,
                                        ProfessionnelService professionnelService,
                                        String repertoire) {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        boolean success = true;
        
        // Export patients
        success &= exporterPatients(patientService.getTousLesPatients(), 
                                  repertoire + "/patients_" + date + ".csv");
        
        // Export consultations (si disponible)
        if (consultationService != null) {
            success &= exporterConsultations(consultationService.getToutesLesConsultations(), 
                                           repertoire + "/consultations_" + date + ".csv");
        }
        
        // Export professionnels
        success &= exporterMedecins(professionnelService.getTousMedecins(), 
                                  repertoire + "/medecins_" + date + ".csv");
        success &= exporterInfirmiers(professionnelService.getTousInfirmiers(), 
                                    repertoire + "/infirmiers_" + date + ".csv");
        success &= exporterPharmaciens(professionnelService.getTousPharmaciens(), 
                                     repertoire + "/pharmaciens_" + date + ".csv");
        
        // Export statistiques
        success &= exporterStatistiques(
            patientService.getNombrePatients(),
            patientService.getPatientsActifs().size(),
            patientService.getNombrePatients() - patientService.getPatientsActifs().size(),
            professionnelService.getNombreMedecins(),
            professionnelService.getNombreInfirmiers(),
            professionnelService.getNombrePharmaciens(),
            consultationService.getNombreConsultations(),
            repertoire + "/statistiques_" + date + ".csv"
        );
        
        return success;
    }
}
