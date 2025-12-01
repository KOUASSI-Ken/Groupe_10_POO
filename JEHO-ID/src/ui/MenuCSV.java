package ui;

import services.*;
import java.util.Scanner;

public class MenuCSV {
    
    private final CSVService csvService;
    private final PatientService patientService;
    private final ProfessionnelService professionnelService;
    private final Scanner scanner;
    
    public MenuCSV(CSVService csvService, 
                   PatientService patientService,
                   ProfessionnelService professionnelService) {
        this.csvService = csvService;
        this.patientService = patientService;
        this.professionnelService = professionnelService;
        this.scanner = new Scanner(System.in);
    }
    
    public void afficherMenu() {
        while (true) {
            System.out.println("\n===== IMPORT/EXPORT CSV =====");
            System.out.println("1. Exporter les patients");
            System.out.println("2. Importer les patients");
            System.out.println("3. Exporter les consultations");
            System.out.println("4. Exporter les médecins");
            System.out.println("5. Exporter les infirmiers");
            System.out.println("6. Exporter les pharmaciens");
            System.out.println("7. Exporter les statistiques");
            System.out.println("8. Exporter un rapport complet");
            System.out.println("0. Retour");
            System.out.print("Choix : ");
            
            try {
                int choix = Integer.parseInt(scanner.nextLine());
                
                switch (choix) {
                    case 1 -> exporterPatients();
                    case 2 -> importerPatients();
                    case 3 -> exporterConsultations();
                    case 4 -> exporterMedecins();
                    case 5 -> exporterInfirmiers();
                    case 6 -> exporterPharmaciens();
                    case 7 -> exporterStatistiques();
                    case 8 -> exporterRapportComplet();
                    case 0 -> { return; }
                    default -> System.out.println("❌ Choix invalide !");
                }
            } catch (Exception e) {
                System.out.println("❌ Erreur : " + e.getMessage());
            }
        }
    }
    
    private void exporterPatients() {
        System.out.println("\n--- EXPORT DES PATIENTS ---");
        System.out.print("Nom du fichier (ex: patients.csv) : ");
        String fichier = scanner.nextLine();
        
        if (csvService.exporterPatients(patientService.getTousLesPatients(), fichier)) {
            System.out.println("✅ Export réussi : " + fichier);
            System.out.println("📊 " + patientService.getTousLesPatients().size() + " patients exportés");
        } else {
            System.out.println("❌ Erreur lors de l'export");
        }
    }
    
    private void importerPatients() {
        System.out.println("\n--- IMPORT DES PATIENTS ---");
        System.out.print("Nom du fichier à importer : ");
        String fichier = scanner.nextLine();
        
        var patients = csvService.importerPatients(fichier);
        if (!patients.isEmpty()) {
            System.out.println("✅ Import réussi !");
            System.out.println("📊 " + patients.size() + " patients lus");
            
            System.out.print("Voulez-vous ajouter ces patients au système ? (O/N) : ");
            String confirmation = scanner.nextLine();
            
            if ("O".equalsIgnoreCase(confirmation)) {
                int succes = 0;
                int echecs = 0;
                
                for (var patient : patients) {
                    try {
                        patientService.ajouterPatient(patient);
                        succes++;
                    } catch (Exception e) {
                        echecs++;
                        System.err.println("❌ Erreur patient " + patient.getId() + ": " + e.getMessage());
                    }
                }
                
                System.out.println("✅ " + succes + " patients ajoutés");
                if (echecs > 0) {
                    System.out.println("❌ " + echecs + " patients en erreur");
                }
            }
        } else {
            System.out.println("❌ Aucun patient importé ou fichier non trouvé");
        }
    }
    
    private void exporterConsultations() {
        System.out.println("\n--- EXPORT DES CONSULTATIONS ---");
        System.out.println("⚠️ Fonctionnalité non disponible - ConsultationService requis");
        System.out.print("Nom du fichier (ex: consultations.csv) : ");
        String fichier = scanner.nextLine();
        
        // Simulation pour le moment
        System.out.println("❌ Export des consultations en cours de développement");
    }
    
    private void exporterMedecins() {
        System.out.println("\n--- EXPORT DES MÉDECINS ---");
        System.out.print("Nom du fichier (ex: medecins.csv) : ");
        String fichier = scanner.nextLine();
        
        if (csvService.exporterMedecins(professionnelService.getTousMedecins(), fichier)) {
            System.out.println("✅ Export réussi : " + fichier);
            System.out.println("📊 " + professionnelService.getTousMedecins().size() + " médecins exportés");
        } else {
            System.out.println("❌ Erreur lors de l'export");
        }
    }
    
    private void exporterInfirmiers() {
        System.out.println("\n--- EXPORT DES INFIRMIERS ---");
        System.out.print("Nom du fichier (ex: infirmiers.csv) : ");
        String fichier = scanner.nextLine();
        
        // Simulation pour le moment
        System.out.println("❌ Export des infirmiers en cours de développement");
    }
    
    private void exporterPharmaciens() {
        System.out.println("\n--- EXPORT DES PHARMACIENS ---");
        System.out.print("Nom du fichier (ex: pharmaciens.csv) : ");
        String fichier = scanner.nextLine();
        
        // Simulation pour le moment
        System.out.println("❌ Export des pharmaciens en cours de développement");
    }
    
    private void exporterStatistiques() {
        System.out.println("\n--- EXPORT DES STATISTIQUES ---");
        System.out.print("Nom du fichier (ex: statistiques.csv) : ");
        String fichier = scanner.nextLine();
        
        int nbPatients = patientService.getNombrePatients();
        int nbPatientsActifs = patientService.getPatientsActifs().size();
        int nbPatientsArchives = nbPatients - nbPatientsActifs;
        
        if (csvService.exporterStatistiques(
            nbPatients, nbPatientsActifs, nbPatientsArchives,
            professionnelService.getNombreMedecins(),
            professionnelService.getNombreInfirmiers(),
            professionnelService.getNombrePharmaciens(),
            0, // consultations non disponibles pour le moment
            fichier)) {
            System.out.println("✅ Export réussi : " + fichier);
            System.out.println("📊 Statistiques exportées");
        } else {
            System.out.println("❌ Erreur lors de l'export");
        }
    }
    
    private void exporterRapportComplet() {
        System.out.println("\n--- EXPORT RAPPORT COMPLET ---");
        System.out.print("Répertoire de destination (ex: exports) : ");
        String repertoire = scanner.nextLine();
        
        // Créer le répertoire si nécessaire
        java.io.File dir = new java.io.File(repertoire);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                System.out.println("✅ Répertoire créé : " + repertoire);
            } else {
                System.out.println("❌ Impossible de créer le répertoire");
                return;
            }
        }
        
        if (csvService.exporterRapportComplet(patientService, null, professionnelService, repertoire)) {
            System.out.println("✅ Rapport complet exporté dans : " + repertoire);
            System.out.println("📁 Fichiers créés :");
            System.out.println("  - patients_YYYY-MM-DD.csv");
            System.out.println("  - consultations_YYYY-MM-DD.csv");
            System.out.println("  - medecins_YYYY-MM-DD.csv");
            System.out.println("  - infirmiers_YYYY-MM-DD.csv");
            System.out.println("  - pharmaciens_YYYY-MM-DD.csv");
            System.out.println("  - statistiques_YYYY-MM-DD.csv");
        } else {
            System.out.println("❌ Erreur lors de l'export du rapport");
        }
    }
}
