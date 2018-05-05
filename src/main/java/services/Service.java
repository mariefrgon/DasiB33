/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;


import com.google.maps.model.LatLng;
import fr.insalyon.dasi.proactifb3328.dao.ClientDao;
import fr.insalyon.dasi.proactifb3328.dao.EmployeDao;
import fr.insalyon.dasi.proactifb3328.util.Mail;
import fr.insalyon.dasi.proactifb3328.dao.InterventionDao;
import fr.insalyon.dasi.proactifb3328.dao.JpaUtil;
import fr.insalyon.dasi.proactifb3328.util.GeoTest;
import fr.insalyon.dasi.proactifb3328.util.PhoneNotificator;
import java.util.Date;
import java.util.List;
import modele.Client;
import modele.Employe;
import modele.Intervention;

/**
 *
 * @author ebosca
 */
public class Service {
    /**
     * service appelé par l'ihm pour inscrire un nouveau client
     * @param c le client à inscire
     * @return true si l'inscritpion dans la base de données s'est correctement déroulée, sinon retourne false
     */
    public static boolean inscriptionClient(Client c){
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        boolean result; 
       
        //Si la civilité est invalide, on envoie un mail d'invalidité et on retourne false;
       if(!c.getCivilite().equals("M") && !c.getCivilite().equals("M.") && !c.getCivilite().equals("Mme")&& !c.getCivilite().equals("Mme.")&& !c.getCivilite().equals("Dr") ){
            
            Mail.envoiMailErreur(c);
            return false;
        }
        
        try{
            ClientDao d = new ClientDao();
            d.insererClient(c);
            JpaUtil.validerTransaction();
            result = true;
            Mail.envoiMailValide(c);
            
        }catch(Exception ex){  
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            result = false;            
            Mail.envoiMailErreur(c);
        }
        JpaUtil.fermerEntityManager();
        return result;
    }
/**
 * service pour ajouter un employer
 * @param e l'employé à ajouter dans la base de données
 * @return true si l'inscritpion dans la base de données s'est correctement déroulée, sinon retourne false
 */
    public static boolean ajouterEmploye(Employe e) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        boolean result; 
        
