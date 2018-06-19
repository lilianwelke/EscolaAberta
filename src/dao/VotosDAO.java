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

    //FAZER OUTRO METODO QUE RETORNA TRUE OU FALSE BASEADO NA PESQUISA QUE TEM A FK
    public boolean verificaCodigo(){
        //aqui vai a SQL
        //LA NO EVENT DO FORM, CHAMA ESSE MÉTODO PARA VERIFICAR SE O CANDIDATO EXISTE, SE EXISTE O CÓDIGO É VALIDO
        return false;
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

    public void saveBranco(Votos voto, Votantes votantes) {
        String SQL = "INSERT INTO VOTOS(VOTO, CCANDIDATO) VALUES(?, ?) ";
        try {
            PreparedStatement p = connection.prepareStatement(SQL);
            p.setInt(1, voto.getVoto());
            p.setInt(2, 0);
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
        String SQL = "SELECT CANDIDATO.NOMECANDIDATO, PARTIDO.NUMPARTIDO"
                + " FROM CANDIDATOS"
                + " INNER JOIN PARTIDO ON (PARTIDO.CPARTIDO = CANDIDATO.CPARTIDO)"
                + " WHERE CANDIDATO.NUMCANDIDATO = ?";

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
