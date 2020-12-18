package fr.esiea.sondageAPIReboot.model;

import java.util.HashMap;

public class ResultatSondage {
    HashMap<String, Integer> mapResulat;

    public ResultatSondage() {
        mapResulat = new HashMap<String, Integer>();
    }

    public void ajouterResultat(String choix, int nbVote) {
        // si la valeur exiset deja on incremente le nb de votes pour cette valeur
        if(mapResulat.containsKey(choix)) {
            mapResulat.put(choix, mapResulat.get(choix)+1);
        }
        // sinon on cree cette valeur
        else {
            mapResulat.put(choix, nbVote);
        }
    }

    public HashMap<String, Integer> getResultat() {
        return mapResulat;
    }
}
