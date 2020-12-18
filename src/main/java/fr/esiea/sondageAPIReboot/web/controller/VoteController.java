package fr.esiea.sondageAPIReboot.web.controller;

import fr.esiea.sondageAPIReboot.dao.SalleDao;
import fr.esiea.sondageAPIReboot.dao.SondageDao;
import fr.esiea.sondageAPIReboot.dao.VoteDao;
import fr.esiea.sondageAPIReboot.model.ResultatSondage;
import fr.esiea.sondageAPIReboot.model.SalleSondage;
import fr.esiea.sondageAPIReboot.model.Sondage;
import fr.esiea.sondageAPIReboot.model.Vote;
import fr.esiea.sondageAPIReboot.web.exceptions.BadRequestException;
import fr.esiea.sondageAPIReboot.web.exceptions.NotFoundException;
import fr.esiea.sondageAPIReboot.web.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class VoteController {
    @Autowired
    SalleDao salleDao;
    @Autowired
    SondageDao sondageDao;
    @Autowired
    VoteDao voteDao;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/sondages/{idSondage}/hasvoted")
    public Map<String, Boolean> hasvoted(@PathVariable int idSondage, @RequestParam String userid) {
        HashMap<String, Boolean> mapHasVoted = new HashMap<String, Boolean>();

        for(Vote vote : voteDao.findAll()) {
            if(vote.getIdUtilisateur().equals(userid) && vote.getIdSondage() == idSondage) {
                mapHasVoted.put(userid, true);
                return mapHasVoted;
            }
        }
        mapHasVoted.put(userid, false);
        return mapHasVoted;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/sondages/{idSondage}/voted")
    public List<String> getSondageResultat(@PathVariable int idSondage, @RequestParam String userid) {
        if(sondageDao.findById(idSondage) != null ) {
            ResultatSondage resulatSondage = new ResultatSondage(sondageDao.findById(idSondage).getReponses());

            Sondage sondage = sondageDao.findById(idSondage);
            if(sondage.isPublic()) {
                // on recupere tous les votes pour ce sondage
                for(Vote vote : voteDao.findAll()) {
                    if(vote.getIdSondage() == idSondage) {
                        resulatSondage.ajouterResultat(vote.getChoix(), 1);
                    }
                }
            }
            else {
                SalleSondage salle = salleDao.findById(sondage.getSalleId());
                if(salle != null) {
                    if(salle.getListUtilisateurs().contains(userid)) {
                        // on recupere tous les votes pour ce sondage
                        for(Vote vote : voteDao.findAll()) {
                            if(vote.getIdSondage() == idSondage) {
                                resulatSondage.ajouterResultat(vote.getChoix(), 1);
                            }
                        }
                    }
                    else {
                        throw new UnauthorizedException("Impossible de voir les votes : l'utilisateur n'a pas acces a ce sondage");
                    }
                }
            }

            return resulatSondage.getResultatEnliste();
        }
        else
            throw new NotFoundException("Le sondage demande n'existe pas");

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/sondages/{idSondage}/vote")
    public void voteSondage(@PathVariable int idSondage, @RequestParam String userid, @RequestParam String choixReponse) {
        Sondage sondage = sondageDao.findById(idSondage);

        if(sondage != null) {
            if(sondage.isPublic()) {
                if(sondage.getReponses().contains(choixReponse))
                    voteDao.save(new Vote(idSondage, choixReponse, userid));
            }
            else {
                SalleSondage salle = salleDao.findById(sondage.getSalleId());
                if(salle != null) {
                    // Si l'utilisateur a acces a la salle
                    // ET que le choix existe dans les reponses possibles du sondage
                    // alors on enregistre le vote
                    if(salle.getListUtilisateurs().contains(userid) && sondage.getReponses().contains(choixReponse)) {
                        voteDao.save(new Vote(idSondage, choixReponse, userid));
                    }
                    else
                        throw new UnauthorizedException("Impossible de voter : l'utilisateur n'a pas acces a ce sondage");
                }
            }
        }
    }
}
