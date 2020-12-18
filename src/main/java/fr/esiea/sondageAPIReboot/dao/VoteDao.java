package fr.esiea.sondageAPIReboot.dao;

import fr.esiea.sondageAPIReboot.model.Sondage;
import fr.esiea.sondageAPIReboot.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteDao extends JpaRepository<Vote, Integer> {
    Vote findById(int id);
}
