package org.enicar.gestionagencevoyages.Model.Personnes;

public class ErreurClient extends Exception {
    public ErreurClient(String message) {
        super("Le client que vous essayez de supprimer n'existe pas");
    }
}
