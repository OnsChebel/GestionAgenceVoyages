package org.enicar.gestionagencevoyages.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class databaseManager {

    private static final String URL = "jdbc:sqlite:agencevoyages.db";

    static {
        try {
            initializeDatabase();
        } catch (SQLException e) {
            System.err.println("CRITIQUE : Impossible d'initialiser la base de données.");
            e.printStackTrace();
        }
    }


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    private static void initializeDatabase() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            String sqlClients = """
                CREATE TABLE IF NOT EXISTS Client (
                    id INTEGER PRIMARY KEY AUTOINCREMENT, 
                    nom TEXT NOT NULL,
                    prenom TEXT NOT NULL,
                    email TEXT,
                    telephone INTEGER,
                    rue TEXT,
                    ville TEXT,
                    codePostal INTEGER
                );
            """;
            stmt.execute(sqlClients);

            String sqlReservations = """
                CREATE TABLE IF NOT EXISTS Reservation (
                    idReservation INTEGER PRIMARY KEY AUTOINCREMENT,
                    dateRes TEXT NOT NULL,
                    statut TEXT NOT NULL,
                    clientId INTEGER,
                    FOREIGN KEY (clientId) REFERENCES Client(id)
                );
            """;
            stmt.execute(sqlReservations);

            String sqlVol = """
    CREATE TABLE IF NOT EXISTS Vol (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        
        
        depart_nom TEXT,
        depart_code TEXT,
        
        arrivee_nom TEXT,
        arrivee_code TEXT,
        
        
        taxAeroport DOUBLE, 
        prixBase DOUBLE,    
        
        reservation_id INTEGER,
        FOREIGN KEY (reservation_id) REFERENCES Reservation(idReservation) ON DELETE CASCADE
    );
""";
            stmt.execute(sqlVol);

            String sqlEscale = """
    CREATE TABLE IF NOT EXISTS Escale (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        nom_aeroport TEXT,
        code_iata TEXT,
        vol_id INTEGER, -- Vérifiez que c'est bien vol_id
        FOREIGN KEY (vol_id) REFERENCES Vol(id) ON DELETE CASCADE
    );
""";
            stmt.execute(sqlEscale);

            String sqlAcc = """
    CREATE TABLE IF NOT EXISTS Accomodation (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        nom TEXT,
        type TEXT,
        adresse TEXT,
        prixBase DOUBLE,
        tarifsSupp DOUBLE,
        reservation_id INTEGER,
        FOREIGN KEY (reservation_id) REFERENCES Reservation(idReservation) ON DELETE CASCADE
    );
""";
            stmt.execute(sqlAcc);
            
            String sqlSupp = """
    CREATE TABLE IF NOT EXISTS Supplement (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        nom_service TEXT,
        accomodation_id INTEGER,
        FOREIGN KEY (accomodation_id) REFERENCES Accomodation(id) ON DELETE CASCADE
    );
""";
            stmt.execute(sqlSupp);

            String sqlActivite = """
    CREATE TABLE IF NOT EXISTS Activite (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        intitule TEXT,
        dateActivite TEXT,
        horaire INTEGER,
        duree INTEGER,
        prixBase DOUBLE,
        reservation_id INTEGER,
        FOREIGN KEY (reservation_id) REFERENCES Reservation(idReservation) ON DELETE CASCADE
    );
""";
            stmt.execute(sqlActivite);

            String sqlTransport = """
    CREATE TABLE IF NOT EXISTS Transport (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        type TEXT,
        prixBase DOUBLE,
        reservation_id INTEGER,
        FOREIGN KEY (reservation_id) REFERENCES Reservation(idReservation) ON DELETE CASCADE
    );
""";
            stmt.execute(sqlTransport);

            System.out.println("Base de données vérifiée et initialisée.");
        }
    }
}