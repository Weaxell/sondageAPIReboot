package fr.esiea.sondageAPIReboot.dao;

import fr.esiea.sondageAPIReboot.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurDao extends JpaRepository<Utilisateur, Integer> {
    Utilisateur findById(int id);
}
