package org.enicar.gestionagencevoyages.Model.Reservations;

import java.util.HashMap;import org.enicar.gestionagencevoyages.Model.Services.*;
import org.enicar.gestionagencevoyages.Model.Personnes.Date;
import javafx.beans.property.*;


public sealed class Reservation permits ReservationConfirmee, ReservationAnnulee {
    private final IntegerProperty idReservation;
    private final IntegerProperty clientId;
    private final ObjectProperty<Date> dateRes;
    private final ObjectProperty<HashMap<Integer, ServiceVoyage>> servInclus;
    private final StringProperty statut;

    public Reservation(){
        this.idReservation = new SimpleIntegerProperty(0);
        this.clientId = new SimpleIntegerProperty(0);
        this.dateRes = new SimpleObjectProperty<>();
        this.servInclus = new SimpleObjectProperty<>(new HashMap<>());
        this.statut = new SimpleStringProperty("En Cours");
    }
    public Reservation(int idReservation,int clientId, Date dateRes, String statut) {
        this.idReservation = new SimpleIntegerProperty(idReservation);
        this.clientId = new SimpleIntegerProperty(clientId);
        this.dateRes = new SimpleObjectProperty<>(dateRes);
        this.servInclus = new SimpleObjectProperty<>(new HashMap<>());
        this.statut = new SimpleStringProperty(statut);
    }

    public IntegerProperty idReservationProperty() {
        return idReservation;
    }

    public IntegerProperty clientIdProperty() {
        return clientId;
    }


    public ObjectProperty<Date> dateResProperty() {
        return dateRes;
    }

    public ObjectProperty<HashMap<Integer, ServiceVoyage>> servInclusProperty() {
        return servInclus;
    }
    public StringProperty statutProperty() {
        return statut;
    }
    public void setIdReservation(Integer idReservation) {
        this.idReservation.set(idReservation);
    }

    public void setClientId(int clientId) {
        this.clientId.set(clientId);}

    public void setStatut(String statut) { this.statut.set(statut); }

    public StringProperty dateAfficheeProperty() {
        return new SimpleStringProperty(dateRes.get() != null ? dateRes.get().toString() : "");
    }

    public String getStatut() {
        return statut.get();
    }
    public int getIdReservation() {
        return idReservation.get();
    }
    public Date getDateRes() {
        return dateRes.get();
    }

    public int getClientId() {
        return clientId.get();
    }

    /*public int getIdReservation() {
        return idReservation;
    }*/
    /*public Date getDateRes() {
        return dateRes;
    }*/
    /*public HashMap<Integer, ServiceVoyage> getServInclus() {
        return servInclus;
    }*/

    public void ajouterServInclus(Integer i, ServiceVoyage serviceVoyage) {
        servInclus.get().put(i, serviceVoyage);
    }
    public void supprimerServInclus(Integer i) {
        servInclus.get().remove(i);
    }
    public void chercherServInclus(Integer i) {
        servInclus.get().get(i);
    }

    /*public void ecrire(Scanner sc){
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
    }*/
}

