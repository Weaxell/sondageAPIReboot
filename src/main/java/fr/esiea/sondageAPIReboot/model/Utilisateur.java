package fr.esiea.sondageAPIReboot.model;


import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Utilisateur {
    @Id
    private int id;
    private String pseudo;
    private String passwd;

    @ElementCollection
    private List<Integer> listSalles;

    public List<Integer> getListSalles() {
        return listSalles;
    }

    public void setListSalles(List<Integer> listSalles) {
        this.listSalles = listSalles;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
