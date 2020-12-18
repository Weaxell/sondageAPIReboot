package fr.esiea.sondageAPIReboot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private int idSondage;
    private String choix;
    private String idUtilisateur;

    public Vote() {
    }

    public Vote(int idSondage, String choix, String idUtilisateur) {
        this.idSondage = idSondage;
        this.choix = choix;
        this.idUtilisateur = idUtilisateur;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSondage() {
        return idSondage;
    }

    public void setIdSondage(int idSondage) {
        this.idSondage = idSondage;
    }

    public String getChoix() {
        return choix;
    }

    public void setChoix(String choix) {
        this.choix = choix;
    }

    public String getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(String idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
}
