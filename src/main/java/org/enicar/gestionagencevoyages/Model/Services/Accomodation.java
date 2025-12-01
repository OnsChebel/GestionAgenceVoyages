package org.enicar.gestionagencevoyages.Model.Services;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Accomodation extends ServiceVoyage {

    private final StringProperty nom;
    private final StringProperty type;
    private final StringProperty adresse;
    private final DoubleProperty tarifsSupp;

    private final ObservableList<String> servicesIncluts;

    public Accomodation() {
        super();
        this.nom = new SimpleStringProperty("");
        this.type = new SimpleStringProperty("");
        this.adresse = new SimpleStringProperty("");
        this.tarifsSupp = new SimpleDoubleProperty(0.0);
        this.servicesIncluts = FXCollections.observableArrayList();
    }

    public Accomodation(int id, double prixBase, String nom, String type, String adresse) {
        super(id, prixBase);
        this.nom = new SimpleStringProperty(nom);
        this.type = new SimpleStringProperty(type);
        this.adresse = new SimpleStringProperty(adresse);
        this.tarifsSupp = new SimpleDoubleProperty(0.0);
        this.servicesIncluts = FXCollections.observableArrayList();
    }

    public void recalculerTarifsSupp() {
        double totalSupp = 0.0;
        for (String service : servicesIncluts) {
            switch (service) {
                case "Chambre individuelle" -> totalSupp += 100.0;
                case "Pension complete" -> totalSupp += 190.0;
                case "Spa" -> totalSupp += 225.0;
                case "Baby sitter" -> totalSupp += 300.0;
            }
        }
        this.tarifsSupp.set(totalSupp);
    }


    public String getNom() { return nom.get(); }
    public void setNom(String val) { this.nom.set(val); }
    public StringProperty nomProperty() { return nom; }

    public String getType() { return type.get(); }
    public void setType(String val) { this.type.set(val); }
    public StringProperty typeProperty() { return type; }

    public String getAdresse() { return adresse.get(); }
    public void setAdresse(String val) { this.adresse.set(val); }
    public StringProperty adresseProperty() { return adresse; }

    public double getTarifsSupp() { return tarifsSupp.get(); }
    public void setTarifsSupp(double val) { this.tarifsSupp.set(val); }
    public DoubleProperty tarifsSuppProperty() { return tarifsSupp; }

    public ObservableList<String> getServicesIncluts() { return servicesIncluts; }
    public void setServicesIncluts(List<String> list) { this.servicesIncluts.setAll(list); }
}