package org.enicar.gestionagencevoyages.Model.Services;

import java.util.ArrayList;
import java.util.Scanner;

public class Vol extends ServiceVoyage{
    private Aeroport aDepart;
    private Aeroport aArrivee;
    private double taxAeroport;
    private ArrayList<Aeroport> escales;

    public Vol(int id, double prixBase, boolean statut, Aeroport aDepart, Aeroport aArrivee, double taxAeroport) {
        super(id, prixBase, statut);
        this.aDepart = aDepart;
        this.aArrivee = aArrivee;
        this.taxAeroport = taxAeroport;
        escales = new ArrayList<>();
    }

    public void setADepart(Aeroport aDepart) {this.aDepart = aDepart;}
    public void setaArrivee(Aeroport aArrivee) {this.aArrivee = aArrivee;}
    public void setTaxAeroport(double taxAeroport) {this.taxAeroport = taxAeroport;}

    public Aeroport getADepart() {return this.aDepart;}
    public Aeroport getaArrivee() {return this.aArrivee;}
    public double getTaxAeroport() {return this.taxAeroport;}
    public ArrayList<Aeroport> getEscales() {return this.escales;}

    public void ajouterEscale(Aeroport aEscale) {
        escales.add(aEscale);
    }
    public void retirerEscale(int rang) {
        escales.remove(rang);
    }
    public boolean chercherEscale(Aeroport aEscale) {
        return escales.contains(aEscale);
    }

    @Override
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
    }

    @Override
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
    }
}
