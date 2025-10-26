package org.enicar.gestionagencevoyages.Model.Personnes;

import java.util.Scanner;

/**
 *
 * @author Ons
 */
public abstract class Personne {
    protected int id;
    protected String nom;
    protected String prenom;
    protected Coordonnes coord;

    public Personne(){
        this.id = 0;
        this.nom = "";
        this.prenom = "";

    }
    public Personne (int id, String nom, String prenom ,Coordonnes coord)
    {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.coord = coord;
    }

    public void setId(int id) {this.id = id;}
    public void setNom(String nom) {this.nom = nom;}
    public void setPrenom(String prenom) {this.prenom = prenom;}


    public int getId() {return this.id;}
    public String getNom(){return this.nom;}
    public String getPrenom() {return this.prenom;}
    public Coordonnes getCoord(){return coord;}

    public void ecrire(Scanner sc){
        System.out.println("Donner l'id: ");
        id = sc.nextInt();
        System.out.println("Donner le nom: ");
        nom = sc.nextLine();
        System.out.println("Donner le prenom: ");
        prenom = sc.nextLine();
        System.out.println("Donner l'email: ");
        String email = sc.nextLine();
        System.out.println("Donner le numero de telephone: ");
        int telephone = sc.nextInt();
        coord = new Coordonnes(email,telephone);
    }

    public void afficher(){
        System.out.println("Id: " +  id);
        System.out.println("Nom: " + nom);
        System.out.println("Prenom: " + prenom);
        System.out.println("Coordonnes: " + coord.toString());
    }
}
