/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactifb3328.util;

import java.util.Date;
import modele.Intervention;

/**
 *
 * @author Etienne
 */
public class PhoneNotificator {
    
    public static void notificationEmploye(Intervention i){
        
        System.out.println("#####################SMS de Notification d'affectation Employe########################");
        System.out.println("Intervention " + i.getType() + " demandée le " + new Date() +  " pour " + i.getClient().getPrenom() + " " + i.getClient().getNom()
        + ", " + i.getClient().getAdresse() + " " + i.getDescription());
        
    }
    
}
