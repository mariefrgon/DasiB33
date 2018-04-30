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
public class Animal extends Intervention {
    
    String animal;
    
    /**
     *
     */
    public Animal(){
        
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getAnimal() {
        return animal;
    }

    /**
     *
     * @param animal
     * @param client
     * @param description
     */
    public Animal(String animal, Client client, String description) {
        super(client, description);
        this.animal = animal;
    }

    @Override
    public String getType() {
        return "Animal";
    }
    
}
