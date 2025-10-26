package org.enicar.gestionagencevoyages.Model.Personnes;

public class ErreurReservation extends Exception {
    public ErreurReservation(String message) {
        super("La reservation que vous voulez supprimer n'existe pas");
    }
}
