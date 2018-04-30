/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author ebosca
 */
@Entity
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int numEmp;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    private int hDebut;
    private int hFin;
    private boolean disponible;

    /**
     *
     */
    public Employe() {
        this.disponible = true;
    }

    /**
     *
     * @param numEmp numéro de l'employé
     * @param prenom
     * @param nom
     * @param telephone
     * @param adresse
     * @param hDebut heure de début de travail
     * @param hFin heure de fin de travail
     */
    public Employe(int numEmp, String prenom, String nom, String telephone, String adresse, int hDebut, int hFin) {
        this.numEmp = numEmp;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.hDebut = hDebut;
        this.hFin = hFin;
        this.disponible = true;
    }
    
    /**
     *
     * @return
     */
    public int getNumEmp() {
        return numEmp;
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
    public String getTelephone() {
        return telephone;
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
    public int gethDebut() {
        return hDebut;
    }

    /**
     *
     * @return
     */
    public int gethFin() {
        return hFin;
    }

    /**
     *
     * @param d disponibilité de l'employé, true si disponible, false sinon
     */
    public void setDisponibilite(boolean d) {
       this.disponible = d;
    }

    
    
    
    
}
