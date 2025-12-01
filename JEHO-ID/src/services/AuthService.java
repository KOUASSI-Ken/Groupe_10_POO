package services;

import java.util.ArrayList;
import java.util.List;
import models.*;

public class AuthService {

    private List<ProfessionnelSante> utilisateurs = new ArrayList<>();
    private List<Administrateur> admins = new ArrayList<>();


    public AuthService() {

        // ADMINISTRATEUR
    	admins.add(new Administrateur("Admin", "System", 40, 1));

        // MEDECINS (4 médecins de spécialités différentes)
        utilisateurs.add(new Medecin("Martin", "Paul", 45, 2, "Cardiologie"));
        utilisateurs.add(new Medecin("Dubois", "Marie", 38, 3, "Pédiatrie"));
        utilisateurs.add(new Medecin("Bernard", "Jean", 52, 4, "Dermatologie"));
        utilisateurs.add(new Medecin("Petit", "Sophie", 41, 5, "Gynécologie"));

        // INFIRMIERS (10 infirmiers)
        utilisateurs.add(new Infirmier("Durand", "Claire", 30, 6, "infirmier"));
        utilisateurs.add(new Infirmier("Lefebvre", "Thomas", 28, 7, "infirmier"));
        utilisateurs.add(new Infirmier("Moreau", "Camille", 35, 8, "infirmier"));
        utilisateurs.add(new Infirmier("Garcia", "Nicolas", 32, 9, "infirmier"));
        utilisateurs.add(new Infirmier("Robert", "Isabelle", 29, 10, "infirmier"));
        utilisateurs.add(new Infirmier("Richard", "David", 40, 11, "infirmier"));
        utilisateurs.add(new Infirmier("Simon", "Laura", 26, 12, "infirmier"));
        utilisateurs.add(new Infirmier("Laurent", "Kevin", 33, 13, "infirmier"));
        utilisateurs.add(new Infirmier("Bertrand", "Emma", 31, 14, "infirmier"));
        utilisateurs.add(new Infirmier("Muller", "Lucas", 27, 15, "infirmier"));

        // PHARMACIENS (5 pharmaciens)
        utilisateurs.add(new Pharmacien("Pierre", "Louis", 35, 16, "pharmacien"));
        utilisateurs.add(new Pharmacien("Rousseau", "Julie", 42, 17, "pharmacien"));
        utilisateurs.add(new Pharmacien("Fournier", "Marc", 38, 18, "pharmacien"));
        utilisateurs.add(new Pharmacien("Gauthier", "Sarah", 34, 19, "pharmacien"));
        utilisateurs.add(new Pharmacien("Henry", "Michel", 45, 20, "pharmacien"));
    }

    public ProfessionnelSante connecterParId(int id) {
        for (ProfessionnelSante p : utilisateurs) {
            if (p.getId() == id) return p;
        }
        for (Administrateur a : admins) {
            if (a.getId() == id) return a;
        }
        return null;
    }
}
