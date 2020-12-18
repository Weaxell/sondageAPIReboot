package fr.esiea.sondageAPIReboot.web.controller;

import fr.esiea.sondageAPIReboot.dao.SalleDao;
import fr.esiea.sondageAPIReboot.dao.SondageDao;
import fr.esiea.sondageAPIReboot.model.SalleSondage;
import fr.esiea.sondageAPIReboot.model.Sondage;
import fr.esiea.sondageAPIReboot.web.exceptions.NotFoundException;
import fr.esiea.sondageAPIReboot.web.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SalleController {
    @Autowired
    SalleDao salleDao;
    @Autowired
    SondageDao sondageDao;

    /**
     *
     * @param userid
     * @return dans une liste toutes les salles auxquelles l'utilisateur a acces
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/salles/my")
    public List<SalleSondage> listSalles(@RequestParam("userid") String userid) {
        List<SalleSondage> listSalles = new ArrayList<SalleSondage>();

        for(SalleSondage sallesond : salleDao.findAll()) {
            if(sallesond.getListUtilisateurs() != null) {
                if(sallesond.getListUtilisateurs().contains(userid))
                    listSalles.add(sallesond);
            }
        }

        return listSalles;
    }

    /**
     *
     * @param salleId
     * @param userid
     * @return tous les sondages de la salle dans une liste si l'utilisateur a acces a cette salle
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/salles/{salleId}")
    public List<Sondage> getSondagesOfSalle(@PathVariable int salleId, @RequestParam("userid") String userid) {
        List<Sondage> listSondages = new ArrayList<Sondage>();
        SalleSondage salle = salleDao.findById(salleId);

        // si la salle existe
        if(salle != null) {
            // si l'utilisateur a acces a la salle
            if(salle.getListUtilisateurs().contains(userid)) {
                // on ajoute a la liste tous les sondages
                for(int idSondage : salle.getListIdSondage()) {
                    listSondages.add(sondageDao.findById(idSondage));
                }
            }
            return listSondages;
        }
        else
            throw new NotFoundException("La salle demandee n'existe pas");
    }

    /**
     * Pemret a un utilisateur de creer une salle, il en devient le peoprietaire
     * @param salleSondage
     * @param userid
     * @return
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/salles")
    public ResponseEntity<Void> addSalle(@Valid @RequestBody SalleSondage salleSondage, @RequestParam("userid") String userid) {
        salleSondage.setIdProprietaire(userid);

        // initalisation des champs ElementCollection
        salleSondage.setListUtilisateurs(new ArrayList<String>());
        salleSondage.setListIdSondage(new ArrayList<Integer>());

        salleSondage.addUser(userid);
        SalleSondage salleAdded = salleDao.save(salleSondage);

        if(salleAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salleAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    /**
     * Permet d'ajouter un utilisateur dans une salle
     * @param salleId
     * @param userid
     * @param userid, useradd
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/salles/{salleId}/adduser")
    public void adduser(@PathVariable int salleId, @RequestParam("userid") String userid, @RequestParam("useradd") String useradd) {
        int idSalle = salleId;
        if(salleDao.findById(idSalle) != null) {
            if(salleDao.findById(idSalle).getListUtilisateurs().contains(userid)) {
                SalleSondage salle = salleDao.findById(idSalle);
                salle.addUser(useradd);
                salleDao.save(salle);
            }
            else
                throw new UnauthorizedException("Vous n'etes pas autorises a acceder a cette salle");
        }
        else
            throw new NotFoundException("Cette salle n'existe pas");

    }
}
