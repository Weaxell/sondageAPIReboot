package fr.esiea.sondageAPIReboot.web.controller;

import fr.esiea.sondageAPIReboot.dao.SalleDao;
import fr.esiea.sondageAPIReboot.dao.SondageDao;
import fr.esiea.sondageAPIReboot.model.SalleSondage;
import fr.esiea.sondageAPIReboot.model.Sondage;
import fr.esiea.sondageAPIReboot.web.exceptions.NotFoundException;
import fr.esiea.sondageAPIReboot.web.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SondageController {
    @Autowired
    SondageDao sondageDao;
    @Autowired
    SalleDao salleDao;


    /*
    ============ DEBUT MAPPING GET ============
     */

    /**
     *
     * @return tous les sondages publics
     */
    @GetMapping(value = "/sondages/public")
    public List<Sondage> listSondages() {
        List<Sondage> sondageList = new ArrayList<Sondage>();

        for(Sondage sond : sondageDao.findAll()) {
            if(sond.isPublic() == true) {
                sondageList.add(sond);
            }
        }
        return sondageList;
    }

    /**
     *
     * @param id
     * @param userid
     * @return un sondage specifique en fonction de son id
     */
    @GetMapping(value = "/sondages/{id}")
    public Sondage getSondage(@PathVariable int id, @RequestParam("userid") String userid) {
        Sondage sondage = sondageDao.findById(id);
        // si le sondage existe
        if(sondage != null) {
            if(sondage.isPublic())
                return sondage;
            // sinon, si l'utilisateur a acces a la salle ou est le sondage
            else if(salleDao.findById(sondage.getSalleId()).getListUtilisateurs().contains(userid))
                return sondage;
            // sinon l'utilisateur n'a pas acces au sondage, on genere une exception UnauthorizedException
            else
                throw new UnauthorizedException("L'utilisateur n'a pas acces au sondage demande");
        }
        else {
            throw new NotFoundException("Le sondage d'id " + id + " n'existe pas");
        }
    }
    /*
    ============ FIN MAPPING GET ============
     */


    /*
    ============ DEBUT MAPPING POST ============
     */

    /**
     * CREATION DE SONDAGE
     * Permet a un utilisateur de creer un sondage s'il est public ou s'il la creation se fait dans une salle à laquelle l'utilisateur a acces
     * @param sondage
     * @param userid
     */
    @PostMapping(value = "/sondages")
    public void addSondage(@Valid @RequestBody Sondage sondage, @RequestParam("userid") String userid) {
        sondage.setIdProprietaire(userid);
        if(sondage.isPublic()) {
            sondage.setIdSalle(-1);
            sondageDao.save(sondage);
        }
        else if(salleDao.findById(sondage.getSalleId()) != null) {
            if(salleDao.findById(sondage.getSalleId()).getListUtilisateurs().contains(userid))
                sondageDao.save(sondage);
        }
        else
            throw new UnauthorizedException("Action non autorisee");
    }

    @PostMapping(value = "/salles/{idSalle}/newSondage")
    public void addSondagePrive(@PathVariable int idSalle, @Valid @RequestBody Sondage sondage, @RequestParam("userid") String userid) {
        SalleSondage salle = salleDao.findById(idSalle);
        if(salle != null) {
            if(salle.getListUtilisateurs().contains(userid)) {
                sondage.setIdSalle(idSalle);
                sondage.setIdProprietaire(userid);
                Sondage sondageAdded = sondageDao.save(sondage);
                salle.addSondage(sondageAdded.getId());
                salleDao.save(salle);
            }
            else
                throw new UnauthorizedException("L'utilisateur " + userid + " n'a pas acces a la salle");
        }
        else
            throw new NotFoundException("Tentative d'acces a une salle qui n'existe pas");
    }
    /*
    ============ FIN MAPPING POST ============
     */



    /*
    ============ DEBUT MAPPINGS PLUS PARTICULIERS (A USAGE UNIQUE, ETC... ============
     */

    @DeleteMapping(value="/sondages/{id}")
    public void effacerSondage(@Valid @RequestBody Sondage sondage, @RequestParam("userid") String userid) {
        Sondage sond = sondageDao.findById(sondage.getId());

        // on commence par verifier que le sondage existe
        if(sond != null) {
            // on verifie que le demandeur de la suppression est bien son proprietaire
            if(sond.getIdProprietaire() == userid) {
                // on dereference le sondage dans les salles
                for(SalleSondage salle : salleDao.findAll())
                sondageDao.delete(sond);
            }
        }
        else
            throw new NotFoundException("Le sondage demande n'existe pas");
    }

    @PutMapping(value = "/sondages")
    public void modifierSondage(@Valid @RequestBody Sondage sondage, @RequestParam("userid") String userid) {
        sondage.setIdProprietaire(userid);
        // seulement le proprietaire peut changer le sondage public
        if (sondage.getIdProprietaire() == userid)
            sondageDao.save(sondage);
        else
            throw new UnauthorizedException("Action non autorisee");
    }
}
