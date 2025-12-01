package org.enicar.gestionagencevoyages.Model.Services;

import javafx.beans.property.*;
import org.enicar.gestionagencevoyages.Model.Personnes.Date;

public class Activite extends ServiceVoyage {
    private final StringProperty intitule;
    private final ObjectProperty<Date> date;
    private final IntegerProperty horaire;
    private final IntegerProperty duree;

    public Activite() {
        super();
        this.intitule = new SimpleStringProperty("");
        this.date = new SimpleObjectProperty<>();
        this.horaire = new SimpleIntegerProperty(0);
        this.duree = new SimpleIntegerProperty(0);
    }

    public Activite(int id, double prixBase, String intitule, Date date, int horaire, int duree) {
        super(id, prixBase);
        this.intitule = new SimpleStringProperty(intitule);
        this.date = new SimpleObjectProperty<>(date);
        this.horaire = new SimpleIntegerProperty(horaire);
        this.duree = new SimpleIntegerProperty(duree);
    }


    public String getIntitule() { return intitule.get(); }
    public void setIntitule(String val) { this.intitule.set(val); }
    public StringProperty intituleProperty() { return intitule; }

    public Date getDate() { return date.get(); }
    public void setDate(Date val) { this.date.set(val); }
    public ObjectProperty<Date> dateProperty() { return date; }

    public int getHoraire() { return horaire.get(); }
    public void setHoraire(int val) { this.horaire.set(val); }
    public IntegerProperty horaireProperty() { return horaire; }

    public int getDuree() { return duree.get(); }
    public void setDuree(int val) { this.duree.set(val); }
    public IntegerProperty dureeProperty() { return duree; }
}