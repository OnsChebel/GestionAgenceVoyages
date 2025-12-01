package org.enicar.gestionagencevoyages.Service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.enicar.gestionagencevoyages.DAO.ClientDAO;
import org.enicar.gestionagencevoyages.DAO.ClientDAOImpl;
import org.enicar.gestionagencevoyages.Model.Personnes.Client;

public class ClientService {

    private final ClientDAO clientDAO = new ClientDAOImpl();
    private final ObservableList<Client> clients;

    public ClientService() {
        this.clients = FXCollections.observableArrayList(clientDAO.getAllClients());
    }

    public ObservableList<Client> getClients() {
        return this.clients;
    }

    public void addClient(Client client) {
        clientDAO.addClient(client);
        if (client.getId() > 0) {
            this.clients.add(client);
        }
    }

    public Client getClientById(int id) {
        return clients.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
