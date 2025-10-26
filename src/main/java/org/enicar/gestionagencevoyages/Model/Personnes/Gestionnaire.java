package org.enicar.gestionagencevoyages.Model.Personnes;

import java.util.HashSet;
import java.util.Scanner;
import org.enicar.gestionagencevoyages.Model.Reservations.*;

public class Gestionnaire extends Personne implements CalculSalaire {
    private Date priseFonct;
    private String poste;
    private HashSet<Reservation> reservations;
    private HashSet<Client> clients;
    private double bonus;
    private double salairefixe;
    public Gestionnaire() {
        super();
        this.poste = "";
        this.reservations = new HashSet<>();
        this.clients = new HashSet<>();
        this.bonus = 0;
    }
    public Gestionnaire(int id, String nom, String prenom, Coordonnes coord, Date priseFonct, String poste,double bonus, double salairefixe) {
        super(id, nom, prenom, coord);
        this.priseFonct = priseFonct;
        this.poste = poste;
        this.bonus = bonus;
        this.salairefixe = salairefixe;
        this.reservations = new HashSet<>();
        this.clients = new HashSet<>();
    }


    public Date getPriseFonct() {
        return this.priseFonct;
    }
    public void setPoste(String poste) {
        this.poste = poste;
    }
    public String getPoste() {
        return this.poste;
    }
    public void setBonus() {
        this.bonus = clients.size() * 100;
    }
    public double getBonus() {
        return this.bonus;
    }
    public void setSalaire(double salairefixe) {
        this.salairefixe = salairefixe;
    }
    public double getSalairefixe() {
        return this.salairefixe;
    }

    public HashSet<Reservation> getReservations() {
        return this.reservations;
    }
    public HashSet<Client> getClients() {
        return this.clients;
    }

    public void ajouterReservation(Reservation resv) {
        this.reservations.add(resv);
    }
    public void supprimerReservation(Reservation resv) {
        this.reservations.remove(resv);
    }
    public boolean chercherReservation(Reservation resv) {
        return this.reservations.contains(resv);
    }

    public void ajouterClient(Client cl) {

        this.clients.add(cl);
    }
    public void supprimerClient(Client cl) {
        this.clients.remove(cl);
    }
    public boolean chercherClient(Client cl) {
        return this.clients.contains(cl);
    }

    @Override
    public double totalSalaire() {
        return salairefixe+bonus;
    }

    public void ecrire(Scanner sc) {
        super.ecrire(sc);
        System.out.println("Donner la date de prise de fonction:");
        System.out.print("jour: ");
        int j = sc.nextInt();
        System.out.print("mois: ");
        int m = sc.nextInt();
        System.out.print("annee: ");
        int a = sc.nextInt();
        priseFonct = new Date(j,m,a);
        System.out.println("Donner le poste:");
        poste = sc.nextLine();
        System.out.println("Donner les reservations:");
        char choix;
        do{
            Reservation res = new Reservation();
            res.ecrire(sc);
            ajouterReservation(res);
            System.out.println("Y a-t-il d'autres reservations? :");
            choix = sc.next().charAt(0);
        } while (choix == 'o' || choix == 'O');
        System.out.println("Donner les clients:");
        char choix2;
        do{
            Client cli = new Client();
            cli.ecrire(sc);
            ajouterClient(cli);
            System.out.println("Y a-t-il d'autres clients? :");
            choix2 = sc.next().charAt(0);
        } while (choix2 == 'o' || choix2 == 'O');
        System.out.println("Donner le salaire fixe:");
        salairefixe = sc.nextDouble();
        setBonus();
    }
    public void afficher() {
        super.afficher();
        System.out.println("Poste: " + poste);
        System.out.println("Date de prise de fonction: " + priseFonct.toString());
        System.out.println("Salaire: " + salairefixe);
        System.out.println("Bonus: " + bonus);
        System.out.println("Salaire total: " + totalSalaire());
        System.out.println("Clients:");
        for (Client cl : clients) {
            cl.afficher();
        }
        System.out.println("Reservations:");
        for (Reservation r : reservations) {
            r.afficher();
        }

    }

}