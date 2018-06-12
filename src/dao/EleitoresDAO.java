package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Eleitores;
import util.ConnectionJDBC;

/**
 *
 * @author lw005973
 */
public class EleitoresDAO {
    Connection connection;
    
    public EleitoresDAO() throws Exception {
        connection = ConnectionJDBC.getConnection();
    }
    
    public List<Eleitores> findAll() throws Exception {
        List<Eleitores> list = new ArrayList<>();
        Eleitores objeto;
        String SQL = "SELECT * FROM ELEITORES ORDER BY CELEITOR";
        
        try {
            PreparedStatement p = connection.prepareStatement(SQL);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                objeto = new Eleitores();
                objeto.setcEleitores(rs.getInt("CELEITOR"));
                objeto.setNomeLeitor(rs.getString("NOMEELEITOR"));
                objeto.setSenha(rs.getString("SENHA"));
                objeto.setCpf(rs.getString("CPF"));
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

