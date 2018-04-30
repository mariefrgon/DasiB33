/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.TemporalType;

import javax.persistence.Temporal;


/**
 *
 * @author ebosca
 */
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int nClient;
    
    private String civilite;
    private String nom;
    private String prenom;

    @Temporal(TemporalType.DATE)
    private Date dateDeNaissance;
    
    @Column(name = "ADRESSE", nullable = false)
    private String adresse;
    private String telephone;
    private String mail;

    /**
     *
     * @param civilite
     * @param prenom
     * @param nom
     * @param dateDeNaissance
     * @param adresse
     * @param telephone
     * @param mail
     */
    public Client(String civilite, String prenom, String nom, Date dateDeNaissance, String adresse, String telephone, String mail) {
        this.civilite = civilite;
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateDeNaissance;
        this.adresse = adresse;
        this.telephone = telephone;
        this.mail = mail;

    }

    /**
     *
     */
    protected Client() {
    }

    /**
     *
     * @return
     */
    public String getCivilite() {
        return civilite;
    }

    /**
     *
     * @return
     */
    public String getNom() {
        return nom;
    }

    /**
     *
     * @return
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     *
     * @return
     */
    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    /**
     *
     * @return
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     *
     * @return
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     *
     * @return
     */
    public String getMail() {
        return mail;
    }

    /**
     *
     * @return
     */
    public int getnClient() {
        return nClient;
    }   
      
}
