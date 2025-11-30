package services;

import java.util.ArrayList;
import java.util.List;
import models.*;

public class AuthService {

    private List<ProfessionnelSante> utilisateurs = new ArrayList<>();
    private List<Administrateur> admins = new ArrayList<>();


    public AuthService() {

        // ADMINISTRATEUR
    	admins.add(new Administrateur("Admin", "System", 40, 1, "admin"));

        // MEDECIN
        utilisateurs.add(new Medecin("Martin", "Paul", 45, 2, "medecin"));

        // INFIRMIER
        utilisateurs.add(new Infirmier("Sophie", "Claire", 30, 3, "infirmier"));

        // PHARMACIEN
        utilisateurs.add(new Pharmacien("Pierre", "Louis", 35, 4, "pharmacien"));
    }

    public ProfessionnelSante connecterParId(int id) {
        for (ProfessionnelSante p : utilisateurs) {
            if (p.getId() == id) return p;
        }
        return null;
    }
}
