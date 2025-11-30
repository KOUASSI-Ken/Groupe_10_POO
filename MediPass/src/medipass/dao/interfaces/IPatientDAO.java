package medipass.dao.interfaces;

import java.util.List;
import java.util.Optional;

//import medipass.models.Consultation;
//port medipass.models.DossierMedical;
import medipass.models.Patient;  // a revoir en fonction du nom que le binôme 1 aura donné

public interface IPatientDAO {
	void createPatient(Patient patient);
	List<Patient> getAllPatients();
	Optional<Patient> getPatientById(int id);
	boolean updatePatient(Patient patient);
	boolean deletePatient(int id);
	int generateNewId();
	
	
	/*void createConsultation(Consultation consultations);
	boolean updateConsultation(Consultation consultations);
	boolean deleteConsultation(int id);
	List<Consultation> getAllConsultation();
	Optional<Consultation> getConsultationById(int id);
	//Optional<Consultation> getConsultationByIdPatient(int idPatient);
	int generateNewConsultationId();
	
	
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



	
