package medipass.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import medipass.dao.interfaces.IPatientDAO;
import medipass.models.Patient;

public class PatientDAO implements IPatientDAO {
	private static final String FILE_PATH = "data/patient.json";
	private final ObjectMapper objectMapper;
	private List<Patient> patients = new ArrayList<>();
	
	
	
	
	public PatientDAO() {
		
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());  
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		
		
		try {
			File file = Paths.get(FILE_PATH).toFile();
			if (file.exists() && file.length() > 0) {
				patients = objectMapper.readValue(file, new TypeReference<List<Patient>>() {});
			}else {
				patients = new ArrayList<>();
				objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, patients);
			}
		} catch (IOException e) {
			System.err.println("Erreur de chargement du fichier JSON: " + e.getMessage());
			//e.printStackTrace();
			patients = new ArrayList<>();
		}
			
		}
	
	public void sauvegarderVersJSON() {
	    try {
	        objectMapper.writerWithDefaultPrettyPrinter().writeValue(Paths.get(FILE_PATH).toFile(), patients);
	    } catch (IOException e) {
	        System.err.println("Erreur de sauvegarde du fichier JSON: " + e.getMessage());
	    }
	}
	@Override	
	public List<Patient>getAllPatients() {
		return Collections.unmodifiableList(this.patients);
	}
	
	@Override
	public Optional<Patient> getPatientById(int id) {
		return patients.stream()
				.filter(p -> p.getId() == id)
				.findFirst();
	}
	
	@Override 
	public int generateNewId() {
		return patients.stream().mapToInt(Patient::getId).max().orElse(0) + 1;
	}
	
	@Override
	public void createPatient(Patient patient) {
		if (patient.getId() == 0) {
			patient.setId(generateNewId());
		}
		
		boolean existe = patients.stream().anyMatch(p -> p.getId() == patient.getId());
		if (existe) {
			System.err.println("Erreur : Un patient avec l'Id" + patient.getId() + "existe déjà !");
			return;
		}
		this.patients.add(patient);
		sauvegarderVersJSON();
	}
	
	@Override
	public boolean updatePatient(Patient patient) {
		Optional<Patient> existingPatient = getPatientById(patient.getId());
		
		if (existingPatient.isPresent()) {
			int index = patients.indexOf(existingPatient.get());
			patients.set(index, patient);
			sauvegarderVersJSON();
			return true;
		}
		return false;
	}
	
	@Override 
	public boolean deletePatient (int id) {
		boolean removed = this.patients.removeIf(patient -> patient.getId() == id);
		if (removed) {
			sauvegarderVersJSON();
			return true;
		}
		return false;
	}
	
	
	}
	
	
