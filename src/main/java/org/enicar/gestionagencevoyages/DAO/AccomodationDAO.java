package org.enicar.gestionagencevoyages.DAO;

import org.enicar.gestionagencevoyages.Model.Services.Accomodation;

import java.util.List;

public interface AccomodationDAO {
    void addAccomodation(Accomodation acc, int reservationId);
    List<Accomodation> getAccomodationsByReservation(int reservationId);
    void deleteAccomodation(int accomodationId);
}
