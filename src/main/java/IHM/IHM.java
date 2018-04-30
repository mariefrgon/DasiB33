/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IHM;

import fr.insalyon.dasi.proactifb3328.dao.JpaUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import modele.Animal;
import modele.Client;
import modele.Employe;
import modele.Incident;
import modele.Intervention;
import modele.Livraison;
import services.Service;


/**
 *
 * @author ebosca
 */
public class IHM {
    public static void main (String [] args) throws ParseException{
        JpaUtil.init();
        
        //Test Inscriptions Clients
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
            Date d = sdf.parse("12/03/1985");
        Client c = new Client("M.", "Robert", "Duchamps", d, "28 rue Bonnet 69100 Villeurbanne", "06.05.04.08.12", "robert.dudu@gmail.com");
        Service.inscriptionClient(c);
        
        Client y = new Client("M.", "Gégé", "Dujardin", d, "39 rue de la Feyssine 69100 Villeurbanne", "06.02.27.89.02", "jean.michel.furieux@da.com");
        Service.inscriptionClient(y);
        
        //Test insciption Client civilité non conforme
        Client h = new Client("Lrd", "Jacques", "Chirac", d, "37 rue de bellevue 37270 Larçay", "02.47.50.38.61", "jaki.chichi@gmail.com");
        Service.inscriptionClient(h);
        
        //Fin test inscription client
        
            //Tests inscription employés
            Employe e = new Employe(568,"Patrice", "Evra", "067598451","3 rue de la pomme 38540 Heyrieux", 7,18 );
            Service.ajouterEmploye(e);
            Employe f = new Employe(569,"jules", "Trouille", "067598451","20 avenue Alvert Einstein 69100 Villeurbanne", 7,19 );
            Service.ajouterEmploye(f);
            Employe nuit = new Employe(600,"Joseph", "DispoLaNuit", "067598451","20 avenue Alvert Einstein 69100 Villeurbanne", 22,23 );
            Service.ajouterEmploye(nuit);
            
            //Si on ajoute un employé avec le même numéro
            Employe g = new Employe(569,"Louis", "pasMoi", "067598451","Avenue de Sévigné 372700 Tours", 7,20 );
            System.out.println(Service.ajouterEmploye(g)); //affichage de false dans la console

            Employe louis = new Employe(570,"Louis", "pasMoi", "067598451","Avenue de Sévigné 372700 Tours", 7,20 );
            System.out.println(Service.ajouterEmploye(louis)); //affichage de true dans la console
            //Fin test inscription Employé
            
            //Tests connexion Employé
            Employe x = Service.connexionEmploye(569);
            System.out.println(x.getNom()); //Affichage du nom de l'employé récupéré dans la console Trouille ici
            
            //Test de connexion avec un identifiant inexistant
            Employe fantome = Service.connexionEmploye(850);
            try {
                System.out.println(fantome.getNom());//affichage null 
            } catch (NullPointerException ex) {
                System.out.println("numéro employé inexistant");
            }
            //Fin test connexion Employé
            
            //Test connexion Client
            Client u = Service.connexionClient(1);
            System.out.println(u.getNom());
            
            Client fantome2 = Service.connexionClient(12051);
            try{
                System.out.println(fantome2.getNom());
            }catch(NullPointerException ex){
                System.out.println("numéro client inexistant");
            }
            //Fin test connexion Client
            
            //Test envoyer Intervention
            //test de proximité
            Intervention i = new Animal("chien",c,"Je suis la première intervention"); //Doit être attribué à Trouille (le plus près)
            System.out.println("la demande a réussi ?" + Service.envoyerIntervention(i));

            Intervention j = new Incident(c, "je suis la seconde intervention"); //Doit être attribué à Evra
            System.out.println("la demande a réussi ?" + Service.envoyerIntervention(j));

            Livraison k = new Livraison("table", "DHL", c, "je suis la troisième intervention"); //Doit être attribué à pasMoi
            System.out.println("la demande a réussi ?" + Service.envoyerIntervention(k));
            
            //test de non disponibilité d'employé
            Livraison l = new Livraison("table", "DHL", c, "je suis l'intervention de trop");
            System.out.println("la demande a réussi ?" + Service.envoyerIntervention(l)); //doit retourner false, plus d'employés dispo car le seul qui reste n'est dispo que la nuit
            //Fin test envoyer Intervention
            
            
            //Test recherche Intervention en cours Employe
            
            Intervention interventionTrouille = Service.rechercheInterventionEnCoursEmploye(f);
            System.out.println(interventionTrouille.getDescription());
            
            //rcherche d'intervention pour un employé qui n'en a pas (qui ne travaille pas en ce moment)
            Intervention interventionfantome = Service.rechercheInterventionEnCoursEmploye(nuit);
            try {
                System.out.println(interventionfantome.getDescription());
            } catch (NullPointerException ex) {
                System.out.println("Pas d'intervention pour cet employé");
            }
            //Fin de test rechercher intervention en cours employé
            
            //Test Recherche Intervention du Jour
            List<Intervention> interJour = Service.rechercheInterventionDuJour();
            System.out.println(interJour.size());
            for(int index = 0 ; index < interJour.size() ; index++) {
                System.out.println(interJour.get(index).getDescription());
            }
            //Fin test intervention du jour
            
            //Test Cloturer intervention
            System.out.println("cloture de l'intervention réussie ? " + Service.cloturerIntervention(k, false, "commentaire")); //Cloture de l'intervention k
            
            //On esssaie de cloturer une intervention qui n'a pas été acceptée (l)
            System.out.println("cloture de l'intervention réussie ? " + Service.cloturerIntervention(l, true, "cette intervention 'a jamais été acceptée"));
            //Fin test Cloturer intervention
            
            //Test historique client
            List<Intervention> histo = Service.recupererHistoriqueIntervention(c);
            System.out.println(histo.size());
            for(int index = 0 ; index < histo.size() ; index++) {
                System.out.println(histo.get(index).getDescription());
            }
            //Fin test historique
        

        JpaUtil.destroy();

    }
}
