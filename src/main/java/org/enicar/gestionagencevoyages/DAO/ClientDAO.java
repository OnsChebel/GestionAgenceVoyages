package org.enicar.gestionagencevoyages.DAO;

import java.util.List;
import org.enicar.gestionagencevoyages.Model.Personnes.Client;

public interface ClientDAO {
    List<Client> getAllClients();
    Client getClientById(int id);
    void addClient(Client client);
    void updateClient(Client client);
    void deleteClient(int id);
}
