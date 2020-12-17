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
    private int idProprietaire;

    @ElementCollection
    private List<Integer> listSondage;

    public List<Integer> getListUtilisateurs() {
        return listUtilisateurs;
    }

    public void setListUtilisateurs(List<Integer> listUtilisateurs) {
        this.listUtilisateurs = listUtilisateurs;
    }

    @ElementCollection
    private List<Integer> listUtilisateurs;

    public SalleSondage() {
        /*
        if(listUtilisateurs == null)
            listUtilisateurs = new ArrayList<Integer>();
         */
    }

    public int getIdProprietaire() {
        return idProprietaire;
    }

    public void setIdProprietaire(int idProprietaire) {
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

    public List<Integer> getListSondage() {
        return listSondage;
    }

    public void addSondage(int idSondage) {
        this.listSondage.add(idSondage);
    }

    public void setListSondage(List<Integer> listSondage) {
        this.listSondage = listSondage;
    }

    public void addUser(int userid) {
        if(listUtilisateurs == null)
            listUtilisateurs = new ArrayList<Integer>();
        listUtilisateurs.add(userid);
    }

    @Override
    public String toString() {
        return "SalleSondage{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", listSondage=" + listSondage +
                '}';
    }
}
