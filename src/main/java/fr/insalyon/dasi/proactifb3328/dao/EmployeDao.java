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
import modele.Employe;

/**
 *
 * @author gbailly
 */
public class EmployeDao {
    
    public EmployeDao(){
        
    }
    
    public void insererEmploye(Employe e){
        
        JpaUtil.obtenirEntityManager().persist(e);
    }
    
    public void mettreAJourEmploye(Employe e){
        
        JpaUtil.obtenirEntityManager().merge(e);
    }
    
    public List<Employe> recupererEmployesDisponibles(Date dateDebut){
        
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query =  em.createQuery("Select e from Employe e where e.disponible = true and e.hDebut <= :hi and e.hFin >= :hi");
        query.setParameter("hi", dateDebut.getHours());
        
        List<Employe> resultats = null;
        try {
            resultats = (List<Employe>) query.getResultList();
        } catch (Exception e) {
            
        }
        return resultats;
    }

    public Employe recupererEmploye(int numEmp) {
        
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query = em.createQuery("Select e from Employe e where e.numEmp = :num");
        query.setParameter("num", numEmp);
        
        Employe e = null;
        try {
            e = (Employe) query.getSingleResult();
        } catch (Exception ex) {
        }
        
        return e;
    }
}
