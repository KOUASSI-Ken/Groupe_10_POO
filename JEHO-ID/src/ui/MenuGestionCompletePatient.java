package ui;

import models.Patient;
import services.PatientService;
import java.util.List;
import java.util.Scanner;

public class MenuGestionCompletePatient {
    
    private final PatientService patientService;
    private final Scanner scanner;
    
    public MenuGestionCompletePatient(PatientService patientService) {
        this.patientService = patientService;
        this.scanner = new Scanner(System.in);
    }
    
    public void afficherMenu() {
        while (true) {
            System.out.println("\n===== GESTION COMPLÈTE DES PATIENTS =====");
            System.out.println("1. Créer un nouveau patient");
            System.out.println("2. Consulter un patient (par ID)");
            System.out.println("3. Rechercher des patients (par nom)");
            System.out.println("4. Mettre à jour un patient");
            System.out.println("5. Archiver un patient");
            System.out.println("6. Lister tous les patients actifs");
            System.out.println("7. Statistiques des patients");
            System.out.println("0. Retour");
            System.out.print("Choix : ");
            
            try {
                int choix = Integer.parseInt(scanner.nextLine());
                
                switch (choix) {
                    case 1 -> creerPatient();
                    case 2 -> consulterPatient();
                    case 3 -> rechercherPatients();
                    case 4 -> mettreAJourPatient();
                    case 5 -> archiverPatient();
                    case 6 -> listerPatientsActifs();
                    case 7 -> afficherStatistiques();
                    case 0 -> { return; }
                    default -> System.out.println("❌ Choix invalide !");
                }
            } catch (Exception e) {
                System.out.println("❌ Erreur : " + e.getMessage());
            }
        }
    }
    
    private void creerPatient() {
        System.out.println("\n--- CRÉATION D'UN NOUVEAU PATIENT ---");
        
        try {
            System.out.print("ID : ");
            int id = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Nom : ");
            String nom = scanner.nextLine();
            
            System.out.print("Prénom : ");
            String prenom = scanner.nextLine();
            
            System.out.print("Date de naissance (jj/mm/aaaa) : ");
            String dateNaissance = scanner.nextLine();
            
            System.out.print("Sexe (M/F) : ");
            String sexe = scanner.nextLine();
            
            Patient patient = new Patient(id, nom, prenom, dateNaissance, sexe);
            patientService.ajouterPatient(patient);
            
            System.out.println("✅ Patient créé avec succès !");
            System.out.println(patient);
            
        } catch (Exception e) {
            System.out.println("❌ Erreur lors de la création : " + e.getMessage());
        }
    }
    
    private void consulterPatient() {
        System.out.println("\n--- CONSULTATION D'UN PATIENT ---");
        
        System.out.print("ID du patient : ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Patient patient = patientService.rechercherPatient(id);
            
            if (patient != null) {
                System.out.println("\n📋 DOSSIER DU PATIENT");
                System.out.println(patient);
                System.out.println("\n📁 DOSSIER MÉDICAL");
                System.out.println(patient.getDossier());
            } else {
                System.out.println("❌ Patient non trouvé.");
            }
        } catch (Exception e) {
            System.out.println("❌ ID invalide.");
        }
    }
    
    private void rechercherPatients() {
        System.out.println("\n--- RECHERCHE DE PATIENTS ---");
        
        System.out.print("Nom à rechercher : ");
        String nom = scanner.nextLine();
        
        List<Patient> resultats = patientService.rechercherParNom(nom);
        
        if (resultats.isEmpty()) {
            System.out.println("❌ Aucun patient trouvé pour : " + nom);
        } else {
            System.out.println("\n🔍 RÉSULTATS (" + resultats.size() + ") :");
            for (Patient p : resultats) {
                System.out.println("\n" + p);
            }
        }
    }
    
    private void mettreAJourPatient() {
        System.out.println("\n--- MISE À JOUR D'UN PATIENT ---");
        
        System.out.print("ID du patient à modifier : ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Patient patient = patientService.rechercherPatient(id);
            
            if (patient == null) {
                System.out.println("❌ Patient non trouvé.");
                return;
            }
            
            System.out.println("Patient actuel :");
            System.out.println(patient);
            
            System.out.println("\nNouvelles informations (laisser vide pour conserver):");
            
            System.out.print("Nom [" + patient.getNom() + "] : ");
            String nom = scanner.nextLine();
            if (!nom.isEmpty()) patient.setNom(nom);
            
            System.out.print("Prénom [" + patient.getPrenom() + "] : ");
            String prenom = scanner.nextLine();
            if (!prenom.isEmpty()) patient.setPrenom(prenom);
            
            System.out.print("Date de naissance [" + patient.getDateNaissanceFormatee() + "] : ");
            String dateNaissance = scanner.nextLine();
            if (!dateNaissance.isEmpty()) patient.setDateNaissance(dateNaissance);
            
            System.out.print("Sexe [" + patient.getSexe() + "] : ");
            String sexe = scanner.nextLine();
            if (!sexe.isEmpty()) patient.setSexe(sexe);
            
            if (patientService.mettreAJourPatient(patient)) {
                System.out.println("✅ Patient mis à jour avec succès !");
                System.out.println(patient);
            } else {
                System.out.println("❌ Erreur lors de la mise à jour.");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
    }
    
    private void archiverPatient() {
        System.out.println("\n--- ARCHIVAGE D'UN PATIENT ---");
        
        System.out.print("ID du patient à archiver : ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Patient patient = patientService.rechercherPatient(id);
            
            if (patient == null) {
                System.out.println("❌ Patient non trouvé.");
                return;
            }
            
            System.out.println("Patient à archiver :");
            System.out.println(patient);
            
            System.out.print("Confirmer l'archivage ? (O/N) : ");
            String confirmation = scanner.nextLine();
            
            if ("O".equalsIgnoreCase(confirmation)) {
                if (patientService.archiverPatient(id)) {
                    System.out.println("✅ Patient archivé avec succès !");
                } else {
                    System.out.println("❌ Erreur lors de l'archivage.");
                }
            } else {
                System.out.println("❌ Archivage annulé.");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
    }
    
    private void listerPatientsActifs() {
        System.out.println("\n--- LISTE DES PATIENTS ACTIFS ---");
        
        List<Patient> patients = patientService.getPatientsActifs();
        
        if (patients.isEmpty()) {
            System.out.println("❌ Aucun patient actif.");
        } else {
            System.out.println("📋 PATIENTS ACTIFS (" + patients.size() + ") :");
            for (Patient p : patients) {
                System.out.println("\n" + p);
            }
        }
    }
    
    private void afficherStatistiques() {
        System.out.println("\n--- STATISTIQUES DES PATIENTS ---");
        
        int total = patientService.getNombrePatients();
        int actifs = patientService.getPatientsActifs().size();
        int archives = total - actifs;
        
        System.out.println("📊 STATISTIQUES :");
        System.out.println("  Total des patients : " + total);
        System.out.println("  Patients actifs : " + actifs);
        System.out.println("  Patients archivés : " + archives);
        System.out.println("  Taux d'archivage : " + 
            (total > 0 ? String.format("%.1f", (double) archives / total * 100) : "0") + "%");
    }
}
