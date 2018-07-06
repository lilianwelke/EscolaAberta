package dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
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

    public void inserirCandidato(Candidatos candidatos) {
        String SQL = "INSERT INTO CANDIDATOS (NUMCANDIDATO, NOMECANDIDATO, CPARTIDO, FOTOCANDIDATO) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement p = connection.prepareStatement(SQL);
            p.setInt(1, candidatos.getNumCandidato());
            p.setString(2, candidatos.getNomeCandidato());
            p.setInt(3, candidatos.getcPartido().getcPartido());
            p.setBinaryStream(4, new FileInputStream(candidatos.getFotoCandidato()));
            p.execute();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public List<Candidatos> findAll() throws Exception {
        List<Candidatos> list = new ArrayList<>();
        Candidatos objeto;
        Partidos objeto2;
        String SQL = "SELECT * FROM CANDIDATOS "
                + " INNER JOIN PARTIDOS ON (PARTIDOS.CPARTIDO = CANDIDATOS.CPARTIDO)"
                + " ORDER BY CCANDIDATO";

        try {
            PreparedStatement p = connection.prepareStatement(SQL);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                objeto = new Candidatos();
                objeto2 = new Partidos();
                objeto.setcCandidato(rs.getInt("CCANDIDATO"));
                objeto.setNumCandidato(rs.getInt("NUMCANDIDATO"));
                objeto.setNomeCandidato(rs.getString("NOMECANDIDATO"));
                //               objeto.setFotoCandidato(rs.getBlob("FOTOCANDIDATO"));
                objeto2.setcPartido(rs.getInt("CPARTIDO"));
                objeto2.setNumPartido(rs.getInt("NUMPARTIDO"));
                objeto2.setNomePartido(rs.getString("NOMEPARTIDO"));
                objeto.setcPartido(objeto2);
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
