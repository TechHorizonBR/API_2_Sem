package Model.ConexaoBD;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    public static Connection ConexaoBD() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/tgsync", "root", "28312515");
        }catch (SQLException e){
            System.out.println("Houve algum erro de SQL"+e.getMessage());
        }catch (ClassNotFoundException e){
            System.out.println("Driver não encontrado"+e.getMessage());
        }
        return connection;

    }

}
