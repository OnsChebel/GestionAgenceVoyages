package org.enicar.gestionagencevoyages.Model.Services;

import org.enicar.gestionagencevoyages.Model.Personnes.Date;
import java.util.Scanner;
/**
 *
 * @author Ons
 */
public class Activite extends ServiceVoyage {
    String intitule;
    Date date;
    int horaire;
    int duree;

    public Activite() {
        super();
        this.intitule = "";
        this.horaire = 0;
        this.duree = 0;
    }
    public Activite (int id, double prixBase, boolean statut, String intitule, Date date, int horaire, int duree)
    {
        super(id, prixBase, statut);
        this.intitule = intitule;
        this.date = date;
        this.horaire = horaire;
        this.duree = duree;
    }

    public void setIntitule( String intitule) {this.intitule = intitule;}
    public void setDate( Date date) {this.date = date;}
    public void setHoraire( int horaire) {this.horaire = horaire;}
    public void setDuree( int duree) {this.duree = duree;}

    public String getIntitule(){return this.intitule;}
    public Date getDate(){return this.date;}
    public int getHoraire() {return this.horaire;}
    public int getDuree() {return this.duree;}

    @Override
    public void ecrire(Scanner sc)
    {
        super.ecrire(sc);
        System.out.println("Donner l'intitule: ");
        intitule = sc.nextLine();
        System.out.println("Donner le jour: ");
        int j = sc.nextInt();
        System.out.println("Donner le mois: ");
        int m = sc.nextInt();
        System.out.println("Donner l'annee: ");
        int a = sc.nextInt();
        date = new Date(j, m, a);
        System.out.println("Donner l'horaire: ");
        horaire = sc.nextInt();
        System.out.println("Donner la duree: ");
        duree = sc.nextInt();
    }

    @Override
    public void afficher()
    {
        super.afficher();
        System.out.println("Intitule: " + intitule);
        System.out.println("Date: " + date.toString());
        System.out.println("Horaire: " + horaire + " h");
        System.out.println("Duree: " + duree);
    }

}

