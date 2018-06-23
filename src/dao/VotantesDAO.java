package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Votantes;
import util.ConnectionJDBC;

/**
 *
 * @author lw005973
 */
public class VotantesDAO {

    Connection connection;

    public VotantesDAO() throws Exception {
        connection = ConnectionJDBC.getConnection();
    }

    public List<Votantes> findAll() throws Exception {
        List<Votantes> list = new ArrayList<>();
        Votantes objeto;
        String SQL = "SELECT * FROM VOTANTES ORDER BY CVOTANTES";

        try {
            PreparedStatement p = connection.prepareStatement(SQL);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                objeto = new Votantes();
                //objeto.setEleitor(rs.getInt("CELEITOR"));
                list.add(objeto);
            }
            rs.close();
            p.close();

        } catch (SQLException ex) {
            throw new Exception(ex);
        }
        return list;
    }
}
