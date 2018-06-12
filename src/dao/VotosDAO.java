package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Candidatos;
import model.Votantes;
import model.Votos;
import util.ConnectionJDBC;

/**
 *
 * @author lw005973
 */
public class VotosDAO {

    Connection connection;

    public VotosDAO() throws Exception {
        connection = ConnectionJDBC.getConnection();
    }

    public void save(Votos voto, Votantes votantes) {
        String SQL = "INSERT INTO VOTOS(VOTO, CCANDIDATO) VALUES(?, ?) ";
        try {
            PreparedStatement p = connection.prepareStatement(SQL);
            p.setInt(1, voto.getVoto());
            p.setInt(2, voto.getcCandidato().getcCandidato());
            p.execute();
            p.close();
            
            String SQL2 = "DELETE FROM VOTANTES WHERE CELEITOR=?";
            PreparedStatement p2 = connection.prepareStatement(SQL2);
            p2.setInt(1, votantes.getEleitor().getcEleitores());
            p2.execute();
            p2.close();
        } catch (SQLException ex) {
            Logger.getLogger(VotosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Votos> findAll() throws Exception {
        List<Votos> list = new ArrayList<>();
        Votos objeto;
        String SQL = "SELECT * FROM VOTOS"
                + "INNER JOIN CANDIDATOS ON (CANDIDATO.CCANDIDATO = VOTOS.CCANDIDATO)";

        try {
            PreparedStatement p = connection.prepareStatement(SQL);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                objeto = new Votos();
                objeto.setVoto(rs.getInt("VOTO"));

                Candidatos candidatos = new Candidatos();
                candidatos.setcCandidato(rs.getInt("CCANDIDATO"));
                objeto.setcCandidato(candidatos);
                list.add(objeto);
                
                //FAZER NOT IN(CÓDIGOS DOS CANDIDATOS)
            }
            rs.close();
            p.close();

        } catch (SQLException ex) {
            throw new Exception(ex);
        }
        return list;
    }
}
