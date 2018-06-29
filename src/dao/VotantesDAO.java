package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Eleitores;
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
        Eleitores objeto2;
        String SQL = "SELECT * FROM VOTANTES ORDER BY CELEITOR";

        try {
            PreparedStatement p = connection.prepareStatement(SQL);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                objeto = new Votantes();
                objeto2 = new Eleitores();
                objeto2.setcEleitores(rs.getInt("CELEITOR"));
                objeto.setEleitor(objeto2);
                list.add(objeto);
            }
            rs.close();
            p.close();

        } catch (SQLException ex) {
            throw new Exception(ex);
        }
        return list;
    }
    
    public Votantes findById(int num) throws Exception {
        List<Votantes> list = new ArrayList<>();
        Votantes objeto = null;
        Eleitores objeto2;
        String SQL = "SELECT * FROM VOTANTES "
                + " WHERE VOTANTES.CELEITOR = " + num
                + "ORDER BY CELEITOR";

        try {
            PreparedStatement p = connection.prepareStatement(SQL);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                objeto = new Votantes();
                objeto2 = new Eleitores();
                objeto2.setcEleitores(rs.getInt("CELEITOR"));
                objeto.setEleitor(objeto2);
                list.add(objeto);
            }
            rs.close();
            p.close();

        } catch (SQLException ex) {
            throw new Exception(ex);
        }
        return objeto;
    }
}
