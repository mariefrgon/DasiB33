/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactifb3328.util;

import modele.Client;

/**
 *
 * @author ebosca
 */
public class Mail {
    public static void envoiMailErreur(Client c){
        System.out.println("##################### Mail d'Erreur d'inscription########################\r\n");
        System.out.println("Expéditeur : contact.proactif.if");
        System.out.println("Pour : " + c.getMail());
        System.out.println("Sujet : Bienvenue chez Proact'IF\r\n");
        System.out.println("Corps :");
        System.out.println("Bonjour " + c.getPrenom() + ",");
        System.out.println("Votre inscription au service Proact'If a malencontreusemet échoué... Merci de recommencer ultérieurement.");
    }
    public static void envoiMailValide(Client c){
        System.out.println("##################### Mail de Confirmation d'inscription########################\r\n");
        System.out.println("Expéditeur : contact.proactif.if");
        System.out.println("Pour : " + c.getMail());
        System.out.println("Sujet : Bienvenue chez Proact'IF\r\n");
        System.out.println("Corps :");
        System.out.println("Bonjour " + c.getPrenom() + ",");
        System.out.println("Nous vous confirmons votre inscription au service proact'If. Votre numéro de client est : " + c.getnClient());
    }
}
