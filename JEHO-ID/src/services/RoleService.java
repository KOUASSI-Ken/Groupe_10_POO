package services;

import models.Permission;
import models.Role;
import java.util.*;

public class RoleService {
    private Map<String, Role> roles;
    private Map<Integer, Set<Role>> rolesParUtilisateur;
    
    public RoleService() {
        this.roles = new HashMap<>();
        this.rolesParUtilisateur = new HashMap<>();
        initialiserRolesParDefaut();
    }
    
    private void initialiserRolesParDefaut() {
        // Rôle Administrateur complet
        Set<Permission> permissionsAdmin = EnumSet.allOf(Permission.class);
        Role admin = new Role("ADMINISTRATEUR", "Accès complet à toutes les fonctionnalités", permissionsAdmin);
        roles.put("ADMINISTRATEUR", admin);
        
        // Rôle Médecin
        Set<Permission> permissionsMedecin = EnumSet.of(
            Permission.LIRE_PATIENT, Permission.MODIFIER_PATIENT, Permission.CREER_PATIENT,
            Permission.LIRE_CONSULTATION, Permission.CREER_CONSULTATION, Permission.MODIFIER_CONSULTATION,
            Permission.LIRE_PRESCRIPTION, Permission.CREER_PRESCRIPTION, Permission.MODIFIER_PRESCRIPTION,
            Permission.LIRE_EXAMEN, Permission.CREER_EXAMEN, Permission.MODIFIER_EXAMEN,
            Permission.LIRE_ANTECEDENT, Permission.CREER_ANTECEDENT, Permission.MODIFIER_ANTECEDENT,
            Permission.VOIR_PLANNING, Permission.GERER_PLANNING, Permission.GERER_RENDEZ_VOUS,
            Permission.VOIR_STATISTIQUES, Permission.EXPORTER_DONNEES
        );
        Role medecin = new Role("MEDECIN", "Professionnel médical avec accès aux dossiers patients", permissionsMedecin);
        roles.put("MEDECIN", medecin);
        
        // Rôle Infirmier
        Set<Permission> permissionsInfirmier = EnumSet.of(
            Permission.LIRE_PATIENT, Permission.MODIFIER_PATIENT,
            Permission.LIRE_CONSULTATION, Permission.CREER_CONSULTATION,
            Permission.LIRE_PRESCRIPTION,
            Permission.LIRE_EXAMEN, Permission.CREER_EXAMEN,
            Permission.LIRE_ANTECEDENT, Permission.CREER_ANTECEDENT,
            Permission.VOIR_PLANNING, Permission.GERER_RENDEZ_VOUS,
            Permission.VOIR_STATISTIQUES
        );
        Role infirmier = new Role("INFIRMIER", "Personnel soignant avec accès limité aux dossiers", permissionsInfirmier);
        roles.put("INFIRMIER", infirmier);
        
        // Rôle Pharmacien
        Set<Permission> permissionsPharmacien = EnumSet.of(
            Permission.LIRE_PATIENT,
            Permission.LIRE_CONSULTATION,
            Permission.LIRE_PRESCRIPTION, Permission.CREER_PRESCRIPTION, Permission.MODIFIER_PRESCRIPTION,
            Permission.LIRE_EXAMEN,
            Permission.LIRE_ANTECEDENT,
            Permission.VOIR_STATISTIQUES, Permission.EXPORTER_DONNEES
        );
        Role pharmacien = new Role("PHARMACIEN", "Pharmacien avec accès aux prescriptions", permissionsPharmacien);
        roles.put("PHARMACIEN", pharmacien);
        
        // Rôle Secrétaire médicale
        Set<Permission> permissionsSecretaire = EnumSet.of(
            Permission.LIRE_PATIENT, Permission.CREER_PATIENT, Permission.MODIFIER_PATIENT,
            Permission.LIRE_CONSULTATION, Permission.CREER_CONSULTATION,
            Permission.VOIR_PLANNING, Permission.GERER_PLANNING, Permission.GERER_RENDEZ_VOUS,
            Permission.VOIR_STATISTIQUES, Permission.EXPORTER_DONNEES
        );
        Role secretaire = new Role("SECRETAIRE", "Secrétaire médicale avec accès administratif", permissionsSecretaire);
        roles.put("SECRETAIRE", secretaire);
        
        // Rôle Observateur (lecture seule)
        Set<Permission> permissionsObservateur = EnumSet.of(
            Permission.LIRE_PATIENT, Permission.LIRE_CONSULTATION, 
            Permission.LIRE_PRESCRIPTION, Permission.LIRE_EXAMEN, Permission.LIRE_ANTECEDENT,
            Permission.VOIR_PLANNING, Permission.VOIR_STATISTIQUES
        );
        Role observateur = new Role("OBSERVATEUR", "Accès en lecture seule aux données médicales", permissionsObservateur);
        roles.put("OBSERVATEUR", observateur);
    }
    
