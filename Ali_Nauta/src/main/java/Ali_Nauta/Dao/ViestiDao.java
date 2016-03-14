package Ali_Nauta.Dao;

import Ali_Nauta.Database.Database;
import Ali_Nauta.Domain.Viesti;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jan on 3.3.2016.
 */
public class ViestiDao implements Dao<Viesti, Integer> {

    private Database database;

    public ViestiDao(Database database) {
        this.database = database;
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
//        Connection connection = database.getConnection();
//        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti")
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Viesti> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Viesti> findAllin(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM viesti WHERE viesti.ketju_id = ? ORDER BY id ASC");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();

        List<Viesti> viestitKetjussa = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            int ketjuId = rs.getInt("ketju_id");
            String viesti = rs.getString("viesti");
            String aika = rs.getString("aika");
            String nimim = rs.getString("nimim");
            Viesti v = new Viesti(id, ketjuId, viesti, aika);
            viestitKetjussa.add(v);
            v.setNimim(nimim);
        }

        return viestitKetjussa;
    }

    public void lisaaViesti(String viesti, String nimim, Integer key) throws SQLException{
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Viesti (ketju_id, viesti, aika, nimim) VALUES (?, ?, DATETIME('now', 'localtime'), ?)");
        stmt.setInt(1, key);
        stmt.setString(2, viesti);
        stmt.setString(3, nimim);

        stmt.executeUpdate();

        stmt.close();
        connection.close();
    }

    @Override
    public void delete(Integer key) throws SQLException {

    }
}
