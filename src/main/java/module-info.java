module org.enicar.gestionagencevoyages {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires javafx.base;

    opens org.enicar.gestionagencevoyages to javafx.fxml;
    exports org.enicar.gestionagencevoyages;

    opens org.enicar.gestionagencevoyages.Model.Reservations to javafx.base;
    opens org.enicar.gestionagencevoyages.Model.Personnes to javafx.base;
}