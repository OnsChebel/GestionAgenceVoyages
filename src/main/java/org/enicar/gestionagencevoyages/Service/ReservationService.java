package org.enicar.gestionagencevoyages.Service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.enicar.gestionagencevoyages.DAO.ReservationDAO;
import org.enicar.gestionagencevoyages.DAO.ReservationDAOImpl;
import org.enicar.gestionagencevoyages.Model.Reservations.Reservation;

public class ReservationService {

    private final ReservationDAO reservationDAO = new ReservationDAOImpl();
    private final ObservableList<Reservation> reservations;

    public ReservationService() {
        this.reservations = FXCollections.observableArrayList(reservationDAO.getAllReservations());
    }

    public ObservableList<Reservation> getReservations() {
        return this.reservations;
    }

    public ObservableList<Reservation> getAllReservations() {
        return FXCollections.observableArrayList(reservationDAO.getAllReservations());
    }

    public void addReservation(Reservation reservation) {
        reservationDAO.addReservation(reservation);
        if (reservation.getIdReservation() > 0) {
            this.reservations.add(reservation);
        }
    }
}
