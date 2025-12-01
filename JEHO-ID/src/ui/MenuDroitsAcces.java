package ui;

import models.Permission;
import models.Role;
import services.RoleService;
import services.ProfessionnelService;
import java.util.Scanner;
import java.util.Set;

public class MenuDroitsAcces {
    
    private final RoleService roleService;
    private final ProfessionnelService professionnelService;
    private final Scanner scanner;
    
    public MenuDroitsAcces(RoleService roleService, ProfessionnelService professionnelService) {
        this.roleService = roleService;
        this.professionnelService = professionnelService;
        this.scanner = new Scanner(System.in);
    }
    
    public void afficherMenu() {
        while (true) {
            System.out.println("\n===== GESTION DES DROITS D'ACCÈS =====");
            System.out.println("1. Voir tous les rôles");
            System.out.println("2. Créer un nouveau rôle");
            System.out.println("3. Supprimer un rôle");
            System.out.println("4. Voir permissions d'un utilisateur");
            System.out.println("5. Assigner un rôle à un utilisateur");
            System.out.println("6. Retirer un rôle d'un utilisateur");
            System.out.println("7. Ajouter une permission à un rôle");
            System.out.println("8. Retirer une permission d'un rôle");
            System.out.println("9. Initialiser les rôles par défaut");
            System.out.println("0. Retour");
            System.out.print("Choix : ");
            
            try {
                int choix = Integer.parseInt(scanner.nextLine());
                
                switch (choix) {
                    case 1 -> voirTousLesRoles();
                    case 2 -> creerRole();
                    case 3 -> supprimerRole();
                    case 4 -> voirPermissionsUtilisateur();
                    case 5 -> assignerRoleUtilisateur();
                    case 6 -> retirerRoleUtilisateur();
                    case 7 -> ajouterPermissionRole();
                    case 8 -> retirerPermissionRole();
                    case 9 -> initialiserRolesParDefaut();
                    case 0 -> { return; }
                    default -> System.out.println("❌ Choix invalide !");
                }
            } catch (Exception e) {
                System.out.println("❌ Erreur : " + e.getMessage());
            }
        }
    }
    
    private void voirTousLesRoles() {
        System.out.println("\n" + roleService.afficherRoles());
    }
    
