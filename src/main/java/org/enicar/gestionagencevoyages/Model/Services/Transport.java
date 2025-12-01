package org.enicar.gestionagencevoyages.Model.Services;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Transport extends ServiceVoyage {
    private final StringProperty type;

    public Transport() {
        super();
        this.type = new SimpleStringProperty("");
    }

    public Transport(int id, double prixBase, String type) {
        super(id, prixBase);
        this.type = new SimpleStringProperty(type);
    }

    public String getType() { return type.get(); }
    public void setType(String type) { this.type.set(type); }
    public StringProperty typeProperty() { return type; }
}