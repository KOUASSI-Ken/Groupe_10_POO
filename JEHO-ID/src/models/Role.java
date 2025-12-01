package models;

import java.util.HashSet;
import java.util.Set;

public class Role {
    private String nom;
    private String description;
    private Set<Permission> permissions;
    
    public Role(String nom, String description) {
        this.nom = nom;
        this.description = description;
        this.permissions = new HashSet<>();
    }
    
    public Role(String nom, String description, Set<Permission> permissions) {
        this.nom = nom;
        this.description = description;
        this.permissions = new HashSet<>(permissions);
    }
    
    // Gestion des permissions
    public void ajouterPermission(Permission permission) {
        permissions.add(permission);
    }
    
    public void retirerPermission(Permission permission) {
        permissions.remove(permission);
    }
    
    public boolean aPermission(Permission permission) {
        return permissions.contains(permission);
    }
    
    public Set<Permission> getPermissions() {
        return new HashSet<>(permissions);
    }
    
    // Getters et Setters
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Rôle: ").append(nom).append("\n");
        sb.append("Description: ").append(description).append("\n");
        sb.append("Permissions (").append(permissions.size()).append("):\n");
        for (Permission perm : permissions) {
            sb.append("  ✓ ").append(perm.getDescription()).append("\n");
        }
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Role role = (Role) obj;
        return nom.equals(role.nom);
    }
    
    @Override
    public int hashCode() {
        return nom.hashCode();
    }
}
