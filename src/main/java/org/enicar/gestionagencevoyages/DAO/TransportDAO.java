package org.enicar.gestionagencevoyages.DAO;

import org.enicar.gestionagencevoyages.Model.Services.Transport;
import java.util.List;

public interface TransportDAO {
    void addTransport(Transport t, int reservationId);
    List<Transport> getTransportsByReservation(int reservationId);
    void deleteTransport(int transportId);
}