    private void creerRole() {
        System.out.println("\n--- CRÉATION D'UN NOUVEAU RÔLE ---");
        
        System.out.print("Nom du rôle : ");
        String nom = scanner.nextLine();
        
        System.out.print("Description : ");
        String description = scanner.nextLine();
        
        try {
            Role role = roleService.creerRole(nom, description);
            System.out.println("✅ Rôle créé : " + role.getNom());
            
            // Demander si on veut ajouter des permissions
            System.out.print("Voulez-vous ajouter des permissions maintenant ? (O/N) : ");
            if ("O".equalsIgnoreCase(scanner.nextLine())) {
                ajouterPermissionsARole(role);
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
    }
    
    private void supprimerRole() {
        System.out.println("\n--- SUPPRESSION D'UN RÔLE ---");
        
        System.out.print("Nom du rôle à supprimer : ");
        String nom = scanner.nextLine();
        
        if (roleService.supprimerRole(nom)) {
            System.out.println("✅ Rôle supprimé : " + nom);
        } else {
            System.out.println("❌ Impossible de supprimer ce rôle (rôle par défaut ou inexistant)");
        }
    }
    
    private void voirPermissionsUtilisateur() {
        System.out.println("\n--- PERMISSIONS UTILISATEUR ---");
        
        System.out.print("ID de l'utilisateur : ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            
            System.out.println("\n" + roleService.afficherPermissionsUtilisateur(id));
            
        } catch (Exception e) {
            System.out.println("❌ ID invalide");
        }
    }
    
    private void assignerRoleUtilisateur() {
        System.out.println("\n--- ASSIGNATION D'UN RÔLE ---");
        
        System.out.print("ID de l'utilisateur : ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            
            System.out.println("Rôles disponibles :");
            for (Role role : roleService.getTousLesRoles()) {
                System.out.println("  - " + role.getNom() + " : " + role.getDescription());
            }
            
            System.out.print("Nom du rôle à assigner : ");
            String nomRole = scanner.nextLine();
            
            if (roleService.assignerRoleUtilisateur(id, nomRole)) {
                System.out.println("✅ Rôle assigné avec succès");
            } else {
                System.out.println("❌ Impossible d'assigner ce rôle");
            }
            
        } catch (Exception e) {
            System.out.println("❌ ID invalide");
        }
    }
    
    private void retirerRoleUtilisateur() {
        System.out.println("\n--- RETRAIT D'UN RÔLE ---");
        
        System.out.print("ID de l'utilisateur : ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            
            Set<Role> rolesUser = roleService.getRolesUtilisateur(id);
            if (rolesUser.isEmpty()) {
                System.out.println("❌ Cet utilisateur n'a aucun rôle");
                return;
            }
            
            System.out.println("Rôles actuels de l'utilisateur :");
            for (Role role : rolesUser) {
                System.out.println("  - " + role.getNom());
            }
            
            System.out.print("Nom du rôle à retirer : ");
            String nomRole = scanner.nextLine();
            
            if (roleService.retirerRoleUtilisateur(id, nomRole)) {
                System.out.println("✅ Rôle retiré avec succès");
            } else {
                System.out.println("❌ Impossible de retirer ce rôle");
            }
            
        } catch (Exception e) {
            System.out.println("❌ ID invalide");
        }
    }
    
    private void ajouterPermissionRole() {
        System.out.println("\n--- AJOUT PERMISSION À UN RÔLE ---");
        
        System.out.println("Rôles disponibles :");
        for (Role role : roleService.getTousLesRoles()) {
            System.out.println("  - " + role.getNom());
        }
        
        System.out.print("Nom du rôle : ");
        String nomRole = scanner.nextLine();
        
        Role role = roleService.getRole(nomRole);
        if (role == null) {
            System.out.println("❌ Rôle non trouvé");
            return;
        }
        
        ajouterPermissionsARole(role);
    }
    
    private void ajouterPermissionsARole(Role role) {
        System.out.println("\nPermissions disponibles :");
        Permission[] permissions = Permission.values();
        for (int i = 0; i < permissions.length; i++) {
            System.out.printf("%2d. %s %s\n", 
                i + 1, 
                role.aPermission(permissions[i]) ? "✓" : " ", 
                permissions[i].getDescription());
        }
        
        System.out.print("Numéro de la permission à ajouter : ");
        try {
            int choix = Integer.parseInt(scanner.nextLine());
            if (choix >= 1 && choix <= permissions.length) {
                Permission permission = permissions[choix - 1];
                
                if (roleService.ajouterPermissionRole(role.getNom(), permission)) {
                    System.out.println("✅ Permission ajoutée : " + permission.getDescription());
                } else {
                    System.out.println("❌ Erreur lors de l'ajout");
                }
            } else {
                System.out.println("❌ Choix invalide");
            }
        } catch (Exception e) {
            System.out.println("❌ Choix invalide");
        }
    }
    
    private void retirerPermissionRole() {
        System.out.println("\n--- RETRAIT PERMISSION D'UN RÔLE ---");
        
        System.out.println("Rôles disponibles :");
        for (Role role : roleService.getTousLesRoles()) {
            System.out.println("  - " + role.getNom());
        }
        
        System.out.print("Nom du rôle : ");
        String nomRole = scanner.nextLine();
        
        Role role = roleService.getRole(nomRole);
        if (role == null) {
            System.out.println("❌ Rôle non trouvé");
            return;
        }
        
        System.out.println("Permissions actuelles :");
        Set<Permission> permissions = role.getPermissions();
        int i = 1;
        for (Permission perm : permissions) {
            System.out.printf("%2d. %s\n", i++, perm.getDescription());
        }
        
        if (permissions.isEmpty()) {
            System.out.println("Ce rôle n'a aucune permission");
            return;
        }
        
        System.out.print("Numéro de la permission à retirer : ");
        try {
            int choix = Integer.parseInt(scanner.nextLine());
            if (choix >= 1 && choix <= permissions.size()) {
                Permission[] permArray = permissions.toArray(new Permission[0]);
                Permission permission = permArray[choix - 1];
                
                if (roleService.retirerPermissionRole(role.getNom(), permission)) {
                    System.out.println("✅ Permission retirée : " + permission.getDescription());
                } else {
                    System.out.println("❌ Erreur lors du retrait");
                }
            } else {
                System.out.println("❌ Choix invalide");
            }
        } catch (Exception e) {
            System.out.println("❌ Choix invalide");
        }
    }
    
    private void initialiserRolesParDefaut() {
        System.out.println("\n--- INITIALISATION DES RÔLES PAR DÉFAUT ---");
        
        System.out.print("Initialiser les rôles pour tous les utilisateurs ? (O/N) : ");
        if ("O".equalsIgnoreCase(scanner.nextLine())) {
            
            var professionnels = professionnelService.getTousLesProfessionnels();
            int count = 0;
            
            for (var pro : professionnels) {
                roleService.assignerRoleParDefaut(pro.getId(), pro.getRole());
                count++;
            }
            
            System.out.println("✅ Rôles initialisés pour " + count + " utilisateurs");
        } else {
            System.out.println("❌ Initialisation annulée");
        }
    }
}
