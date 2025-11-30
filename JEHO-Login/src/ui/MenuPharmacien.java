package ui;

import models.Patient;
import models.Pharmacien;
import services.PatientService;
import services.DossierService;
import services.PrescriptionService;
import services.ConsultationService;
import services.ExamenService;
import services.AntecedentService;

import java.time.LocalDate;
import java.util.Scanner;

public class MenuPharmacien {

    private  Pharmacien pharmacien;
    private  Scanner scanner = new Scanner(System.in);

    private PatientService patientService;
    private PrescriptionService prescriptionService;
    private DossierService dossierService;

    public MenuPharmacien(Pharmacien pharmacien, PatientService patientService,
            DossierService dossierService, PrescriptionService prescriptionService) {
        this.pharmacien = pharmacien;
        this.patientService = new PatientService();
        this.prescriptionService = new PrescriptionService();
        this.dossierService = new DossierService(
                new ConsultationService(),
                prescriptionService,
                new ExamenService(),
                new AntecedentService()
        );
    }

    public void afficherMenu() {
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n===== MENU PHARMACIEN =====");
            System.out.println("1. Rechercher patient par ID");
            System.out.println("2. Rechercher patient par NOM");
            System.out.println("0. Déconnexion");
            System.out.print("Choix : ");

            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    Patient patientId = rechercherPatientParID();
                    if (patientId != null) sousMenuPatient(patientId);
                    break;
                case "2":
                    Patient patientNom = rechercherPatientParNom();
                    if (patientNom != null) sousMenuPatient(patientNom);
                    break;
                case "0":
                    continuer = false;
                    System.out.println("Déconnexion...");
                    break;
                default:
                    System.out.println("❌ Choix invalide !");
            }
        }
    }

    private Patient rechercherPatientParID() {
        System.out.print("ID du patient : ");
        int id = Integer.parseInt(scanner.nextLine());
        Patient patient = patientService.rechercherPatient(id);
        if (patient == null) {
            System.out.println("❌ Patient introuvable !");
        } else {
            System.out.println("✅ Patient trouvé : " + patient.getNom() + " " + patient.getPrenom());
        }
        return patient;
    }

    private Patient rechercherPatientParNom() {
        System.out.print("Nom du patient : ");
        String nom = scanner.nextLine();
        for (Patient p : patientService.getTousLesPatients()) {
            if (p.getNom().equalsIgnoreCase(nom)) {
                System.out.println("Patient trouvé : " + p.getNom() + " " + p.getPrenom());
                return p;
            }
        }
        System.out.println("Aucun patient trouvé avec ce nom.");
        return null;
    }

    private void sousMenuPatient(Patient patient) {
        boolean retour = false;

        while (!retour) {
            System.out.println("\n--- Menu Patient: " + patient.getNom() + " ---");
            System.out.println("1. Voir dossier médical");
            System.out.println("2. Ajouter prescription");
            System.out.println("3. Modifier prescription");
            System.out.println("4. Supprimer prescription");
            System.out.println("0. Retour");
            System.out.print("Choix : ");

            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    System.out.println(dossierService.afficherDossierComplet(patient));
                    break;
                case "2":
                    ajouterPrescription(patient);
                    break;
                case "3":
                    modifierPrescription(patient);
                    break;
                case "4":
                    supprimerPrescription(patient);
                    break;
                case "0":
                    retour = true;
                    break;
                default:
                    System.out.println("❌ Choix invalide !");
            }
        }
    }

    private void ajouterPrescription(Patient patient) {
        System.out.print("Nom du produit : ");
        String nomProduit = scanner.nextLine();
        System.out.print("Posologie : ");
        String posologie = scanner.nextLine();
        LocalDate date = LocalDate.now();

        try {
            prescriptionService.ajouterPrescription(null, patient, 
                new models.Prescription(nomProduit, posologie, date)
            );
            System.out.println("Prescription ajoutée !");
        } catch (Exception e) {
            System.out.println("Impossible d'ajouter la prescription: " + e.getMessage());
        }
    }

    private void modifierPrescription(Patient patient) {
        System.out.print("Nom du produit à modifier : ");
        String nomProduit = scanner.nextLine();
        System.out.print("Nouveau nom produit (laisser vide pour ne pas changer) : ");
        String nouveauNom = scanner.nextLine();
        System.out.print("Nouvelle posologie (laisser vide pour ne pas changer) : ");
        String nouvellePosologie = scanner.nextLine();

        boolean ok = prescriptionService.modifierPrescription(
                pharmacien, patient, nomProduit,
                nouveauNom.isEmpty() ? null : nouveauNom,
                nouvellePosologie.isEmpty() ? null : nouvellePosologie,
                null
        );

        if (ok) System.out.println("Prescription modifiée !");
        else System.out.println("Modification impossible !");
    }

    private void supprimerPrescription(Patient patient) {
        System.out.print("Nom du produit à supprimer : ");
        String nomProduit = scanner.nextLine();
        models.Prescription p = prescriptionService.rechercherPrescription(patient, nomProduit);

        if (p != null && prescriptionService.supprimerPrescription(patient, p)) {
            System.out.println("Prescription supprimée !");
        } else {
            System.out.println("Impossible de supprimer la prescription !");
        }
    }
}
