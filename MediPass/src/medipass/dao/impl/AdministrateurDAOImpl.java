package medipass.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import medipass.dao.interfaces.AdministrateurDAOInterface;

import medipass.models.Administrateur;

public class AdministrateurDAOImpl implements AdministrateurDAOInterface {
    public void save(Administrateur admin) {}
	private final String filePath = "data/administrateur.json";
    private final ObjectMapper mapper = new ObjectMapper();
    private List<Administrateur> administrateur = new ArrayList<>();

    public AdministrateurDAOImpl() { 
    	load();
    	if (administrateur.isEmpty()) {
            add(new Administrateur(0, "Admin", "Super", 30));
        }
    }

    @Override
    public void load() {
    	try {
            File file = new File(filePath);
            if (file.exists() && file.length() > 0) {
                administrateur = mapper.readValue(file, new TypeReference<List<Administrateur>>() {});
            } else {
                administrateur = new ArrayList<>();
                // Création du dossier parent si inexistant
                if (file.getParentFile() != null) file.getParentFile().mkdirs();
            }
        } catch (IOException e) {
            e.printStackTrace();
            administrateur = new ArrayList<>();
        }
    }

    @Override
    public void save() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), administrateur);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public List<Administrateur> findAll() { return administrateur; }

    @Override
    public Administrateur findById(Integer id) {
        return administrateur.stream().filter(a -> a.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void add(Administrateur admin) {
        if (admin.getId() == 0 ) admin.setId(generateId());
        administrateur.add(admin);
        save();
    }

    @Override
    public void update
    (Administrateur admin) {
        for (int i = 0; i < administrateur.size(); i++) {
            if (administrateur.get(i).getId() == admin.getId()) {
            	administrateur.set(i, admin);
                save();
                return;
            }
        }
    }

    @Override
    public boolean delete(Integer id) {
        boolean removed = administrateur.removeIf(a -> a.getId() == id);
        if (removed) save();
        return removed;
    }

    @Override
    public int generateId() {
    	return administrateur.stream()
                .mapToInt(Administrateur::getId)
                .max()
                .orElse(0) + 1;
    }

    
    public Administrateur findByEmail(int id) {
        return administrateur.stream()
                .filter(u -> u.getId() == id)
                .findFirst().orElse(null);
    }
    
}