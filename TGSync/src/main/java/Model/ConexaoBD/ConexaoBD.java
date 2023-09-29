package Model.ConexaoBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    public static Connection ConexaoBD() throws SQLException {
        Connection connection = DriverManager.getConnection("localhost:3307/TGSync", "root","28312515");
        return connection;
    }

}
