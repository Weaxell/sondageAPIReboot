package fr.esiea.sondageAPIReboot.model;

import java.util.*;

public class ResultatSondage {
    HashMap<String, Integer> mapResulat;

    public ResultatSondage(List<String> listChoix) {
        mapResulat = new HashMap<String, Integer>();
        for(String choix : listChoix)
            mapResulat.put(choix, 0);
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

    public ArrayList<String> getResultatEnliste() {
        ArrayList<String> listRes = new ArrayList<String>();

        Iterator it = mapResulat.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            listRes.add(pair.getKey().toString());
            listRes.add(pair.getValue().toString());
            it.remove(); // avoids a ConcurrentModificationException
        }
        return listRes;
    }
}
