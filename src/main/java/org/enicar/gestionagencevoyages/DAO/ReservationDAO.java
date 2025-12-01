package org.enicar.gestionagencevoyages.DAO;

import java.util.List;
import org.enicar.gestionagencevoyages.Model.Reservations.Reservation;

public interface ReservationDAO {
    List<Reservation> getAllReservations();
    void addReservation(Reservation reservation);
    void updateReservation(Reservation reservation);
    void deleteReservation(int id);
}