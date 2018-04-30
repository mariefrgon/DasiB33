/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ebosca
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Intervention {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int nIntervention; 
    @ManyToOne
    Client client;
    @ManyToOne
    Employe employe;
    String description;
    @Temporal(TemporalType.DATE)
    Date dateDebut;
    @Temporal(TemporalType.DATE)
    Date dateFin;
    boolean probleme;
    String commentaireFin;
    
    /**
     *
     */
    public Intervention(){
        
    }

    /**
     *
     * @param client
     * @param description
     */
    public Intervention(Client client, String description) {
        this.client = client;
        this.employe = employe;
        this.description = description;
        this.dateDebut = new Date();
    }

    /**
     *
     * @return
     */
    public int getnIntervention() {
        return nIntervention;
    }

    /**
     *
     * @return
     */
    public Client getClient() {
        return client;
    }

    /**
     *
     * @return
     */
    public Employe getEmploye() {
        return employe;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return
     */
    public Date getDateDebut() {
        return dateDebut;
    }

    /**
     *
     * @return
     */
    public Date getDateFin() {
        return dateFin;
    }
    
    /**
     *
     * @return
     */
    public boolean isProbleme() {
        return probleme;
    }

    /**
     *
     * @return
     */
    public String getCommentaireFin() {
        return commentaireFin;
    }

    /**
     *
     * @param dateFin date de fin d'intervention
     */
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    /**
     *
     * @param employe
     */
    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    /**
     *
     * @param probleme true si l'employe a rencontré un problème lors de l'intervention, false sinon
     */
    public void setProbleme(boolean probleme) {
        this.probleme = probleme;
    }

    /**
     *
     * @param commentaireFin
     */
    public void setCommentaireFin(String commentaireFin) {
        this.commentaireFin = commentaireFin;
    }
    
    /**
     *
     * @return
     */
    public abstract String getType();
}
