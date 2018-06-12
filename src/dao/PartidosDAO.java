package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Partidos;
import util.ConnectionJDBC;

/**
 *
 * @author lw005973
 */
public class PartidosDAO {
    Connection connection;
    
    public PartidosDAO() throws Exception {
        connection = ConnectionJDBC.getConnection();
    }
    
    public Partidos findById(int id){
        Partidos objeto = null;
        String SQL = "SELECT * FROM PARTIDOS WHERE CPARTIDO=?";
        
        try {
            PreparedStatement p = connection.prepareStatement(SQL);
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                objeto = new Partidos();
                objeto.setcPartido(rs.getInt("CPARTIDO"));
                objeto.setNumPartido(rs.getInt("NUMPARTIDO"));
                objeto.setNomePartido(rs.getString("NOMEPARTIDO"));
            }
            rs.close();
            p.close();

        } catch (SQLException ex) {
            try {
                throw new Exception(ex);
            } catch (Exception ex1) {
                Logger.getLogger(PartidosDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return objeto;
    }
    
    public List<Partidos> findAll() throws Exception {
        List<Partidos> list = new ArrayList<>();
        Partidos objeto;
        String SQL = "SELECT * FROM PARTIDOS ORDER BY CPARTIDO";
        
        try {
            PreparedStatement p = connection.prepareStatement(SQL);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                objeto = new Partidos();
                objeto.setcPartido(rs.getInt("CPARTIDO"));
                objeto.setNumPartido(rs.getInt("NUMPARTIDO"));
                objeto.setNomePartido(rs.getString("NOMEPARTIDO"));
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
