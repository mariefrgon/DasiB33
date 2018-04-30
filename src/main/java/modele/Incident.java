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
public class Incident extends Intervention {
    
    /**
     *
     */
    public Incident(){
        
    }

    /**
     *
     * @param client
     * @param description
     */
    public Incident(Client client, String description) {
        super(client, description);
    }

    @Override
    public String getType() {
       return "Incident";
    }
    
    
}
