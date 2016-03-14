/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ali_Nauta.Database;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {

//    private boolean debug;
//    private Connection connection;
//    private String databaseAddress;
//
//    public Database(String address) throws Exception {
//        connection = DriverManager.getConnection(address);
//        databaseAddress = address;
//    }
//
//    public Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(databaseAddress);
//    }
//
//    public void setDebugMode(boolean d) {
//        debug = d;
//    }

    private String databaseAddress;

    public Database(String databaseAddress) throws Exception {
        this.databaseAddress = databaseAddress;

        init();
    }

    private void init() {
        List<String> lauseet = null;
        if (this.databaseAddress.contains("postgres")) {
            lauseet = postgreLauseet();
        }
//        } else {
//            lauseet = sqliteLauseet();
//        }

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        if (this.databaseAddress.contains("postgres")) {
            try {
                URI dbUri = new URI(databaseAddress);

                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

                return DriverManager.getConnection(dbUrl, username, password);
            } catch (Throwable t) {
                System.out.println("Error: " + t.getMessage());
                t.printStackTrace();
            }
        }

        return DriverManager.getConnection(databaseAddress);
    }

    private List<String> postgreLauseet() {
        ArrayList<String> lista = new ArrayList<>();


        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("DROP TABLE Alue;");
        lista.add("DROP TABLE ViestiKetju");
        lista.add("DROP TABLE Viesti");
        // heroku käyttää SERIAL-avainsanaa uuden tunnuksen automaattiseen luomiseen
        lista.add("CREATE TABLE Alue (id SERIAL PRIMARY KEY, otsikko varchar(50) NOT NULL)");

        lista.add("CREATE TABLE Viestiketju (\n" +
                "id SERIAL PRIMARY KEY, \n" +
                "alue_id integer NOT NULL, \n" +
                "otsikko varchar(50) NOT NULL, \n" +
                "FOREIGN KEY (alue_id) REFERENCES Alue(id))");

        lista.add("CREATE TABLE Viesti (\n" +
                "id SERIAL PRIMARY KEY, \n" +
                "ketju_id integer NOT NULL, \n" +
                "viesti text NOT NULL, \n" +
                "aika DATETIME DEFAULT(DATETIME('now', 'localtime')),\n" +
                "nimim varchar(20) NOT NULL, \n" +
                "FOREIGN KEY (ketju_id) REFERENCES Viestiketju(id))");

        return lista;
    }
}
