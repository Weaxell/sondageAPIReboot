package fr.esiea.sondageAPIReboot.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Sondage {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String titre;

    @ElementCollection
    private List<String> reponses;

    private boolean isPublic;
    private int idSalle;
    private String idProprietaire;


    public Sondage() {
    }

    // constructeur de test
    public Sondage(String titre, List<String> reponses, boolean isPublic, int idSalle, String idProprietaire) {
        this.titre = titre;
        this.reponses = reponses;
        this.isPublic = isPublic;
        this.idSalle = idSalle;
        this.idProprietaire = idProprietaire;
    }


    public String getIdProprietaire() {
        return idProprietaire;
    }

    public void setIdProprietaire(String idProprietaire) {
        this.idProprietaire = idProprietaire;
    }

    public int getSalleId() {
        return idSalle;
    }

    public void setIdSalle(int idSalle) {
        this.idSalle = idSalle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public List<String> getReponses() {
        return reponses;
    }

    public void setReponses(List<String> reponses) {
        this.reponses = reponses;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
