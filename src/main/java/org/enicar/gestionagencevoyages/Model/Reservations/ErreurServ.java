package org.enicar.gestionagencevoyages.Model.Reservations;

public class ErreurServ extends Exception {
    public ErreurServ(String message) {
        super("Le service voyage que vous voulez supprimer n'existe pas");
    }
}