    // Gestion des rôles
    public Role creerRole(String nom, String description) {
        if (roles.containsKey(nom)) {
            throw new IllegalArgumentException("Le rôle '" + nom + "' existe déjà");
        }
        
        Role role = new Role(nom, description);
        roles.put(nom, role);
        return role;
    }
    
    public boolean supprimerRole(String nom) {
        // Ne pas supprimer les rôles par défaut
        if (nom.equals("ADMINISTRATEUR") || nom.equals("MEDECIN") || 
            nom.equals("INFIRMIER") || nom.equals("PHARMACIEN")) {
            return false;
        }
        
        return roles.remove(nom) != null;
    }
    
    public Role getRole(String nom) {
        return roles.get(nom);
    }
    
    public Collection<Role> getTousLesRoles() {
        return new ArrayList<>(roles.values());
    }
    
    // Gestion des permissions
    public boolean ajouterPermissionRole(String nomRole, Permission permission) {
        Role role = roles.get(nomRole);
        if (role != null) {
            role.ajouterPermission(permission);
            return true;
        }
        return false;
    }
    
    public boolean retirerPermissionRole(String nomRole, Permission permission) {
        Role role = roles.get(nomRole);
        if (role != null) {
            role.retirerPermission(permission);
            return true;
        }
        return false;
    }
    
    // Gestion des rôles par utilisateur
    public boolean assignerRoleUtilisateur(int idUtilisateur, String nomRole) {
        Role role = roles.get(nomRole);
        if (role == null) {
            return false;
        }
        
        rolesParUtilisateur.computeIfAbsent(idUtilisateur, k -> new HashSet<>()).add(role);
        return true;
    }
    
    public boolean retirerRoleUtilisateur(int idUtilisateur, String nomRole) {
        Set<Role> rolesUser = rolesParUtilisateur.get(idUtilisateur);
        if (rolesUser != null) {
            return rolesUser.removeIf(role -> role.getNom().equals(nomRole));
        }
        return false;
    }
    
    public Set<Role> getRolesUtilisateur(int idUtilisateur) {
        return rolesParUtilisateur.getOrDefault(idUtilisateur, new HashSet<>());
    }
    
    // Vérification des permissions
    public boolean aPermission(int idUtilisateur, Permission permission) {
        Set<Role> rolesUser = rolesParUtilisateur.get(idUtilisateur);
        if (rolesUser == null) {
            return false;
        }
        
        for (Role role : rolesUser) {
            if (role.aPermission(permission)) {
                return true;
            }
        }
        
        return false;
    }
    
    // Assignation automatique des rôles selon le type de professionnel
    public void assignerRoleParDefaut(int idUtilisateur, String typeProfessionnel) {
        switch (typeProfessionnel.toLowerCase()) {
            case "admin":
            case "administrateur":
                assignerRoleUtilisateur(idUtilisateur, "ADMINISTRATEUR");
                break;
            case "medecin":
                assignerRoleUtilisateur(idUtilisateur, "MEDECIN");
                break;
            case "infirmier":
                assignerRoleUtilisateur(idUtilisateur, "INFIRMIER");
                break;
            case "pharmacien":
                assignerRoleUtilisateur(idUtilisateur, "PHARMACIEN");
                break;
            default:
                assignerRoleUtilisateur(idUtilisateur, "OBSERVATEUR");
        }
    }
    
    // Affichage
    public String afficherRoles() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== LISTE DES RÔLES ===\n\n");
        
        for (Role role : roles.values()) {
            sb.append(role).append("\n");
        }
        
        return sb.toString();
    }
    
    public String afficherPermissionsUtilisateur(int idUtilisateur) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== PERMISSIONS UTILISATEUR #").append(idUtilisateur).append(" ===\n");
        
        Set<Role> rolesUser = getRolesUtilisateur(idUtilisateur);
        if (rolesUser.isEmpty()) {
            sb.append("Aucun rôle assigné\n");
        } else {
            Set<Permission> toutesPermissions = new HashSet<>();
            for (Role role : rolesUser) {
                toutesPermissions.addAll(role.getPermissions());
            }
            
            sb.append("Rôles: ");
            for (Role role : rolesUser) {
                sb.append(role.getNom()).append(" ");
            }
            sb.append("\n\nPermissions:\n");
            
            for (Permission perm : toutesPermissions) {
                sb.append("  ✓ ").append(perm.getDescription()).append("\n");
            }
        }
        
        return sb.toString();
    }
}
