package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author lw005973
 */
public class ConnectionJDBC {
    private static Connection connection;

    public static Connection getConnection() throws Exception {

        if (connection == null) {
            try {
                Class.forName("org.firebirdsql.jdbc.FBDriver");
            } catch (ClassNotFoundException ex) {
                throw new Exception(ex);
            }

            String servidor = "localhost";
            String database = "/databases/escolaaberta.fdb";
            String user = "SYSDBA";
            String password = "masterkey";
            String url = "jdbc:firebirdsql:" + servidor + "/3050:" + database + "?encoding=WIN1252";

            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException ex) {
                throw new Exception(ex);
            }
        }
        return connection;
    }

    public static void main(String[] args) {
        System.out.println("Teste!");
        try {
            ConnectionJDBC.getConnection();
        } catch (Exception ex) {
            System.out.println(""+ex.getMessage());
        }
    }
}
