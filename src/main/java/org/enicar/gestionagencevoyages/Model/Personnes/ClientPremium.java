package org.enicar.gestionagencevoyages.Model.Personnes;

import java.util.Scanner;

public final class ClientPremium extends Client {
    int pointsFidelite;

    public ClientPremium() {
        super();
        this.pointsFidelite = 0;
    }
    public ClientPremium(int id, String nom, String prenom, Adresse adresse, int pointsFidelite) {
        super(id, nom, prenom, adresse);
        this.pointsFidelite = pointsFidelite;
    }

    public int getPointsFidelite() {
        return pointsFidelite;
    }

    @Override
    public void ecrire(Scanner sc){
        super.ecrire(sc);
        System.out.println("Donner la valeur des points fidelite: ");
        pointsFidelite = sc.nextInt();
    }

    @Override
    public void afficher() {
        super.afficher();
        System.out.println("Points fidelite: " + pointsFidelite);
    }
}
