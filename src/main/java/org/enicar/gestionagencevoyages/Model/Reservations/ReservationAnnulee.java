package org.enicar.gestionagencevoyages.Model.Reservations;

import org.enicar.gestionagencevoyages.Model.Personnes.Date;

import java.util.Scanner;

public final class ReservationAnnulee extends Reservation{
    String raison;
    public ReservationAnnulee(){
        super();
        this.raison = "";
    }
    public ReservationAnnulee(int idReservation, Date dateRes, String raison) {
        super(idReservation, dateRes);
        this.raison = raison;
    }

    public String getRaison() {
        return raison;
    }

    @Override
    public void ecrire(Scanner sc){
        super.ecrire(sc);
        System.out.println("Donner la raison de l'annulation");
        raison = sc.nextLine();
    }

    @Override
    public void afficher() {
        super.afficher();
        System.out.println("Raison de l'annulation" + raison);
    }
}
