package fr.esiea.sondageAPIReboot.dao;

import fr.esiea.sondageAPIReboot.model.Sondage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// <Sondage, Integer> correspond au type d'entite concernee, puis au type de l'id manipule
@Repository
public interface SondageDao extends JpaRepository<Sondage, Integer> {
    Sondage findById(int id);
    // List<Sondage> findAll(String recherche);
}
