package org.enicar.gestionagencevoyages.Model.Services;

import java.util.Scanner;

/**
 * @author Ons
 */
public class Transport extends ServiceVoyage {
    String type;

    public Transport(int id, double prixBase, boolean statut, String type) {
        super(id, prixBase, statut);
        this.type = type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public void ecrire(Scanner sc) {
        super.ecrire(sc);
        System.out.println("Donner le type: ");
        type = sc.nextLine();
    }

    @Override
    public void afficher() {
        super.afficher();
        System.out.println("Type : " + type);
    }
}
