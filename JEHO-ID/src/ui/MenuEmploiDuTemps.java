package ui;

import models.*;
import services.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuEmploiDuTemps {
    
    private final EmploiDuTempsService emploiDuTempsService;
    private final ProfessionnelService professionnelService;
    private final Scanner scanner;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    public MenuEmploiDuTemps(EmploiDuTempsService emploiDuTempsService, 
                           ProfessionnelService professionnelService) {
        this.emploiDuTempsService = emploiDuTempsService;
        this.professionnelService = professionnelService;
        this.scanner = new Scanner(System.in);
    }
    
    public void afficherMenu() {
        while (true) {
            System.out.println("\n===== GESTION DES EMPLOIS DU TEMPS =====");
            System.out.println("1. Voir emploi du temps médecin");
            System.out.println("2. Voir emploi du temps infirmier");
            System.out.println("3. Voir créneaux disponibles médecin");
            System.out.println("4. Réserver un créneau");
            System.out.println("5. Voir infirmiers en service actuellement");
            System.out.println("6. Statistiques des services");
            System.out.println("7. Initialiser tous les emplois du temps");
            System.out.println("0. Retour");
            System.out.print("Choix : ");
            
            try {
                int choix = Integer.parseInt(scanner.nextLine());
                
                switch (choix) {
                    case 1 -> voirEmploiDuTempsMedecin();
                    case 2 -> voirEmploiDuTempsInfirmier();
                    case 3 -> voirCreneauxDisponibles();
                    case 4 -> reserverCreneau();
                    case 5 -> voirInfirmiersEnService();
                    case 6 -> voirStatistiquesServices();
                    case 7 -> initialiserEmploisDuTemps();
                    case 0 -> { return; }
                    default -> System.out.println("❌ Choix invalide !");
                }
            } catch (Exception e) {
                System.out.println("❌ Erreur : " + e.getMessage());
            }
        }
    }
    
    private void voirEmploiDuTempsMedecin() {
        System.out.println("\n--- EMPLOI DU TEMPS MÉDECIN ---");
        System.out.print("ID du médecin : ");
        
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Medecin medecin = (Medecin) professionnelService.rechercherProfessionnel(id);
            
            if (medecin != null && "medecin".equals(medecin.getRole())) {
                System.out.println("\n" + emploiDuTempsService.afficherEmploiDuTempsMedecin(medecin));
            } else {
                System.out.println("❌ Médecin non trouvé.");
            }
        } catch (Exception e) {
            System.out.println("❌ ID invalide.");
        }
    }
    
    private void voirEmploiDuTempsInfirmier() {
        System.out.println("\n--- EMPLOI DU TEMPS INFIRMIER ---");
        System.out.print("ID de l'infirmier : ");
        
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Infirmier infirmier = (Infirmier) professionnelService.rechercherProfessionnel(id);
            
            if (infirmier != null && "infirmier".equals(infirmier.getRole())) {
                System.out.println("\n" + emploiDuTempsService.afficherEmploiDuTempsInfirmier(infirmier));
            } else {
                System.out.println("❌ Infirmier non trouvé.");
            }
        } catch (Exception e) {
            System.out.println("❌ ID invalide.");
        }
    }
    
    private void voirCreneauxDisponibles() {
        System.out.println("\n--- CRÉNEAUX DISPONIBLES ---");
        System.out.print("ID du médecin : ");
        
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Medecin medecin = (Medecin) professionnelService.rechercherProfessionnel(id);
            
            if (medecin != null && "medecin".equals(medecin.getRole())) {
                System.out.print("Date (jj/mm/aaaa) : ");
                String dateStr = scanner.nextLine();
                LocalDateTime date = LocalDateTime.parse(dateStr + " 00:00", 
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                
                List<Creneau> creneaux = emploiDuTempsService.getCreneauxDisponiblesMedecin(id, date);
                
                if (creneaux.isEmpty()) {
                    System.out.println("❌ Aucun créneau disponible pour cette date.");
                } else {
                    System.out.println("\n🕐 CRÉNEAUX DISPONIBLES (" + creneaux.size() + ") :");
                    for (Creneau creneau : creneaux) {
                        System.out.println("  " + creneau.getDebut().toLocalTime() + 
                                         " - " + creneau.getFin().toLocalTime());
                    }
                }
            } else {
                System.out.println("❌ Médecin non trouvé.");
            }
        } catch (Exception e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
    }
    
    private void reserverCreneau() {
        System.out.println("\n--- RÉSERVATION D'UN CRÉNEAU ---");
        
        try {
            System.out.print("ID du médecin : ");
            int idMedecin = Integer.parseInt(scanner.nextLine());
            Medecin medecin = (Medecin) professionnelService.rechercherProfessionnel(idMedecin);
            
            if (medecin == null || !"medecin".equals(medecin.getRole())) {
                System.out.println("❌ Médecin non trouvé.");
                return;
            }
            
            System.out.print("ID du patient : ");
            int idPatient = Integer.parseInt(scanner.nextLine());
            // Ici il faudrait un PatientService pour vérifier le patient
            
            System.out.print("Date et heure (jj/mm/aaaa hh:mm) : ");
            String dateHeureStr = scanner.nextLine();
            LocalDateTime dateHeure = LocalDateTime.parse(dateHeureStr, FORMATTER);
            
            System.out.print("Motif : ");
            String motif = scanner.nextLine();
            
            // Simulation d'un patient
            Patient patient = new Patient(idPatient, "Test", "Patient", "01/01/1990", "M");
            
            if (emploiDuTempsService.reserverCreneauMedecin(idMedecin, dateHeure, patient, motif)) {
                System.out.println("✅ Créneau réservé avec succès !");
            } else {
                System.out.println("❌ Créneau non disponible.");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
    }
    
    private void voirInfirmiersEnService() {
        System.out.println("\n--- INFIRMIERS EN SERVICE ACTUELLEMENT ---");
        
        LocalDateTime maintenant = LocalDateTime.now();
        List<Infirmier> infirmiersEnService = emploiDuTempsService.getInfirmiersEnService(maintenant);
        
        if (infirmiersEnService.isEmpty()) {
            System.out.println("❌ Aucun infirmier en service actuellement.");
        } else {
            System.out.println("👷 INFIRMIERS EN SERVICE (" + infirmiersEnService.size() + ") :");
            for (Infirmier infirmier : infirmiersEnService) {
                EmploiDuTempsInfirmier edt = emploiDuTempsService.getEmploiDuTempsInfirmier(infirmier.getId());
                String poste = edt != null ? edt.getPosteActuel(maintenant) : "Inconnu";
                System.out.println("  " + infirmier.getNom() + " " + infirmier.getPrenom() + 
                                 " (ID: " + infirmier.getId() + ") - Poste: " + poste);
            }
        }
    }
    
    private void voirStatistiquesServices() {
        System.out.println("\n" + emploiDuTempsService.afficherStatistiquesService());
    }
    
    private void initialiserEmploisDuTemps() {
        System.out.println("\n--- INITIALISATION DES EMPLOIS DU TEMPS ---");
        
        List<ProfessionnelSante> professionnels = professionnelService.getTousLesProfessionnels();
        List<Medecin> medecins = new ArrayList<>();
        List<Infirmier> infirmiers = new ArrayList<>();
        
        for (ProfessionnelSante pro : professionnels) {
            if ("medecin".equals(pro.getRole())) {
                medecins.add((Medecin) pro);
            } else if ("infirmier".equals(pro.getRole())) {
                infirmiers.add((Infirmier) pro);
            }
        }
        
        emploiDuTempsService.initialiserEmploisDuTemps(medecins, infirmiers);
        
        System.out.println("✅ Emplois du temps initialisés pour :");
        System.out.println("  - " + medecins.size() + " médecin(s)");
        System.out.println("  - " + infirmiers.size() + " infirmier(s)");
    }
}
