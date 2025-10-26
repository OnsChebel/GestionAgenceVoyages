package org.enicar.gestionagencevoyages.Model.Services;

import java.util.Scanner;
public abstract class ServiceVoyage implements Reservable {
    protected int id;
    protected double prixBase;
    protected boolean statut;

    public ServiceVoyage(){
        super();
        this.id = 0;
        this.prixBase = 0;
        this.statut = false;
    }
    public ServiceVoyage(int id, double prixBase, boolean statut)
    {
        this.id = id;
        this.prixBase = prixBase;
        this.statut = statut;
    }

    public void setId(int id) {this.id = id;}
    public void setPrixBase(double prixBase) {this.prixBase = prixBase;}

    public int getId(){ return id;}
    public double getPrixBase(){ return prixBase;}
    public boolean getStatut() {return statut;}

    @Override
    public void reserver() {
        statut = true;
    }

    @Override
    public void annuler() {
        statut = false;
    }

    public void ecrire(Scanner sc)
    {
        System.out.println("Donner l'id: ");
        id = sc.nextInt();
        System.out.println("Donner le prix de base: ");
        prixBase = sc.nextDouble();
    }

    public void afficher()
    {
        System.out.println("L'identifiant: " + id);
        System.out.println("Prix de base: " + prixBase + " DT");
        System.out.println("Reserve? : " + statut );
    }

    public void calculerCoutTotal(double prix){};
}
