package Model.ConexaoBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    public static Connection ConexaoBD() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/tgsync", "root","");
        return connection;
    }

}
