package ui;

import models.Patient;
import models.Medecin;
import models.RendezVous;
import services.PatientService;
import services.RendezVousService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MenuProgrammationConsultations {
    
    private final PatientService patientService;
    private final RendezVousService rendezVousService;
    private final Medecin medecinConnecte;
    private final Scanner scanner;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    public MenuProgrammationConsultations(PatientService patientService, 
                                        RendezVousService rendezVousService,
                                        Medecin medecinConnecte) {
        this.patientService = patientService;
        this.rendezVousService = rendezVousService;
        this.medecinConnecte = medecinConnecte;
        this.scanner = new Scanner(System.in);
    }
    
    public void afficherMenu() {
        while (true) {
            System.out.println("\n===== PROGRAMMATION DES CONSULTATIONS =====");
            System.out.println("1. Voir mes rendez-vous");
            System.out.println("2. Programmer un nouveau rendez-vous");
            System.out.println("3. Vérifier mes disponibilités");
            System.out.println("4. Confirmer un rendez-vous");
            System.out.println("5. Annuler un rendez-vous");
            System.out.println("6. Terminer une consultation");
            System.out.println("0. Retour");
            System.out.print("Choix : ");
            
            try {
                int choix = Integer.parseInt(scanner.nextLine());
                
                switch (choix) {
                    case 1 -> voirMesRendezVous();
                    case 2 -> programmerRendezVous();
                    case 3 -> verifierDisponibilites();
                    case 4 -> confirmerRendezVous();
                    case 5 -> annulerRendezVous();
                    case 6 -> terminerConsultation();
                    case 0 -> { return; }
                    default -> System.out.println("❌ Choix invalide !");
                }
            } catch (Exception e) {
                System.out.println("❌ Erreur : " + e.getMessage());
            }
        }
    }
    
    private void voirMesRendezVous() {
        System.out.println("\n--- MES RENDEZ-VOUS ---");
        
        List<RendezVous> rdvs = rendezVousService.getRendezVousMedecin(medecinConnecte);
        
        if (rdvs.isEmpty()) {
            System.out.println("❌ Aucun rendez-vous programmé.");
        } else {
            System.out.println("📅 RENDEZ-VOUS (" + rdvs.size() + ") :");
            for (RendezVous rdv : rdvs) {
                System.out.println("\n" + rdv);
            }
        }
    }
    
    private void programmerRendezVous() {
        System.out.println("\n--- PROGRAMMATION D'UN RENDEZ-VOUS ---");
        
        try {
            // Sélection du patient
            System.out.print("ID du patient : ");
            int idPatient = Integer.parseInt(scanner.nextLine());
            Patient patient = patientService.rechercherPatient(idPatient);
            
            if (patient == null) {
                System.out.println("❌ Patient non trouvé.");
                return;
            }
            
            System.out.println("Patient sélectionné : " + patient.getNom() + " " + patient.getPrenom());
            
            // Saisie de la date/heure
            System.out.print("Date et heure (jj/mm/aaaa hh:mm) : ");
            String dateStr = scanner.nextLine();
            LocalDateTime dateHeure = LocalDateTime.parse(dateStr, FORMATTER);
            
            // Vérification de disponibilité
            if (!rendezVousService.verifierDisponibilite(medecinConnecte, dateHeure)) {
                System.out.println("❌ Ce créneau n'est pas disponible.");
                System.out.println("Voulez-vous voir les créneaux disponibles ? (O/N)");
                String reponse = scanner.nextLine();
                if ("O".equalsIgnoreCase(reponse)) {
                    verifierDisponibilitesPourDate(dateHeure.toLocalDate());
                }
                return;
            }
            
            // Motif du rendez-vous
            System.out.print("Motif : ");
            String motif = scanner.nextLine();
            
            // Création du rendez-vous
            RendezVous rdv = rendezVousService.creerRendezVous(patient, medecinConnecte, dateHeure, motif);
            
            System.out.println("✅ Rendez-vous programmé avec succès !");
            System.out.println(rdv);
            
        } catch (Exception e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
    }
    
    private void verifierDisponibilites() {
        System.out.println("\n--- VÉRIFICATION DES DISPONIBILITÉS ---");
        
        try {
            System.out.print("Date (jj/mm/aaaa) : ");
            String dateStr = scanner.nextLine();
            LocalDateTime date = LocalDateTime.parse(dateStr + " 00:00", 
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            
            verifierDisponibilitesPourDate(date.toLocalDate());
            
        } catch (Exception e) {
            System.out.println("❌ Date invalide : " + e.getMessage());
        }
    }
    
    private void verifierDisponibilitesPourDate(java.time.LocalDate date) {
        List<LocalDateTime> creneaux = rendezVousService.getCreneauxDisponibles(
            medecinConnecte, date.atStartOfDay());
        
        if (creneaux.isEmpty()) {
            System.out.println("❌ Aucun créneau disponible le " + 
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } else {
            System.out.println("🕐 CRÉNEAUX DISPONIBLES le " + 
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " (" + creneaux.size() + ") :");
            
            for (LocalDateTime creneau : creneaux) {
                System.out.println("  " + creneau.format(FORMATTER));
            }
        }
    }
    
    private void confirmerRendezVous() {
        System.out.println("\n--- CONFIRMATION D'UN RENDEZ-VOUS ---");
        
        System.out.print("ID du rendez-vous à confirmer : ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            
            if (rendezVousService.confirmerRendezVous(id)) {
                System.out.println("✅ Rendez-vous confirmé !");
                RendezVous rdv = rendezVousService.rechercherRendezVous(id);
                if (rdv != null) {
                    System.out.println(rdv);
                }
            } else {
                System.out.println("❌ Impossible de confirmer ce rendez-vous.");
            }
        } catch (Exception e) {
            System.out.println("❌ ID invalide.");
        }
    }
    
    private void annulerRendezVous() {
        System.out.println("\n--- ANNULATION D'UN RENDEZ-VOUS ---");
        
        System.out.print("ID du rendez-vous à annuler : ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            
            RendezVous rdv = rendezVousService.rechercherRendezVous(id);
            if (rdv == null) {
                System.out.println("❌ Rendez-vous non trouvé.");
                return;
            }
            
            System.out.println("Rendez-vous à annuler :");
            System.out.println(rdv);
            
            System.out.print("Confirmer l'annulation ? (O/N) : ");
            String confirmation = scanner.nextLine();
            
            if ("O".equalsIgnoreCase(confirmation)) {
                if (rendezVousService.annulerRendezVous(id)) {
                    System.out.println("✅ Rendez-vous annulé !");
                } else {
                    System.out.println("❌ Impossible d'annuler ce rendez-vous.");
                }
            } else {
                System.out.println("❌ Annulation annulée.");
            }
        } catch (Exception e) {
            System.out.println("❌ ID invalide.");
        }
    }
    
    private void terminerConsultation() {
        System.out.println("\n--- TERMINER UNE CONSULTATION ---");
        
        System.out.print("ID du rendez-vous à terminer : ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            
            RendezVous rdv = rendezVousService.rechercherRendezVous(id);
            if (rdv == null) {
                System.out.println("❌ Rendez-vous non trouvé.");
                return;
            }
            
            if (!"CONFIRMÉ".equals(rdv.getStatut())) {
                System.out.println("❌ Le rendez-vous doit être confirmé avant d'être terminé.");
                return;
            }
            
            rdv.terminer();
            System.out.println("✅ Consultation terminée !");
            System.out.println("Patient : " + rdv.getPatient().getNom() + " " + rdv.getPatient().getPrenom());
            
            // Ici on pourrait rediriger vers le menu de consultation pour ajouter des notes
            System.out.println("Vous pouvez maintenant consulter le dossier médical du patient.");
            
        } catch (Exception e) {
            System.out.println("❌ ID invalide.");
        }
    }
}
