package org.enicar.gestionagencevoyages.Model.Services;

import javafx.beans.property.*;
import java.util.Scanner;
public abstract class ServiceVoyage {
    protected final IntegerProperty id;
    protected final DoubleProperty prixBase;


    public ServiceVoyage(){
        this.id = new SimpleIntegerProperty(0);
        this.prixBase = new SimpleDoubleProperty(0.0);
    }
    public ServiceVoyage(int id, double prixBase)
    {
        this.id = new SimpleIntegerProperty(id);
        this.prixBase = new SimpleDoubleProperty(prixBase);
    }

    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public IntegerProperty idProperty() { return id; }

    public double getPrixBase() { return prixBase.get(); }
    public void setPrixBase(double prix) { this.prixBase.set(prix); }
    public DoubleProperty prixBaseProperty() { return prixBase; }



    /*public void ecrire(Scanner sc)
    {
        System.out.println("Donner l'id: ");
        id = sc.nextInt();
        System.out.println("Donner le prix de base: ");
        prixBase = sc.nextDouble();
    }*/

    /*public void afficher()
    {
        System.out.println("L'identifiant: " + id);
        System.out.println("Prix de base: " + prixBase + " DT");
        System.out.println("Reserve? : " + statut );
    }

    public void calculerCoutTotal(double prix){};*/
}
