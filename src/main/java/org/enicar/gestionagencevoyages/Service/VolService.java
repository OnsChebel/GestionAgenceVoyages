package org.enicar.gestionagencevoyages.Service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.enicar.gestionagencevoyages.DAO.VolDAO;
import org.enicar.gestionagencevoyages.DAO.VolDAOImpl;
import org.enicar.gestionagencevoyages.Model.Services.Vol;

public class VolService {

    private final VolDAO volDAO = new VolDAOImpl();

    public ObservableList<Vol> getVolsForReservation(int reservationId) {
        return FXCollections.observableArrayList(volDAO.getVolsByReservation(reservationId));
    }

    public void addVol(Vol vol, int reservationId) {
        volDAO.addVol(vol, reservationId);
    }

    public void deleteVol(Vol vol) {
        volDAO.deleteVol(vol.getId());
    }
}