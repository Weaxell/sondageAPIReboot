package fr.esiea.sondageAPIReboot;

import fr.esiea.sondageAPIReboot.dao.SalleDao;
import fr.esiea.sondageAPIReboot.dao.SondageDao;
import fr.esiea.sondageAPIReboot.model.SalleSondage;
import fr.esiea.sondageAPIReboot.model.Sondage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class SondageApiRebootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SondageApiRebootApplication.class, args);
	}


	// insertion de quelques donnees de test
	@Bean
	public CommandLineRunner demo(SalleDao salleDao, SondageDao sondageDao) {
		return (args) -> {
			// deux salles
			if(salleDao.findAll().size() < 2) {
				SalleSondage salle1 = new SalleSondage("Salle de Simon", "simon");
				salle1.addUser("simon");
				SalleSondage salle1added = salleDao.save(salle1);

				SalleSondage salle2 = new SalleSondage("Salle d'Edward", "edward");
				salle2.addUser("edward");
				SalleSondage salle2added = salleDao.save(salle2);

				// quatre sondages
				// deux sondages publics
				ArrayList<String> listReponses = new ArrayList<String>();
				listReponses.add("rep1");
				listReponses.add("rep2");
				listReponses.add("rep3");
				sondageDao.save(new Sondage("Sondage public 1", listReponses, true, -1, "edward"));
				sondageDao.save(new Sondage("Second sondage public 2", listReponses, true, -1, "simon"));

				// deux sondages prives
				Sondage sond1 = new Sondage("Sondage prive 1", listReponses, false, 1, "edward");
				sond1.setIdSalle(salle1added.getId());
				sond1.setIdProprietaire("edward");
				Sondage sondage1added = sondageDao.save(sond1);
				salle1added.addSondage(sondage1added.getId());
				salleDao.save(salle1added);

				Sondage sond2 = new Sondage("Second Sondage prive 2", listReponses, false, 1, "simon");
				sond2.setIdSalle(salle2added.getId());
				sond2.setIdProprietaire("simon");
				Sondage sondage2added = sondageDao.save(sond2);
				salle2added.addSondage(sondage2added.getId());
				salleDao.save(salle2added);
			}
		};
	}

}
