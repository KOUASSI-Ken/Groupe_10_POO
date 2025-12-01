package models;

public enum Permission {
    // Permissions de base
    LIRE_PATIENT("Lire les informations des patients"),
    MODIFIER_PATIENT("Modifier les informations des patients"),
    CREER_PATIENT("Créer de nouveaux patients"),
    ARCHIVER_PATIENT("Archiver des patients"),
    
    // Permissions consultations
    LIRE_CONSULTATION("Lire les consultations"),
    CREER_CONSULTATION("Créer des consultations"),
    MODIFIER_CONSULTATION("Modifier des consultations"),
    SUPPRIMER_CONSULTATION("Supprimer des consultations"),
    
    // Permissions prescriptions
    LIRE_PRESCRIPTION("Lire les prescriptions"),
    CREER_PRESCRIPTION("Créer des prescriptions"),
    MODIFIER_PRESCRIPTION("Modifier des prescriptions"),
    SUPPRIMER_PRESCRIPTION("Supprimer les prescriptions"),
    
    // Permissions examens
    LIRE_EXAMEN("Lire les examens médicaux"),
    CREER_EXAMEN("Créer des examens médicaux"),
    MODIFIER_EXAMEN("Modifier les examens médicaux"),
    SUPPRIMER_EXAMEN("Supprimer les examens médicaux"),
    
    // Permissions antécédents
    LIRE_ANTECEDENT("Lire les antécédents médicaux"),
    CREER_ANTECEDENT("Créer des antécédents médicaux"),
    MODIFIER_ANTECEDENT("Modifier les antécédents médicaux"),
    SUPPRIMER_ANTECEDENT("Supprimer les antécédents médicaux"),
    
    // Permissions administratives
    GERER_UTILISATEURS("Gérer les comptes utilisateurs"),
    GERER_DROITS("Gérer les droits d'accès"),
    VOIR_STATISTIQUES("Voir les statistiques du système"),
    EXPORTER_DONNEES("Exporter les données du système"),
    IMPORTER_DONNEES("Importer des données dans le système"),
    
    // Permissions planning
    VOIR_PLANNING("Voir les plannings"),
    GERER_PLANNING("Gérer les plannings"),
    GERER_RENDEZ_VOUS("Gérer les rendez-vous");
    
    private final String description;
    
    Permission(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return description;
    }
}
