package fr.esiea.sondageAPIReboot.web.controller;

import fr.esiea.sondageAPIReboot.dao.SalleDao;
import fr.esiea.sondageAPIReboot.dao.SondageDao;
import fr.esiea.sondageAPIReboot.model.SalleSondage;
import fr.esiea.sondageAPIReboot.model.Sondage;
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
    @GetMapping(value = "/salles/my")
    public List<SalleSondage> listSalles(@RequestParam("userid") int userid) {
        List<SalleSondage> listSalles = new ArrayList<SalleSondage>();

        // System.out.println("################ Userid: " + userid);

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
     * @param id
     * @param userid
     * @return tous les sondages de la salle dans une liste si l'utilisateur a acces a cette salle
     */
    @GetMapping(value = "/salles/{id}")
    public List<Sondage> getSondagesOfSalle(@PathVariable int id, @RequestParam("userid") int userid) {
        List<Sondage> listSondages = new ArrayList<Sondage>();

        if(salleDao.findById(id) != null)
            if(salleDao.findById(id).getListUtilisateurs() != null && salleDao.findById(id).getListUtilisateurs().contains(userid))
                for(int idsond : salleDao.findById(id).getListIdSondage())
                    listSondages.add(sondageDao.findById(idsond));

        return listSondages;
    }

    /**
     * Pemret a un utilisateur de creer une salle, il en devient le peoprietaire
     * @param salleSondage
     * @param userid
     * @return
     */
    @PostMapping(value = "/salles")
    public ResponseEntity<Void> addSalle(@Valid @RequestBody SalleSondage salleSondage, @RequestParam("userid") int userid) {
        salleSondage.setIdProprietaire(userid);

        // initalisation des champs ElementCollection
        salleSondage.setListUtilisateurs(new ArrayList<Integer>());
        salleSondage.setListSondage(new ArrayList<Integer>());

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
    @PostMapping(value = "/salles/{salleId}/adduser")
    public void adduser(@PathVariable int salleId, @RequestParam("userid") int userid, @RequestParam("useradd") int useradd) {
        // int idSalle = Integer.valueOf(salleId.charAt(0));
        int idSalle = salleId;
        if(salleDao.findById(idSalle).getListUtilisateurs().contains(userid)) {
            SalleSondage salle = salleDao.findById(idSalle);
            salle.addUser(useradd);
            salleDao.save(salle);
        }
    }
}
