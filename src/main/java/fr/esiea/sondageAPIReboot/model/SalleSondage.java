package fr.esiea.sondageAPIReboot.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SalleSondage {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String idProprietaire;

    @ElementCollection
    private List<Integer> listIdSondage;

    public List<String> getListUtilisateurs() {
        return listUtilisateurs;
    }

    public void setListUtilisateurs(List<String> listUtilisateurs) {
        this.listUtilisateurs = listUtilisateurs;
    }

    @ElementCollection
    private List<String> listUtilisateurs;

    public SalleSondage() {
        /*
        if(listUtilisateurs == null)
            listUtilisateurs = new ArrayList<String>();
         */
    }

    // constructeur de test
    public SalleSondage(String nom, String idProprietaire) {
        this.nom = nom;
        this.idProprietaire = idProprietaire;
        listIdSondage = new ArrayList<Integer>();
        listUtilisateurs = new ArrayList<String>();

    }

    public String getIdProprietaire() {
        return idProprietaire;
    }

    public void setIdProprietaire(String idProprietaire) {
        this.idProprietaire = idProprietaire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Integer> getListIdSondage() {
        return listIdSondage;
    }

    public void addSondage(int idSondage) {
        this.listIdSondage.add(idSondage);
    }

    public void setListIdSondage(List<Integer> listSondage) {
        this.listIdSondage = listSondage;
    }

    public void addUser(String userid) {
        if(listUtilisateurs == null)
            listUtilisateurs = new ArrayList<String>();
        if(!listUtilisateurs.contains(userid))
            listUtilisateurs.add(userid);
    }

    @Override
    public String toString() {
        return "SalleSondage{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", listSondage=" + listIdSondage +
                '}';
    }
}
