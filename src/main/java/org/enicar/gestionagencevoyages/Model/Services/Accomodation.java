package org.enicar.gestionagencevoyages.Model.Services;

import java.util.HashSet;
import java.util.Scanner;

public class Accomodation extends ServiceVoyage{
    double tarifsSupp;
    HashSet<String> servicesIncluts;

    public Accomodation(){
        super();
        this.tarifsSupp = 0.0;
        this.servicesIncluts = new HashSet<>();
    }
    public Accomodation(int id, double prixBase, boolean statut) {
        super(id, prixBase, statut);
        tarifsSupp = 0.0;
        servicesIncluts = new HashSet<>();
    }

    public void setTarifsSupp(){
        if (servicesIncluts.contains("Chambre individuelle") ) tarifsSupp =+ 100.0;
        if (servicesIncluts.contains("Pension complete")) tarifsSupp =+ 190.0;
        if (servicesIncluts.contains("Spa")) tarifsSupp =+ 225.0;
        if (servicesIncluts.contains("Baby sitter")) tarifsSupp =+ 300.0;
    }

    public double getTarifsSupp(){return tarifsSupp;}
    public HashSet<String> getServicesIncluts(){return servicesIncluts;}

    public void ajouterServicesInclut(String service){
        servicesIncluts.add(service);
    }
    public void supprimerServicesInclut(String service){
        servicesIncluts.remove(service);
    }
    public boolean chercherServicesInclut(String service){
        return servicesIncluts.contains(service);
    }

    @Override
    public void ecrire(Scanner sc) {
        super.ecrire(sc);
        System.out.println("Y-a-t-il des services supp? ");
        String rep = sc.nextLine();
        if (rep.equals("oui")) {
            char choix;
            do {
                System.out.println("Donner le service:");
                String service = sc.nextLine();
                ajouterServicesInclut(service);
                System.out.println("Y-a-t-il un autre service?");
                choix = sc.next().charAt(0);
            } while (choix == 'o' || choix == 'O');
        }
    }

    @Override
    public void afficher() {
        super.afficher();
        System.out.println("Tarifs Supp: " + tarifsSupp);
        System.out.println("ServicesIncluts: ");
        for (String service : servicesIncluts) {System.out.println(service);}
    }

    @Override
    public void calculerCoutTotal(double prix) {
        prix = super.getPrixBase() + tarifsSupp;
    }
}
