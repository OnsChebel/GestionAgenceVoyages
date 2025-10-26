package org.enicar.gestionagencevoyages.Model.Personnes;

import java.util.ArrayList;
import java.util.Scanner;
import org.enicar.gestionagencevoyages.Model.Reservations.*;

public sealed class Client extends Personne permits ClientPremium {
    private Adresse adresse;
    private ArrayList<Reservation> hisResv;

    public Client(){
        super();
        this.hisResv = new ArrayList<>();
    }
    public Client(int id, String nom, String prenom, Adresse adresse) {
        super(id, nom, prenom, null);
        this.adresse = adresse;
        this.hisResv = new ArrayList<>();
    }

    public Adresse getAdresse() {
        return this.adresse;
    }
    public ArrayList<Reservation> getHisResv() {return this.hisResv;}

    public void ajouteHisResv(Reservation reservation) {
        this.hisResv.add(reservation);
    }
    public void supprimerHisResv(int rang) {
        this.hisResv.remove(rang);
    }
    public Reservation getReservation(int rang) {
        return hisResv.get(rang);
    }

    public void afficherhisResv() {
        for (Reservation resv : hisResv) {
            resv.afficher();
        }
    }

    @Override
    public void ecrire(Scanner sc){
        super.ecrire(sc);
        System.out.println("Donner la rue ");
        String rue = sc.nextLine();
        System.out.println("Donner la ville ");
        String ville = sc.nextLine();
        System.out.println("Donner la codePostal ");
        int codePostal = sc.nextInt();
        adresse = new Adresse(rue, ville, codePostal);
        char choix;
        do{
            Reservation res = null;
            res.ecrire(sc);
            hisResv.add(res);
            System.out.println("Voulez-vous ajouter une reservation?");
            choix = sc.next().charAt(0);
        } while (choix == 'o' || choix == 'O');
    }

    @Override
    public void afficher() {
        super.afficher();
        System.out.println("Adresse : " + adresse.toString());
        for(Reservation resv : hisResv) {
            resv.afficher();
        }
    }

}

