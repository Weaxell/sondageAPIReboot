package fr.esiea.sondageAPIReboot.model;

import java.util.HashMap;

public class ResultatSondage {
    HashMap<String, Integer> mapResulat;

    public ResultatSondage() {
        mapResulat = new HashMap<String, Integer>();
    }

    public void ajouterResultat(String choix, int nbVote) {
        if(mapResulat.containsKey(choix)) {
            mapResulat.put(choix, mapResulat.get(choix)+1);
        }
        else {
            mapResulat.put(choix, nbVote);
        }
    }

    public HashMap<String, Integer> getResultat() {
        return mapResulat;
    }
}
