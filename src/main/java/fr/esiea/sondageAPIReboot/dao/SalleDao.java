package fr.esiea.sondageAPIReboot.dao;

import fr.esiea.sondageAPIReboot.model.SalleSondage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalleDao extends JpaRepository<SalleSondage, Integer> {
    SalleSondage findById(int id);
}
