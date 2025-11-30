package medipass.dao.interfaces;

import medipass.models.Administrateur;

public interface AdministrateurDAOInterface extends GenericDAO<Administrateur, Integer> {
    Administrateur findById(Integer id);
}