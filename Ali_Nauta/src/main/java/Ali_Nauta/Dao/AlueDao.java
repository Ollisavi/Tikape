package Ali_Nauta.Dao;

import Ali_Nauta.Database.Database;
import Ali_Nauta.Domain.Alue;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jan on 3.3.2016.
 */
public class AlueDao implements Dao<Alue, Integer> {

    private Database database;

    public AlueDao(Database database) {
        this.database = database;
    }

    @Override
    public Alue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM alue WHERE alue.id = ?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();

        Alue alue = new Alue(rs.getInt("id"), rs.getString("otsikko"));

        rs.close();
        stmt.close();
        connection.close();
        return alue;
    }

    @Override
    public List<Alue> findAllin(Integer key) throws SQLException {
        return null;
    }

    @Override
    public List<Alue> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM alue");
        ResultSet rs = stmt.executeQuery();

        List<Alue> alueet = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String otsikko = rs.getString("otsikko");
            int viestinMaara = viestitAlueella(id);
            String viimeisin = viimViestinAika(id);
            Alue a = new Alue(id, otsikko);
            a.setViestienMaara(viestinMaara);
            a.setViimViestinAika(viimeisin);
            alueet.add(a);
        }

        rs.close();
        stmt.close();
        connection.close();
        return alueet;
    }

    public int viestitAlueella(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(viesti.id) AS viestit FROM viesti,alue,viestiketju " +
                "WHERE viesti.ketju_id = viestiketju.id AND viestiketju.alue_id = alue.id AND alue.id = ?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();

        int maara = rs.getInt("viestit");

        rs.close();
        stmt.close();
        connection.close();
        return maara;
    }

    public void lisaaAlue(String otsikko) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO alue (otsikko) VALUES (?)");
        stmt.setString(1, otsikko);

        stmt.executeUpdate();

        stmt.close();
        connection.close();
    }

    public String viimViestinAika(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT viesti.aika AS viimeisin FROM viesti, viestiketju, alue " +
                "WHERE viesti.ketju_id = viestiketju.id AND viestiketju.alue_id = alue.id AND alue.id = ?" +
                "ORDER BY viesti.aika DESC LIMIT 1");
        stmt.setInt(1, key);

        String viimeisin;
        try {
            ResultSet rs = stmt.executeQuery();
            viimeisin = rs.getString(1);
            rs.close();
        } catch (Exception e) {
            viimeisin = "ei viestejä";
        }

        stmt.close();
        connection.close();
        return viimeisin;
    }

    @Override
    public void delete(Integer key) throws SQLException {

    }
}
