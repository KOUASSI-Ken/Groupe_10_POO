package ui;

import models.Medecin;
import models.Patient;
import models.Consultation;
import services.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MenuMedecin {

    private final Medecin medecin;
    private final PatientService patientService;
    private final DossierService dossierService;
    private final ConsultationService consultationService;
    private final RendezVousService rendezVousService;
    private final Scanner scanner = new Scanner(System.in);

    public MenuMedecin(Medecin medecin,
                       PatientService patientService,
                       DossierService dossierService,
                       ConsultationService consultationService,
                       RendezVousService rendezVousService) {
        this.medecin = medecin;
        this.patientService = patientService;
        this.dossierService = dossierService;
        this.consultationService = consultationService;
        this.rendezVousService = rendezVousService;
    }

    // ============================
    // MENU PRINCIPAL MEDECIN
    // ============================
    public void afficherMenu() {
        boolean actif = true;

        while (actif) {
            System.out.println("\n===== MENU MÉDECIN =====");
            System.out.println("1. Gestion des rendez-vous");
            System.out.println("2. Gestion complète des patients");
            System.out.println("3. Accéder au dossier médical d'un patient");
            System.out.println("4. Ajouter une consultation");
            System.out.println("0. Se déconnecter");
            System.out.print("Choix : ");

            String choix = scanner.nextLine();

            switch (choix) {
                case "1" -> {
                    MenuProgrammationConsultations menuRdv = new MenuProgrammationConsultations(
                        patientService, rendezVousService, medecin);
                    menuRdv.afficherMenu();
                }
                case "2" -> {
                    MenuGestionCompletePatient menuPatients = new MenuGestionCompletePatient(patientService);
                    menuPatients.afficherMenu();
                }
                case "3" -> ouvrirDossierPatient();
                case "4" -> {
                    System.out.print("ID du patient : ");
                    try {
                        int idPatient = Integer.parseInt(scanner.nextLine());
                        Patient patient = patientService.rechercherPatient(idPatient);
                        if (patient != null) {
                            ajouterConsultation(patient);
                        } else {
                            System.out.println("❌ Patient non trouvé.");
                        }
                    } catch (Exception e) {
                        System.out.println("❌ ID invalide.");
                    }
                }
                case "0" -> {
                    actif = false;
                    System.out.println("✅ Déconnexion réussie.");
                }
                default -> System.out.println("❌ Choix invalide !");
            }
        }
    }

    // ============================
    // ACCES DOSSIER PATIENT
    // ============================
    private void ouvrirDossierPatient() {
        System.out.print("ID du patient : ");

        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("❌ ID invalide !");
            return;
        }

        Patient patient = patientService.rechercherPatient(id);
        if (patient == null) {
            System.out.println("❌ Patient non trouvé.");
            return;
        }

        boolean continuer = true;

        while (continuer) {
            System.out.println(dossierService.afficherDossierComplet(patient));

            System.out.println("\nOPTIONS DOSSIER");
            System.out.println("1. Ajouter une consultation");
            System.out.println("2. Modifier infos patient");
            System.out.println("3. Supprimer dossier médical");
            System.out.println("0. Retour");
            System.out.print("Choix : ");

            String choix = scanner.nextLine();

            switch (choix) {
                case "1" -> ajouterConsultation(patient);
                case "2" -> modifierPatient(patient);
                case "3" -> {
                    supprimerDossier(patient);
                    continuer = false;
                }
                case "0" -> continuer = false;
                default -> System.out.println("❌ Choix invalide");
            }
        }
    }

    // ============================
    // AJOUT CONSULTATION
    // ============================
    private void ajouterConsultation(Patient patient) {
        try {
            System.out.print("Motif : ");
            String motif = scanner.nextLine();

            System.out.print("Diagnostic : ");
            String diagnostic = scanner.nextLine();

            Consultation consultation = new Consultation(
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    motif,
                    diagnostic,
                    medecin.getNom() + " " + medecin.getPrenom()
            );

            consultationService.ajouterConsultation(patient, consultation);
            System.out.println("✅ Consultation ajoutée !");
        } catch (Exception e) {
            System.out.println("❌ Erreur lors de l'ajout.");
        }
    }

    // ============================
    // MODIFICATION INFOS PATIENT
    // ============================
    private void modifierPatient(Patient patient) {

        System.out.print("Nouveau nom (laisser vide si inchangé) : ");
        String nom = scanner.nextLine();
        if (!nom.isEmpty()) patient.setNom(nom);

        System.out.print("Nouveau prenom : ");
        String prenom = scanner.nextLine();
        if (!prenom.isEmpty()) patient.setPrenom(prenom);

        System.out.print("Nouvel âge : ");
        String age = scanner.nextLine();
        if (!age.isEmpty()) {
            try {
                patient.setAge(Integer.parseInt(age));
            } catch (Exception e) {
                System.out.println("❌ Age invalide");
            }
        }

        System.out.println("✅ Informations mises à jour.");
    }

    // ============================
    // SUPPRESSION DOSSIER
    // ============================
    private void supprimerDossier(Patient patient) {

        System.out.print("Confirmer suppression (O/N) : ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("o")) {
            boolean ok = dossierService.supprimerDossierComplet(patient);
            System.out.println(ok ? "✅ Dossier supprimé." : "❌ Erreur suppression.");
        } else {
            System.out.println("❌ Suppression annulée.");
        }
    }
}
