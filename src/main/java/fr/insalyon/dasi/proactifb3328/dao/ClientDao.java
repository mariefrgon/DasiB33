/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactifb3328.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import modele.Client;

/**
 *
 * @author ebosca
 */
public class ClientDao {

    public ClientDao() {
    }
    
    public void insererClient(Client c){
        
        JpaUtil.obtenirEntityManager().persist(c);
        
    }
    
    public Client recupererClient(int id){
        
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query =  em.createQuery("Select c from Client c where c.nClient = :id");
        query.setParameter("id", id);
        List<Client> resultats = (List<Client>) query.getResultList();
        
        Client c = null;
        try {
            c = (Client) query.getSingleResult();
        } catch (Exception e) {
        }
        return c;
    }
    
}
