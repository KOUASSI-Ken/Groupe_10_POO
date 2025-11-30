package ui;

import models.Medecin;
import models.Patient;
import models.Consultation;
import services.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuMedecin {

    private final Medecin medecin;
    private final PatientService patientService;
    private final DossierService dossierService;
    private final ConsultationService consultationService;
    private final Scanner scanner = new Scanner(System.in);

    public MenuMedecin(Medecin medecin,
                       PatientService patientService,
                       DossierService dossierService,
                       ConsultationService consultationService) {
        this.medecin = medecin;
        this.patientService = patientService;
        this.dossierService = dossierService;
        this.consultationService = consultationService;
    }

    // ============================
    // MENU PRINCIPAL MEDECIN
    // ============================
    public void afficherMenu() {
        boolean actif = true;

        while (actif) {
            System.out.println("\n===== MENU MÉDECIN =====");
            System.out.println("1. Voir mes rendez-vous");
            System.out.println("2. Accéder au dossier médical d'un patient");
            System.out.println("0. Se déconnecter");
            System.out.print("Choix : ");

            String choix = scanner.nextLine();

            switch (choix) {
                case "1" -> afficherRendezVous();
                case "2" -> ouvrirDossierPatient();
                case "0" -> {
                    actif = false;
                    System.out.println("✅ Déconnexion réussie.");
                }
                default -> System.out.println("❌ Choix invalide !");
            }
        }
    }

    // ============================
    // AFFICHER RDV MEDECIN
    // ============================
    private void afficherRendezVous() {
        System.out.println("\n--- MES RENDEZ-VOUS ---");
        boolean trouve = false;

        for (Patient p : patientService.getTousLesPatients()) {
            for (Consultation c : p.getDossier().getConsultations()) {
                if (c.getMedecin().equals(medecin)) {
                    System.out.println("🧑 Patient : " + p.getNom()
                            + " | Date : " + c.getDate()
                            + " | Motif : " + c.getMotif());
                    trouve = true;
                }
            }
        }

        if (!trouve) System.out.println("Aucun rendez-vous enregistré.");
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
                    LocalDate.now(),
                    motif,
                    diagnostic,
                    medecin
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
