package fr.esiea.sondageAPIReboot.web.controller;

import fr.esiea.sondageAPIReboot.dao.SalleDao;
import fr.esiea.sondageAPIReboot.dao.SondageDao;
import fr.esiea.sondageAPIReboot.dao.UtilisateurDao;
import fr.esiea.sondageAPIReboot.model.SalleSondage;
import fr.esiea.sondageAPIReboot.model.Sondage;
import fr.esiea.sondageAPIReboot.model.Utilisateur;
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
    UtilisateurDao utilisateurDao;
    @Autowired
    SondageDao sondageDao;

    /**
     *
     * @param userid
     * @return dans une liste toutes les salles auxquelles l'utilisateur a acces
     */
    @GetMapping(value = "/salles")
    public List<SalleSondage> listSalles(@RequestParam("userid") int userid) {
        List<SalleSondage> listSalles = new ArrayList<SalleSondage>();

        // System.out.println("################ Userid: " + userid);

        Utilisateur utilisateur = null;
        utilisateur = utilisateurDao.findById(userid);

        for(SalleSondage salle : salleDao.findAll()) {
            if(salle.getListUtilisateurs().contains(userid))
                listSalles.add(salle);
        }

        return listSalles;
    }

    /**
     *
     * @param id
     * @param userid
     * @return tous les sondages de la salle dans une liste si l'utilisateur possede un acces a cette salle
     */
    @GetMapping(value = "/salles/{id}")
    public List<Sondage> getSondagesOfSalle(@PathVariable int id, @RequestParam("userid") int userid) {
        List<Sondage> listSondages = new ArrayList<Sondage>();

        if(utilisateurDao.findById(userid).getListSalles().contains(id) == true) {
            for(int idSondage : salleDao.findById(id).getListSondage())
                listSondages.add(sondageDao.findById(idSondage));
        }

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

    @PostMapping(value = "/salles/{id}")
    public void adduser(@Valid @RequestBody SalleSondage salleSondage, @RequestParam("userid") int userid, @RequestParam("userid") int useraddid) {
        if(salleDao.findById(salleSondage.getId()).getListUtilisateurs().contains(userid)) {
            SalleSondage salle = salleDao.findById(salleSondage.getId());
            salle.addUser(useraddid);
            salleDao.save(salleSondage);
        }
    }
}
