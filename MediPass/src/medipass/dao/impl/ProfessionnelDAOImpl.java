package medipass.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import medipass.models.ProfessionnelSante;
import medipass.dao.interfaces.ProfessionnelDAOInterface;
import medipass.models.Medecin;
//import medipass.models.ProfessionnelSante;

import java.io.File;
//import java.io.IOException;
import java.util.ArrayList;
//import java.util.Comparator;
import java.util.List;
//import java.util.Objects;
import java.util.stream.Collectors;

public class ProfessionnelDAOImpl implements ProfessionnelDAOInterface {
    private final String filePath = "data/professionnels.json";
    private final ObjectMapper mapper = new ObjectMapper();
    private List<ProfessionnelSante> pros = new ArrayList<>();

    public ProfessionnelDAOImpl() { 
    	mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    	mapper.registerSubtypes(medipass.models.Medecin.class);
        mapper.registerSubtypes(medipass.models.Infirmier.class);
        mapper.registerSubtypes(medipass.models.Pharmacien.class);
    	load(); }

    @Override
    public void load() {
    	try {
            File f = new File(filePath);
            if (f.exists() && f.length() > 0) {
                pros = mapper.readValue(f, new TypeReference<List<ProfessionnelSante>>() {});
            } else {
                pros = new ArrayList<>();
                if (f.getParentFile() != null) f.getParentFile().mkdirs();
            }
        } catch (Exception e) {
            System.err.println("Erreur de chargement du professionnel: " + e.getMessage());
            pros = new ArrayList<>();
        }
    }

    @Override
    public void save() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), pros);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public List<ProfessionnelSante> findAll() { return pros; }

    @Override
    public ProfessionnelSante findById(Integer id) {
        return pros.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void add(ProfessionnelSante p) {
    	if (p.getId() == 0) p.setId(generateId());
        pros.add(p);
        save();
    }

    @Override
    public void update(ProfessionnelSante p) {
        for (int i = 0; i < pros.size(); i++) {
        	if (pros.get(i).getId() == p.getId()) {
                pros.set(i, p);
                save();
                return;
            }
        }
    }

    @Override
    public boolean delete(Integer id) {
        boolean removed = pros.removeIf(p -> p.getId() == id);
        if (removed) save();
        return removed;
    }

    
    public int generateId() {
    	return pros.stream()
                .mapToInt(ProfessionnelSante::getId)
                .max()
                .orElse(0) + 1;
    }

    
    public List<ProfessionnelSante> findMedecinsBySpecialite(String specialite) {
        return pros.stream()
                .filter(p -> p instanceof Medecin) 
                .map(p -> (Medecin) p)             
                .filter(m -> m.getSpecialite() != null && m.getSpecialite().equalsIgnoreCase(specialite))
                .collect(Collectors.toList());
    }

 /*   
    public boolean hasDisponibilite(String isoDateTime) {
        return pros.stream()
                .anyMatch(p -> p.getDisponibilites() != null && p.getDisponibilites().contains(isoDateTime));
    }

   
    public List<ProfessionnelSante> findBySpecialiteSorted(String specialite) {
        return findBySpecialite(specialite).stream()
                .sorted((a, b) -> a.getNom().compareToIgnoreCase(b.getNom()))
                .collect(Collectors.toList());
    }*/
}