        try {
            EmployeDao ed = new EmployeDao();  
            ed.insererEmploye(e);
            
            result = true;
            JpaUtil.validerTransaction();       
            
        } catch (Exception ex) {
            
            result = false;
            JpaUtil.annulerTransaction();
        }
        
        
        JpaUtil.fermerEntityManager();
        return result;
    }
    /**
     * service pour la connexion
     * @param numEmp le numéro de l'employé à récuperer
     * @return e l'objet représentant l'employé correspondant au numéro d'employé numEmp, null si aucun employe ne correspond ou si la transaction a échoué
     */
    public static Employe connexionEmploye(int numEmp){
        
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        Employe e = null;
        
        try {
            EmployeDao ed = new EmployeDao();
            e = ed.recupererEmploye(numEmp);
            
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            
            JpaUtil.annulerTransaction();
        }
        
        JpaUtil.fermerEntityManager();
        return e;
    }

    
    /**
     * Methode pour la connexion d'un client : recherche de l'ID donnée dans la base
     * @param id : l'ID entrée par le client dans l'ihm pour se connecter
     * @return c : le Client correspondant à l'ID s'il existe dans la base, null sinon ou si la transaction a échoué
     */
    public static Client connexionClient(int id) {
        
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        Client c = null;
        try {
            ClientDao cd = new ClientDao();
            c = cd.recupererClient(id);
            
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            
            JpaUtil.annulerTransaction();
        }
        
        JpaUtil.fermerEntityManager();
        return c;
    }
    
    /**
     * ajout d'une intervention 
     * @param intervention l'intervention à créer
     * @return statut un boolean à vrai si la demande a été reçue et affectée, false si aucun employé n'est disponible pour la traiter ou si la demande a échoué
     */
    public static boolean envoyerIntervention(Intervention intervention){  
        
        boolean statut; //boolean retourné par la méthode
       
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        try {
            EmployeDao ed = new EmployeDao();
            List<Employe> employeDispo = ed.recupererEmployesDisponibles(intervention.getDateDebut());
            LatLng interventionAddr = GeoTest.getLatLng(intervention.getClient().getAdresse());

            //si il y a au moins un employé disponible
            if (employeDispo.size() > 0) {
                
                Employe employeAffecte;
                LatLng currentAddr = GeoTest.getLatLng(employeDispo.get(0).getAdresse());
                double distanceMin = GeoTest.getTripDurationByBicycleInMinute(currentAddr, interventionAddr);
                employeAffecte = employeDispo.get(0);
                
                for (int i = 0; i < employeDispo.size(); i++) {
                    
                    currentAddr = GeoTest.getLatLng(employeDispo.get(i).getAdresse());
                    
                    if (GeoTest.getTripDurationByBicycleInMinute(currentAddr, interventionAddr) < distanceMin) {
                        
                        distanceMin = GeoTest.getTripDurationByBicycleInMinute(currentAddr, interventionAddr);
                        employeAffecte = employeDispo.get(i);
                    }
                    
                }
                //l'employe n'est plus disponible
                employeAffecte.setDisponibilite(false);
                ed.mettreAJourEmploye(employeAffecte);

                //on affecte l'employé le plus proche
                intervention.setEmploye(employeAffecte);
                InterventionDao id = new InterventionDao();
                id.insererIntervention(intervention);

                //Envoie d'une notification à l'employé affecté
                PhoneNotificator.notificationEmploye(intervention);
                
                statut = true;
                
            } else {
                
                statut = false;
            }
            
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            
            
            statut = false;
            JpaUtil.annulerTransaction();
        }
        
        JpaUtil.fermerEntityManager();
        return statut;
    }
    
    /**
     * service pour qu'un employé se connectant à l'application obtienne l'intervention sur laquelle il est affecté
     * @param e l'employé qui se connecte
     * @return i l'intervention sur laquelle l'employé est affecté, null si aucune intervention pour cet employé ou si transaction échoué
     */
    public static Intervention rechercheInterventionEnCoursEmploye(Employe e){
        
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        Intervention i = null;
        try {
            InterventionDao id = new InterventionDao();
            i = id.rechercherInterventionEnCoursEmploye(e);
            
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            
            JpaUtil.annulerTransaction();
        }
           
        JpaUtil.fermerEntityManager();
        
        return i;
    }
    
   /**
    * Marquer une intervention comme achevée en indiquand une heure de fin, un commentaire et si un éventuel problème a empèché le bon déroulement de l'intervention
    * @param i l'intervention à cloturer
    * @param pb boolean vrai si il y a eu un problème
    * @param commentaire commentaire laissé par l'employé à la résolution de l'intervention
    * @return true si la cloture d'intervention s'est correctement déroulée, sinon retourne false
    */
    public static boolean cloturerIntervention(Intervention i, boolean pb, String commentaire){
        
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        boolean result;
        
        try {
            i.setDateFin(new Date());
            i.setCommentaireFin(commentaire);
            i.setProbleme(pb);

            //On pousse la persistance
            InterventionDao id = new InterventionDao();
            id.mettreAJourIntervention(i);

            //l'employe est de nouveau disponible
            i.getEmploye().setDisponibilite(true);
            //On pousse la persistance sur l'employe
            EmployeDao ed = new EmployeDao();
            ed.mettreAJourEmploye(i.getEmploye());
            
            result = true;
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            
            result = false;
            JpaUtil.annulerTransaction();
        }
        
        JpaUtil.fermerEntityManager();
        return result;
    }
    
    /**
     * Méthode pour l'affichage de la carte des interventions de la journée
     * @return un liste de toutes les interventions du jour, null si aucune intervention ou si erreur lors de l'accès aux données
     */
    public static List<Intervention> rechercheInterventionDuJour(){
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        List<Intervention> liste = null;
        try {
            Date d = new Date();
            
            InterventionDao iDao = new InterventionDao();
            liste = iDao.obtenirInterventionsDuJour(d);
            
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            
            JpaUtil.annulerTransaction();
        }
        
        JpaUtil.fermerEntityManager();
        return liste;
    }
    /**
     * service pour renvoier un client l'histoirique des interventions qu'il à initiées
     * @param c le client qui souhaite consulter l'historique de ses interventions
     * @return  i la liste d'interventions concernant l'employé c, null si aucune historique ou si eurreur lors de l'accès aux données
     */
    public static List<Intervention> recupererHistoriqueIntervention(Client c){
        
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        List<Intervention> i = null;
        try {
            InterventionDao id = new InterventionDao();
            i = id.obtenirHistoriqueInterventionClient(c);
            
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        }
        
        JpaUtil.fermerEntityManager();
        return i;
    }
}

