package medipass.dao.interfaces;

import medipass.models.ProfessionnelSante;
import java.util.List;

public interface ProfessionnelDAOInterface extends GenericDAO<ProfessionnelSante, Integer> {
    List<ProfessionnelSante> findMedecinsBySpecialite(String specialite);
    /*boolean hasDisponibilite(String isoDateTime);
    List<ProfessionnelSante> findBySpecialiteSorted(String specialite);*/
}
