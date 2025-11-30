package medipass.dao.interfaces;

import java.util.List;
import java.util.Optional;
import medipass.models.DossierMedical;// a revoir en fonction du nom que le binôme 1 aura donné

public interface IDossierMedicalDAO {
	void createDossier (DossierMedical dossier);
	List<DossierMedical> getAllDossier();
	//Optional<DossierMedical> getDossierByIdPatient(int idPatient);
	Optional<DossierMedical> getDossierById(int id);
	boolean updateDossier(DossierMedical dossier);
	boolean deleteDossier (int id);
	int generateNewDossierId();
	/*boolean addConsultation(int idDossier, int idConsultation);
	boolean addAntecedent(int idDossier, int idAntecedent);
	boolean deleteConsultation(int idDossier, int idConsultation);
	boolean deleteAntecedent(int idDossier, int idAntecedent);
	List<Integer> getIdConsultations(int idDossier);
	List<Integer> getIdAntecedents(int idDossier);	*/
}