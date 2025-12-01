package services;

import models.ProfessionnelSante;
import models.Medecin;
import models.Infirmier;
import models.Pharmacien;
import java.util.ArrayList;
import java.util.List;

public class ProfessionnelService {
    private List<ProfessionnelSante> professionnels;

    public ProfessionnelService() {
        this.professionnels = new ArrayList<>();
    }

    // Créer un professionnel
    public void creerProfessionnel(ProfessionnelSante professionnel) {
        if (professionnel == null) {
            throw new IllegalArgumentException("Le professionnel ne peut pas être null");
        }
        if (rechercherProfessionnel(professionnel.getId()) != null) {
            throw new IllegalArgumentException("Un professionnel avec l'ID " + professionnel.getId() + " existe déjà");
        }
        professionnels.add(professionnel);
    }

    // Rechercher un professionnel par ID
    public ProfessionnelSante rechercherProfessionnel(int id) {
        for (ProfessionnelSante p : professionnels) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    // Supprimer un professionnel
    public boolean supprimerProfessionnel(ProfessionnelSante professionnel) {
        return professionnels.remove(professionnel);
    }

    public boolean supprimerProfessionnel(int id) {
        ProfessionnelSante pro = rechercherProfessionnel(id);
        if (pro != null) {
            return professionnels.remove(pro);
        }
        return false;
    }

    // Obtenir tous les professionnels
    public List<ProfessionnelSante> getTousLesProfessionnels() {
        return new ArrayList<>(professionnels);
    }

    // Obtenir le nombre de professionnels
    public List<ProfessionnelSante> getTousProfessionnels() {
        return new ArrayList<>(professionnels);
    }

    // Statistiques par type
    public int getNombreMedecins() {
        return (int) professionnels.stream()
                .filter(p -> p instanceof Medecin)
                .count();
    }

    public int getNombreInfirmiers() {
        return (int) professionnels.stream()
                .filter(p -> p instanceof Infirmier)
                .count();
    }

    public int getNombrePharmaciens() {
        return (int) professionnels.stream()
                .filter(p -> p instanceof Pharmacien)
                .count();
    }

    // Lister par type
    public List<Medecin> getTousMedecins() {
        List<Medecin> medecins = new ArrayList<>();
        for (ProfessionnelSante p : professionnels) {
            if (p instanceof Medecin) {
                medecins.add((Medecin) p);
            }
        }
        return medecins;
    }
    
    public List<Infirmier> getTousInfirmiers() {
        List<Infirmier> infirmiers = new ArrayList<>();
        for (ProfessionnelSante p : professionnels) {
            if (p instanceof Infirmier) {
                infirmiers.add((Infirmier) p);
            }
        }
        return infirmiers;
    }
    
    public List<Pharmacien> getTousPharmaciens() {
        List<Pharmacien> pharmaciens = new ArrayList<>();
        for (ProfessionnelSante p : professionnels) {
            if (p instanceof Pharmacien) {
                pharmaciens.add((Pharmacien) p);
            }
        }
        return pharmaciens;
    }
}