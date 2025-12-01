package org.enicar.gestionagencevoyages.Service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.enicar.gestionagencevoyages.DAO.AccomodationDAO;
import org.enicar.gestionagencevoyages.DAO.AccomodationDAOImpl;
import org.enicar.gestionagencevoyages.Model.Services.Accomodation;

import java.util.List;

public class AccomodationService {

    private final AccomodationDAO accomodationDAO = new AccomodationDAOImpl();

    public void addAccomodation(Accomodation accomodation, int reservationId) {
        accomodationDAO.addAccomodation(accomodation, reservationId);
    }

    public ObservableList<Accomodation> getAccomodationsForReservation(int reservationId) {
        List<Accomodation> list = accomodationDAO.getAccomodationsByReservation(reservationId);
        return FXCollections.observableArrayList(list);
    }
}