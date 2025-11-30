package medipass.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import medipass.dao.interfaces.IConsultation;
import medipass.models.Consultation;

public class ConsultationDAO implements IConsultation {
	private static final String FILE_PATH = "data/consultation.json";
	private final ObjectMapper mapper = new ObjectMapper();
	private List<Consultation> Consultations = new ArrayList<>();
	
	public ConsultationDAO () {
		try {
			File file = Paths.get(FILE_PATH).toFile();
			if(file.exists()) {
				Consultations = mapper.readValue(file, new TypeReference<List<Consultation>>(){});
			}else {
				Consultations = new ArrayList<>();
				mapper.writerWithDefaultPrettyPrinter().writeValue(file, Consultations);
			}
		}catch(IOException e) {
			System.err.println("Erreur de chargement du fichier JSON : " + e.getMessage());
			Consultations = new ArrayList<>();
			}
		}

	
	private void sauvegarderVersJSON() {
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(Paths.get(FILE_PATH).toFile(), Consultations);
		}catch(IOException e) {
			System.err.println("Erreur de sauvegarde du fichier JSON: " + e.getMessage());
		}
	}
	
	@Override
	public int generateNewConsultationId() {
		return Consultations.stream().mapToInt(Consultation :: getIdConsultation).max().orElse(0) + 1;
	}
	
	@Override
	public void createConsultation(Consultation consultations) {
		if(consultations.getIdConsultation() == 0) {
			consultations.setIdConsultation(generateNewConsultationId());
		}
		boolean existe = Consultations.stream().anyMatch(c -> c.getIdConsultation() == consultations.getIdConsultation());
		if(existe) {
			System.err.println("Une consultation avec l'Id " + consultations.getIdConsultation() + "existe déjà");
			return;
		}
		this.Consultations.add(consultations);
		sauvegarderVersJSON();
	}
	
	@Override
	public List<Consultation> getAllConsultation() {
		return Collections.unmodifiableList(this.Consultations);
	}
	
	@Override
	public Optional<Consultation> getConsultationById(int id){
		return Consultations.stream()
				.filter(c -> c.getIdConsultation() == id)
				.findFirst();		
	}
	
	@Override
	public boolean updateConsultation(Consultation consultations) {
		Optional<Consultation> existingConsultation = getConsultationById(consultations.getIdConsultation());
		
		if(existingConsultation.isPresent()) {
			int index = Consultations.indexOf(existingConsultation.get());
			Consultations.set(index,  existingConsultation.get());
			sauvegarderVersJSON();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean deleteConsultation(int id) {
		boolean removed = this.Consultations.removeIf(c -> c.getIdConsultation() == id);
		if(removed) {
			sauvegarderVersJSON();
			return true;
		}
		return false;
	}
	
	/*@Override
	public Optional<Consultation> getConsultationByIdPatient(int idPatient){
		return Consultations.stream()
				.filter(p -> p.getIdPatient() == idPatient)
				.finFirst();
	}*/
		
	
}