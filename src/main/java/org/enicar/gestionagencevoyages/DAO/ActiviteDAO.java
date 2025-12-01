package org.enicar.gestionagencevoyages.DAO;

import org.enicar.gestionagencevoyages.Model.Services.Activite;

import java.util.List;

public interface ActiviteDAO {
    void addActivite(Activite a, int reservationId);
    List<Activite> getActivitesByReservation(int reservationId);
}
