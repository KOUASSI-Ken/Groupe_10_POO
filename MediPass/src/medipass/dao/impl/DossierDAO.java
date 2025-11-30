package medipass.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import medipass.models.DossierMedical; 
import medipass.dao.interfaces.IDossierMedicalDAO;

public class DossierDAO implements IDossierMedicalDAO {
	private static final String FILE_PATH = "data/dossier.json";
	private final ObjectMapper mapper = new ObjectMapper();
	private List<DossierMedical> dossiers = new ArrayList<>();
	
	
	public DossierDAO() {
		try {
			File file = Paths.get(FILE_PATH).toFile();
			if(file.exists()) {
				dossiers = mapper.readValue(file, new TypeReference<List<DossierMedical>>() {});
			}else {
				dossiers = new ArrayList<>();
				//file.getParentFile().mkdirs();
				mapper.writerWithDefaultPrettyPrinter().writeValue(file, dossiers);
			}
		}catch (IOException e) {
			System.err.println("Erreur de chargement du fichier JSON: " + e.getMessage());
			dossiers = new ArrayList<>();
		}
	}
	
	private void sauvegarderVersJSON() {
	    try {
	        mapper.writerWithDefaultPrettyPrinter().writeValue(Paths.get(FILE_PATH).toFile(), dossiers);
	    } catch (IOException e) {
	        System.err.println("Erreur de sauvegarde du fichier JSON: " + e.getMessage());
	    }
	}
	@Override
	public List<DossierMedical> getAllDossier() {
		return Collections.unmodifiableList(this.dossiers);
	}
	
	@Override 
	public Optional<DossierMedical> getDossierById(int id) {
		return dossiers.stream()
				.filter(m -> m.getIdDossier() == id)
				.findFirst();
	}
	
	/*@Override
	public Optional<DossierMedical> getDossierByIdPatient(int idPatient){
		return dossiers.stream()
				.filter(p -> p.getIdPatient() == idPatient)
				.findFirst();
	}
	*/
	@Override 
	public int generateNewDossierId() {
		return dossiers.stream().mapToInt(DossierMedical::getIdDossier).max().orElse(0) + 1;
	}
	
	@Override
	public void createDossier (DossierMedical dossier) {
		if(dossier.getIdDossier() == 0) {
			dossier.setId(generateNewDossierId());			
		}
		
		boolean existe = dossiers.stream().anyMatch(d -> d.getIdDossier() == dossier.getIdDossier());
		if (existe) {
			System.err.println("Erreur : Un dossier avec l'Id" + dossier.getIdDossier() + "existe déjà !");
			return;
		}
		this.dossiers.add(dossier);
		sauvegarderVersJSON (); 
	}
	
	@Override
	public boolean updateDossier(DossierMedical dossier) {
		Optional<DossierMedical> existingDossier = getDossierById(dossier.getIdDossier());
		if (existingDossier.isPresent()) {
			int index = dossiers.indexOf(existingDossier.get());
			dossiers.set(index, dossier);
			sauvegarderVersJSON ();
			return true;
		}
		return false;
	}
	@Override
	public boolean deleteDossier (int id) {
		boolean removed = this.dossiers.removeIf(dossier -> dossier.getIdDossier() == id);
		if(removed) {
			sauvegarderVersJSON();
			return true;
		}
		return false;
	} 
	
	/*@Override
	public boolean addConsultation(int idDossier, int idConsultation) {
		Optional<DossierMedical> dossierOpt = getDossierById(idDossier);
		if (dossierOpt.isPresent()) {
			DossierMedical dossier = dossierOpt.get();
			List<Integer> listeIds = dossier.getConsultationIds();
			if (!listeIds.contains(idConsultation)) {
				listeIds.add(idConsultation);
			}
			sauvegarderVersJSON();
			return true; 
		}
		return false;
	}
	
	@Override 
	public 	boolean addAntecedent(int idDossier, int idAntecedent) {
		Optional<DossierMedical> dossierOpt = getDossierById(idDossier);
		if (dossierOpt.isPresent()) {
			DossierMedical dossier = dossierOpt.get();
			List<Integer> listeIds = dossier.getAntecedentIds();
			if (!listeIds.contains(idAntecedent)) {
				listeIds.add(idAntecedent);
			}
			sauvegarderVersJSON();
			return true;
		}
		return false;
	}

	@Override 
	public 	boolean deleteConsultation(int idDossier, int idConsultation) {
		Optional<DossierMedical> dossierOpt = getDossierById(idDossier);
		if (dossierOpt.isPresent()) {
			DossierMedical dossier = dossierOpt.get();
			List<Integer> listeIds = dossier.getConsultationIds();
			boolean removed = listeIds.remove(Integer.valueOf(idConsultation));
			if (removed) {
				sauvegarderVersJSON();
				return true;
			}
		}
		return false;
	}
	
	@Override 
	public 	boolean deleteAntecedent(int idDossier, int idAntecedent) {
		Optional<DossierMedical> dossierOpt = getDossierById(idDossier);
		if (dossierOpt.isPresent()) {
			DossierMedical dossier = dossierOpt.get();
			List<Integer> listeIds = dossier.getAntecedentIds();
			boolean removed = listeIds.remove(Integer.valueOf(idAntecedent));
			if (removed) {
				sauvegarderVersJSON();
				return true;				
			}
		}
		return false;
		
		
	}
	
	public 	List<Integer> getIdConsultations(int idDossier){
		Optional<DossierMedical> dossierOpt = getDossierById(idDossier);
		if(dossierOpt.isPresent()) {
			return dossierOpt.get().getConsultationIds();
		}
		return new ArrayList<>();
	}

	public List<Integer> getIdAntecedents(int idDossier){
		Optional<DossierMedical> dossierOpt = getDossierById(idDossier);
		if(dossierOpt.isPresent()) {
			return dossierOpt.get().getAntecedentIds();
		}
		return new ArrayList<>();
	}*/

	
	
}






