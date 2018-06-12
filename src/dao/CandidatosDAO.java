package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Candidatos;
import model.Partidos;
import util.ConnectionJDBC;

/**
 *
 * @author lw005973
 */
public class CandidatosDAO {
    Connection connection;
    PartidosDAO partidoDAO = null;
    
    public CandidatosDAO() throws Exception {
        connection = ConnectionJDBC.getConnection();
        partidoDAO = new PartidosDAO();
    }
    
    public List<Candidatos> findAll() throws Exception {
        List<Candidatos> list = new ArrayList<>();
        Candidatos objeto;
        String SQL = "SELECT * FROM PARTIDOS ORDER BY CPARTIDO";
        
        try {
            PreparedStatement p = connection.prepareStatement(SQL);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                objeto = new Candidatos();
                objeto.setcCandidato(rs.getInt("CCANDIDATO"));
                objeto.setNumCandidato(rs.getInt("NUMCANDIDATO"));
                objeto.setNomeCandidato(rs.getString("NOMECANDIDATO"));
                
                objeto.setcPartido(partidoDAO.findById((rs.getInt("CPARTIDO"))));
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

