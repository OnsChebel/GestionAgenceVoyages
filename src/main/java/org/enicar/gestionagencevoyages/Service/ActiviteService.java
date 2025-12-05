package org.enicar.gestionagencevoyages.Service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.enicar.gestionagencevoyages.DAO.ActiviteDAOImpl;
import org.enicar.gestionagencevoyages.Model.Services.Activite;

import java.util.List;

public class ActiviteService {
    private final ActiviteDAOImpl activiteDAO = new ActiviteDAOImpl();

    public void addActivite(Activite activite, int reservationId) {
        activiteDAO.addActivite(activite, reservationId);
    }

    public ObservableList<Activite> getActivitesForReservation(int reservationId) {
        List<Activite> list = activiteDAO.getActivitesByReservation(reservationId);
        return FXCollections.observableArrayList(list);
    }

    public void deleteActivite(int activiteId) {
        activiteDAO.deleteActivite(activiteId);
    }
}
