package org.enicar.gestionagencevoyages.Model.Services;

import javafx.beans.property.*;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;
import java.util.Scanner;

public class Vol extends ServiceVoyage{
    private final ObjectProperty<Aeroport> aDepart;
    private final ObjectProperty<Aeroport> aArrivee;
    private final DoubleProperty taxAeroport;
    private final ObservableList<Aeroport> escales;

    public Vol(){
        super();
        this.aDepart = new SimpleObjectProperty<>();
        this.aArrivee = new SimpleObjectProperty<>();
        this.taxAeroport = new SimpleDoubleProperty(0.0);
        this.escales = FXCollections.observableArrayList();
    }
    public Vol(int id, double prixBase, Aeroport aDepart, Aeroport aArrivee, double taxAeroport) {
        super(id, prixBase);
        this.aDepart = new SimpleObjectProperty<>(aDepart);
        this.aArrivee = new SimpleObjectProperty<>(aArrivee);
        this.taxAeroport = new SimpleDoubleProperty(taxAeroport);
        this.escales = FXCollections.observableArrayList();
    }

    public Aeroport getADepart() { return aDepart.get(); }
    public void setADepart(Aeroport val) { this.aDepart.set(val); }
    public ObjectProperty<Aeroport> aDepartProperty() { return aDepart; }
    public ObservableList<Aeroport> getEscales() {
        return escales;
    }


    public void setEscales(List<Aeroport> nouvellesEscales) {
        this.escales.setAll(nouvellesEscales);
    }

    public Aeroport getAArrivee() { return aArrivee.get(); }
    public void setAArrivee(Aeroport val) { this.aArrivee.set(val); }
    public ObjectProperty<Aeroport> aArriveeProperty() { return aArrivee; }

    public double getTaxAeroport() { return taxAeroport.get(); }
    public void setTaxAeroport(double val) { this.taxAeroport.set(val); }
    public DoubleProperty taxAeroportProperty() { return taxAeroport; }

    public void ajouterEscale(Aeroport aEscale) {
        escales.add(aEscale);
    }

    public void retirerEscale(Aeroport aEscale) {
        escales.remove(aEscale);
    }


    public boolean chercherEscale(Aeroport aEscale) {
        return escales.contains(aEscale);
    }

    /*@Override
    public void ecrire (Scanner sc){
        super.ecrire(sc);
        System.out.println("Donner le nom de l'aeroport de depart : ");
        String nomaerod = sc.nextLine();
        System.out.println("Donner son code IATA : ");
        String codeIATA1 = sc.nextLine();
        aDepart = new Aeroport(nomaerod, codeIATA1);
        System.out.println("Donner le nom de l'aeroport d'arrive' : ");
        String nomaeroa = sc.nextLine();
        System.out.println("Donner son code IATA : ");
        String codeIATA2 = sc.nextLine();
        aArrivee = new Aeroport(nomaeroa, codeIATA2);
        System.out.println("Donner les taxes des aeroports :");
        taxAeroport = sc.nextDouble();
    }*/

    /*@Override
    public void afficher() {
        super.afficher();
        System.out.println("Aeroport de depart:" + aDepart.toString());
        System.out.println("Aeroport de arrivee:" + aArrivee.toString());
        System.out.println("Les taxes des aeroports :" + taxAeroport);
        System.out.println("Les escales :");
        if(escales.size() == 0) System.out.println("Pas d'escales");
        else {
            for (Aeroport a : escales) {System.out.println(a.toString());}
        }
    }

    @Override
    public void calculerCoutTotal(double prix) {
        prix = super.getPrixBase() + taxAeroport;
    }*/
}
