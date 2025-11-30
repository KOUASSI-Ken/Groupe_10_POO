package ui;

import models.Patient;
import services.PatientService;

import java.util.Scanner;

public class MenuGestionPatient {

    private final PatientService patientService;
    private final Scanner scanner = new Scanner(System.in);

    public MenuGestionPatient(PatientService service) {
        this.patientService = service;
    }

    public void afficherMenu() {

        boolean actif = true;

        while (actif) {
            System.out.println("\n===== GESTION DES PATIENTS =====");
            System.out.println("1. Ajouter un patient");
            System.out.println("2. Rechercher un patient (ID)");
            System.out.println("3. Lister les patients");
            System.out.println("4. Supprimer un patient");
            System.out.println("0. Retour");

            System.out.print("Choix : ");
            String choix = scanner.nextLine();

            switch (choix) {
                case "1" -> ajouterPatient();
                case "2" -> rechercherPatient();
                case "3" -> afficherTous();
                case "4" -> supprimerPatient();
                case "0" -> actif = false;
                default -> System.out.println("Choix invalide !");
            }
        }
    }

    // =============================
    // AJOUT PATIENT
    // =============================
    private void ajouterPatient() {
        try {
            System.out.println("\n--- NOUVEAU PATIENT ---");

            System.out.print("ID : ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Nom : ");
            String nom = scanner.nextLine();

            System.out.print("Prénom : ");
            String prenom = scanner.nextLine();

            System.out.print("Date de naissance (jj/MM/aaaa) : ");
            String dateNaissance = scanner.nextLine();

            System.out.print("Sexe (M/F) : ");
            String sexe = scanner.nextLine();

            Patient patient = new Patient(id, nom, prenom, dateNaissance, sexe);

            patientService.ajouterPatient(patient);

            System.out.println("Patient ajouté avec succès.");

        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    // =============================
    // RECHERCHE
    // =============================
    private void rechercherPatient() {
        try {
            System.out.print("ID du patient : ");
            int id = Integer.parseInt(scanner.nextLine());

            Patient patient = patientService.rechercherPatient(id);

            if (patient == null) {
                System.out.println("Aucun patient trouvé.");
            } else {
                System.out.println("\n--- PATIENT ---");
                System.out.println(patient);
            }

        } catch (Exception e) {
            System.out.println("ID invalide.");
        }
    }

    // =============================
    // LISTE DES PATIENTS
    // =============================
    private void afficherTous() {

        System.out.println("\n--- LISTE DES PATIENTS ---");

        if (patientService.getTousLesPatients().isEmpty()) {
            System.out.println("Aucun patient enregistré.");
            return;
        }

        for (Patient p : patientService.getTousLesPatients()) {
            System.out.println(p);
            System.out.println("-----------------------------");
        }
    }

    // =============================
    // SUPPRESSION
    // =============================
    private void supprimerPatient() {

        try {
            System.out.print("ID à supprimer : ");
            int id = Integer.parseInt(scanner.nextLine());

            boolean supprime = patientService.supprimerPatient(id);

            System.out.println(
                supprime ? "Patient supprimé." : "Patient introuvable."
            );

        } catch (Exception e) {
            System.out.println("ID invalide.");
        }
    }
}
