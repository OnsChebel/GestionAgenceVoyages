package org.enicar.gestionagencevoyages.Model.Reservations;

import java.util.HashMap;
import java.util.Scanner;
import org.enicar.gestionagencevoyages.Model.Services.*;
import org.enicar.gestionagencevoyages.Model.Personnes.Date;

public sealed class Reservation permits ReservationConfirmee, ReservationAnnulee {
    private int idReservation;
    private Date dateRes;
    private HashMap<Integer, ServiceVoyage> servInclus;

    public Reservation(){
        this.idReservation = 0;
        this.servInclus = new HashMap<>();
    }
    public Reservation(int idReservation, Date dateRes) {
        this.idReservation = idReservation;
        this.dateRes = dateRes;
        this.servInclus = new HashMap<>();
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }
    public int getIdReservation() {
        return idReservation;
    }
    public Date getDateRes() {
        return dateRes;
    }
    public HashMap<Integer, ServiceVoyage> getServInclus() {
        return servInclus;
    }

    public void ajouterServInclus(Integer i, ServiceVoyage serviceVoyage) {
        servInclus.put(i, serviceVoyage);
    }
    public void supprimerServInclus(Integer i) {
        servInclus.remove(i);
    }
    public void chercherServInclus(Integer i) {
        servInclus.get(i);
    }

    public void ecrire(Scanner sc){
        System.out.println("Donner l'identifiant de la reservation: ");
        idReservation = sc.nextInt();
        System.out.println("Donner le jour: ");
        int j = sc.nextInt();
        System.out.println("Donner le mois: ");
        int m = sc.nextInt();
        System.out.println("Donner l'annee': ");
        int a = sc.nextInt();
        dateRes = new Date(j,m,a);
        int i = 0;
        char choix;
        do{
            System.out.println("Veuillez choisir une option:");
            System.out.println("1.vol \t 2.accomodation \t 3.Activite \t 4.Transport");
            int option = sc.nextInt();
            switch(option){
                case 1:{
                    Vol v = new Vol();
                    v.ecrire(sc);
                    ajouterServInclus(i,v);
                    break;
                }
                case 2:{
                    Accomodation acc = new Accomodation();
                    acc.ecrire(sc);
                    ajouterServInclus(i,acc);
                    break;
                }
                case 3:{
                    Activite ac = new Activite();
                    ac.ecrire(sc);
                    ajouterServInclus(i,ac);
                    break;
                }
                case 4:{
                    Transport t = new Transport();
                    t.ecrire(sc);
                    ajouterServInclus(i,t);
                    break;
                }
                default:{
                    System.out.println("Veuillez choisir une option valide"); break;
                }
            }
            System.out.println("Voulez-vous ajouter un service voyage?");
            choix = sc.next().charAt(0);
        }while (choix == 'o' || choix == 'O');
    }

    public void afficher() {
        System.out.println("l'identifiant de la reservation: " + idReservation);
        System.out.println("la date de la reservation: " + dateRes.toString());
        System.out.println("les services reserves:");
        for(ServiceVoyage serviceVoyage : servInclus.values()) {
            serviceVoyage.afficher();
        }
    }
}

