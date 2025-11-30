package ui;

import services.StatistiqueService;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MenuStatistiques {

    private final StatistiqueService statService;
    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public MenuStatistiques(StatistiqueService statService) {
        this.statService = statService;
    }

    public void afficher() {
        System.out.println("\n=== STATISTIQUES GÉNÉRALES ===");
        System.out.println(statService.genererRapportComplet());

        LocalDate debut = null;
        LocalDate fin = null;

        System.out.println("\n--- STATISTIQUES D'UNE PÉRIODE ---");

        // Boucle pour obtenir des dates valides
        while (debut == null || fin == null) {
            try {
                System.out.print("Date début (yyyy-MM-dd) : ");
                debut = LocalDate.parse(scanner.nextLine().trim(), formatter);

                System.out.print("Date fin (yyyy-MM-dd) : ");
                fin = LocalDate.parse(scanner.nextLine().trim(), formatter);

                if (fin.isBefore(debut)) {
                    System.out.println("❌ La date de fin doit être après la date de début !");
                    debut = null;
                    fin = null;
                    continue;
                }

            } catch (DateTimeParseException e) {
                System.out.println("❌ Format de date invalide ! Veuillez réessayer.");
                debut = null;
                fin = null;
            }
        }

        // Affichage des statistiques pour la période
        System.out.println("\n--- STATISTIQUES DE LA PÉRIODE ---");
        System.out.println("Consultations : " + statService.getNombreConsultationsPeriode(debut, fin));
        System.out.println("Examens : " + statService.getNombreExamensPeriode(debut, fin));

        System.out.print("\nAppuyez sur Entrée pour revenir au menu...");
        scanner.nextLine(); // pause avant retour
    }
}
