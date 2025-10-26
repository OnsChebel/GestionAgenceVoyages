package org.enicar.gestionagencevoyages.Model.Reservations;

import org.enicar.gestionagencevoyages.Model.Personnes.Date;

import java.util.Scanner;

public final class ReservationConfirmee extends Reservation{
    private int codeConfirmation;

    public ReservationConfirmee(){
        super();
        this.codeConfirmation = 0;
    }
    public ReservationConfirmee(int idReservation, Date dateRes, int codeConfirmation) {
        super(idReservation, dateRes);
        this.codeConfirmation = codeConfirmation;
    }

    public int getCodeConfirmation() {
        return codeConfirmation;
    }

    @Override
    public void ecrire(Scanner sc){
        super.ecrire(sc);
        System.out.println("Donner le code confirmation: ");
        codeConfirmation = sc.nextInt();
    }

    @Override
    public void afficher() {
        super.afficher();
        System.out.println("Code confirmation: " + codeConfirmation);
    }
}
