/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.Date;
import javax.persistence.Entity;

/**
 *
 * @author gbailly
 */

@Entity
public class Livraison extends Intervention{
    
    String objet;
    String entreprise;
    
    /**
     *
     */
    public Livraison(){
        
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    /**
     *
     * @param objet objet à livrer
     * @param entreprise entreprise de livraison utilisée
     * @param client
     * @param description
     */
    public Livraison(String objet, String entreprise, Client client, String description) {
        super(client, description);
        this.objet = objet;
        this.entreprise = entreprise;
    }

    @Override
    public String getType() {
        return "Livraison";
    }
    
}
