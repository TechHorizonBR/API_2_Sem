package Model.DAO;

import Model.ConexaoBD.ConexaoBD;
import Model.DTO.TGDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TGDAO {
    Connection connection = null;

    public void addTg(TGDTO tgdto){
        PreparedStatement stmt = null;

        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "INSERTO INTO tg(tipo, disciplina, problema, empresa, idAluno) VALUES (?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, tgdto.getTipo());
            stmt.setString(2, tgdto.getDisciplina());
            stmt.setString(3, tgdto.getProblema());
            stmt.setString(4, tgdto.getEmpresa());
            stmt.setLong(5, tgdto.getIdAluno());
            stmt.executeUpdate();

        }catch (SQLException e){
            e.getMessage();
        }catch (ClassNotFoundException e ){
            e.getMessage();
        }finally {
            try{
                if(connection != null) connection.close();
            }catch (SQLException e){
                e.getMessage();
            }
        }
    }
}
