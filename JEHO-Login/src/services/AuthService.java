package services;

import java.util.ArrayList;
import java.util.List;
import models.*;

public class AuthService {
    private List<ProfessionnelSante> utilisateurs = new ArrayList<>();

    public AuthService() {
        utilisateurs.add(new Administrateur("Admin", "System", 40, 1, "admin", "pass123"));
        utilisateurs.add(new Medecin("Martin", "Paul", 45, 2, "medecin", "pass123", "Cardiologie"));
        utilisateurs.add(new Infirmier("Sophie", "Claire", 30, 3, "infirmier", "pass123"));
        utilisateurs.add(new Pharmacien("Pierre", "Louis", 35, 4, "pharmacien", "pass123"));
    }

    public ProfessionnelSante connecterParId(int id) {
        for (ProfessionnelSante p : utilisateurs) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public ProfessionnelSante connecterParLogin(String login, String motDePasse) {
        for (ProfessionnelSante p : utilisateurs) {
            if (p.getLogin().equals(login) && p.getMotDePasse().equals(motDePasse)) {
                return p;
            }
        }
        return null;
    }
}
