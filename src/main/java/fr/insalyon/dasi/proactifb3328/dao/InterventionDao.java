/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactifb3328.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import modele.Client;
import modele.Employe;
import modele.Intervention;

/**
 *
 * @author gbailly
 */
public class InterventionDao {
    
    public InterventionDao(){
    
    }
    
    public void insererIntervention(Intervention i){
        
        JpaUtil.obtenirEntityManager().persist(i);
        
    }
    
     public void mettreAJourIntervention(Intervention i){
        
        JpaUtil.obtenirEntityManager().merge(i);
    }
    
    public Intervention rechercherInterventionEnCoursEmploye(Employe e){  
        
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query =  em.createQuery("Select i from Intervention i where i.employe = :emp and i.dateFin = null");
        query.setParameter("emp", e);
      
        Intervention i = null;
        try {
            i = (Intervention) query.getSingleResult();
        } catch (Exception ex) {
            
        }
        return i;
    }
    
    public List<Intervention> obtenirInterventionsDuJour(Date d){
        List<Intervention> liste = null;
        
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query =  em.createQuery("Select i from Intervention i where i.dateDebut = :date");
        query.setParameter("date", d);

        
        try {
            liste = (List<Intervention>) query.getResultList();
        } catch (Exception e) {
            System.err.println(e);
        }
        
        return liste;
    }
    
    public List<Intervention> obtenirHistoriqueInterventionClient(Client c){
        
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query = em.createQuery("Select i from Intervention i where i.client = :c");
        query.setParameter("c", c);
        
        List<Intervention> result = null;
        try {
            result = (List<Intervention>) query.getResultList();
        } catch (Exception e) {
        }
        
        return result;
    }
}
