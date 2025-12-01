package org.enicar.gestionagencevoyages.Model.Personnes;

import javafx.beans.property.*;
import java.util.Scanner;


public abstract class Personne {
    protected final IntegerProperty id;
    protected final StringProperty nom;
    protected final StringProperty prenom;
    protected final ObjectProperty<Coordonnes> coord;

    public Personne(){
        this.id = new SimpleIntegerProperty(0);
        this.nom = new SimpleStringProperty("");
        this.prenom = new SimpleStringProperty("");
        this.coord = new SimpleObjectProperty<>();

    }
    public Personne (int id, String nom, String prenom ,Coordonnes coord)
    {
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.coord = new SimpleObjectProperty<>(coord);
    }

    public IntegerProperty idProperty() {return this.id;}
    public StringProperty nomProperty() {return this.nom;}
    public StringProperty prenomProperty() {return this.prenom;}
    public ObjectProperty<Coordonnes> coordProperty() {return this.coord;}


    public void setId(int id) {this.id.set(id);}
    public void setNom(String nom) {this.nom.set(nom);}
    public void setPrenom(String prenom) {this.prenom.set(prenom);}


    public int getId() {return this.id.get();}
    public String getNom(){return this.nom.get();}
    public String getPrenom() {return this.prenom.get();}
    public Coordonnes getCoord(){return coord.get();}

    /*public void ecrire(Scanner sc){
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
    }*/

    /*public void afficher(){
        System.out.println("Id: " +  id);
        System.out.println("Nom: " + nom);
        System.out.println("Prenom: " + prenom);
        System.out.println("Coordonnes: " + coord.toString());
    }*/
}
