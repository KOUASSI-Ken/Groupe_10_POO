package services;

import models.ProfessionnelSante;

public class AdministrateurService {
    private ProfessionnelService professionnelService;

    public AdministrateurService() {
    }

    // Créer un utilisateur
    public void creerUtilisateur(ProfessionnelSante professionnel) {
        professionnelService.creerProfessionnel(professionnel);
    }

    // Supprimer un utilisateur
    public boolean supprimerUtilisateur(ProfessionnelSante professionnel) {
        return professionnelService.supprimerProfessionnel(professionnel);
    }

    public boolean supprimerUtilisateur(int id) {
        return professionnelService.supprimerProfessionnel(id);
    }

    // Statistiques
    public String getStatistiques() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== STATISTIQUES DU SYSTÈME ===\n");
        sb.append("Médecins: ").append(professionnelService.getNombreMedecins()).append("\n");
        sb.append("Infirmiers: ").append(professionnelService.getNombreInfirmiers()).append("\n");
        sb.append("Pharmaciens: ").append(professionnelService.getNombrePharmaciens()).append("\n");
        sb.append("Total: ").append(professionnelService.getTousProfessionnels().size()).append("\n");
        return sb.toString();
    }
}