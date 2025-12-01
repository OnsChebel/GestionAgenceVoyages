package org.enicar.gestionagencevoyages.Model.Personnes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Scanner;

public final class ClientPremium extends Client {
    private final IntegerProperty pointsFidelite;

    public ClientPremium() {
        super();this.pointsFidelite = new SimpleIntegerProperty(0);
    }
    public ClientPremium(int id, String nom, String prenom,Coordonnes coord, Adresse adresse, int pointsFidelite) {
        super(id, nom, prenom,coord, adresse);
        this.pointsFidelite = new SimpleIntegerProperty(pointsFidelite);
    }


    public IntegerProperty pointsFideliteProperty() {
        return pointsFidelite;
    }


    public int getPointsFidelite() {
        return pointsFidelite.get();
    }
    public void setPointsFidelite(int pointsFidelite) {
        this.pointsFidelite.set(pointsFidelite);
    }

    /*@Override
    public void ecrire(Scanner sc){
        super.ecrire(sc);
        System.out.println("Donner la valeur des points fidelite: ");
        pointsFidelite = sc.nextInt();
    }

    @Override
    public void afficher() {
        super.afficher();
        System.out.println("Points fidelite: " + pointsFidelite);
    }*/
}
