package Ali_Nauta.Dao;

import Ali_Nauta.Database.Database;
import Ali_Nauta.Domain.Alue;
import Ali_Nauta.Domain.ViestiKetju;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ViestiKetjuDao implements Dao<ViestiKetju, Integer> {

    private Database database;

    public ViestiKetjuDao(Database database) {
        this.database = database;
    }

    @Override
    public ViestiKetju findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();

        int id = rs.getInt("id");
        String otsikko = rs.getString("otsikko");
        int alueId = rs.getInt("alue_id");

        ViestiKetju vk = new ViestiKetju(id, alueId, otsikko);

        rs.close();
        stmt.close();
        connection.close();

        return vk;
    }

    public ViestiKetju etsiOtsikolla(String otsikko) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju WHERE otsikko = ?");
        stmt.setString(1, otsikko);

        ResultSet rs = stmt.executeQuery();

        int id = rs.getInt("id");
        String otsik = rs.getString("otsikko");
        int alueId = rs.getInt("alue_id");

        ViestiKetju vk = new ViestiKetju(id, alueId, otsik);

        rs.close();
        stmt.close();
        connection.close();

        return vk;
    }


    @Override
    public List<ViestiKetju> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ViestiKetju> findAllin(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM viestiketju WHERE viestiketju.alue_id = ?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();

        List<ViestiKetju> ketjutAlueella = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            int alue_id = rs.getInt("alue_id");
            String otsikko = rs.getString("otsikko");
            int viestienMaara = viestitKetjussa(id);
            ViestiKetju ketju = new ViestiKetju(id, alue_id, otsikko);
            ketjutAlueella.add(ketju);
            ketju.setViestienMaara(viestienMaara);
        }

        rs.close();
        stmt.close();
        connection.close();
        return ketjutAlueella;
    }

    public int viestitKetjussa(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(viesti.id) FROM viesti,viestiketju " +
                "WHERE viesti.ketju_id = viestiketju.id AND viestiketju.id = ?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();

        int maara = rs.getInt(1);

        rs.close();
        stmt.close();
        connection.close();
        return maara;
    }

    public void uusiViestiKetju(Integer key, String ketjunNimi) throws SQLException{
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO viestiketju (alue_id, otsikko) VALUES (?, ?)");
        stmt.setInt(1, key);
        stmt.setString(2, ketjunNimi);

        stmt.executeUpdate();

        stmt.close();
        connection.close();
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
