package org.enicar.gestionagencevoyages.DAO;

import org.enicar.gestionagencevoyages.Model.Services.Vol;

import java.util.List;

public interface VolDAO {
    void addVol(Vol vol, int reservationId);
    List<Vol> getVolsByReservation(int reservationId);
    void deleteVol(int id);
}
