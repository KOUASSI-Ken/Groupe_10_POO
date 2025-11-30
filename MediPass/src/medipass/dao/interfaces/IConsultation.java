package medipass.dao.interfaces;

import java.util.List;
import java.util.Optional;
import medipass.models.Consultation;

public interface IConsultation {
	void createConsultation(Consultation consultations);
	boolean updateConsultation(Consultation consultations);
	boolean deleteConsultation(int id);
	List<Consultation> getAllConsultation();
	Optional<Consultation> getConsultationById(int id);
	//Optional<Consultation> getConsultationByIdPatient(int idPatient);
	int generateNewConsultationId();
		
}
