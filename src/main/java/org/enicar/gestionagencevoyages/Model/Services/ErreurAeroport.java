package org.enicar.gestionagencevoyages.Model.Services;

public class ErreurAeroport extends Exception {
    public ErreurAeroport(String message) {
        super("L'aeroport que vous voulez supprimer ne fait pas partie des escales de ce voyage");
    }
}
