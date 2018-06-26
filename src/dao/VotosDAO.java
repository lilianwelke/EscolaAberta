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
import model.Partidos;
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
    
    public boolean validaCodigo(int numCandidato){
        Candidatos objeto = new Candidatos();
        Partidos objeto2 = new Partidos();
        String SQL = "SELECT CANDIDATOS.CCANDIDATO"
                + " FROM CANDIDATOS"
                + " WHERE CANDIDATOS.NUMCANDIDATO=?";

        try {
            PreparedStatement p = connection.prepareStatement(SQL);
            p.setInt(1, numCandidato);
            ResultSet rs = p.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(VotosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //FAZER OUTRO METODO QUE RETORNA TRUE OU FALSE BASEADO NA PESQUISA QUE TEM A FK
    public Candidatos verificaCodigo(int numCandidato) {
        Candidatos objeto = new Candidatos();
        Partidos objeto2 = new Partidos();
        String SQL = "SELECT CANDIDATOS.NUMCANDIDATO, CANDIDATOS.CCANDIDATO, CANDIDATOS.NOMECANDIDATO, PARTIDOS.CPARTIDO, PARTIDOS.NUMPARTIDO, PARTIDOS.NOMEPARTIDO"
                + " FROM CANDIDATOS"
                + " INNER JOIN PARTIDOS ON (PARTIDOS.CPARTIDO = CANDIDATOS.CPARTIDO)"
                + " WHERE CANDIDATOS.NUMCANDIDATO=?";

        try {
            PreparedStatement p = connection.prepareStatement(SQL);
            p.setInt(1, numCandidato);
            ResultSet rs = p.executeQuery();

            while (rs.next()) {
                objeto.setcCandidato(rs.getInt("CCANDIDATO"));
                objeto.setNumCandidato(rs.getInt("NUMCANDIDATO"));
                objeto.setNomeCandidato(rs.getString("NOMECANDIDATO"));

                objeto2.setcPartido(rs.getInt("CPARTIDO"));
                objeto2.setNumPartido(rs.getInt("NUMPARTIDO"));
                objeto2.setNomePartido(rs.getString("NOMEPARTIDO"));
                objeto.setcPartido(objeto2);
            }
            rs.close();
            p.close();

        } catch (SQLException ex) {
            Logger.getLogger(VotosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //aqui vai a SQL
        //LA NO EVENT DO FORM, CHAMA ESSE MÉTODO PARA VERIFICAR SE O CANDIDATO EXISTE, SE EXISTE O CÓDIGO É VALIDO
        return objeto;
    }

    public void save(Candidatos candidatos, Votantes votantes) {

        String SQL = "INSERT INTO VOTOS(CCANDIDATO) VALUES(?) ";
        try {
            PreparedStatement p = connection.prepareStatement(SQL);
            p.setInt(1, candidatos.getcCandidato());
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

    public void saveBranco(Votantes votantes) {
        String SQL = "INSERT INTO VOTOS(CCANDIDATO) VALUES(?) ";
        try {
            PreparedStatement p = connection.prepareStatement(SQL);
            p.setInt(1, 0);
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

    public Candidatos getByNum(int numCandidato) {
        Candidatos objeto = new Candidatos();
        String SQL = "SELECT CANDIDATOS.*, PARTIDOS.*"
                + " FROM CANDIDATOS"
                + " INNER JOIN PARTIDOS ON (PARTIDOS.CPARTIDO = CANDIDATOS.CPARTIDO)"
                + " WHERE CANDIDATOS.NUMCANDIDATO = ?";

        try {
            PreparedStatement p = connection.prepareStatement(SQL);
            p.setInt(1, numCandidato);

            ResultSet rs = p.executeQuery();

            while (rs.next()) {
                objeto = new Candidatos();
                Partidos objeto2 = new Partidos();
                objeto.setcCandidato(rs.getInt("CCANDIDATO"));
                objeto.setNumCandidato(rs.getInt("NUMCANDIDATO"));
                objeto.setNomeCandidato(rs.getString("NOMECANDIDATO"));

                objeto2.setcPartido(rs.getInt("CPARTIDO"));
                objeto2.setNumPartido(rs.getInt("NUMPARTIDO"));
                objeto2.setNomePartido(rs.getString("NOMEPARTIDO"));

                objeto.setcPartido(objeto2);
            }
            rs.close();
            p.close();
        } catch (SQLException ex) {
            Logger.getLogger(VotosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return objeto;
    }

    public List<Votos> findAll() throws Exception {
        List<Votos> list = new ArrayList<>();
        Votos objeto;
        String SQL = "SELECT * FROM VOTOS"
                + "INNER JOIN CANDIDATOS ON (CANDIDATOS.CCANDIDATO = VOTOS.CCANDIDATO)";

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
