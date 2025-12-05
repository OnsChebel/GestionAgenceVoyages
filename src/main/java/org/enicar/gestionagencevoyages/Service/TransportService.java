package org.enicar.gestionagencevoyages.Service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.enicar.gestionagencevoyages.DAO.TransportDAOImpl;
import org.enicar.gestionagencevoyages.Model.Services.Transport;

public class TransportService {
    private final TransportDAOImpl dao = new TransportDAOImpl();

    public void addTransport(Transport t, int resId) { dao.addTransport(t, resId); }

    public ObservableList<Transport> getTransports(int resId) {
        return FXCollections.observableArrayList(dao.getTransportsByReservation(resId));
    }

    public void deleteTransport(int transportId) { dao.deleteTransport(transportId); }
}
