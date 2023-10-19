package Model.DAO;

import Model.ConexaoBD.ConexaoBD;
import Model.DTO.AlunoDTO;
import Model.DTO.EntregaDTO;
import Model.DTO.NotaDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotaDAO {

    Connection connection = null;

    public void addNota(NotaDTO notaDTO){
        PreparedStatement stmt = null;

        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "INSERT INTO nota (feedback, valor, idAluno, idEntrega) VALUES(?,?,?,?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1,notaDTO.getFeedback());
            stmt.setDouble(2,notaDTO.getValor());
            stmt.setLong(3,notaDTO.getIdAluno());
            stmt.setLong(4,notaDTO.getIdEntrega());

            stmt.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } finally {
            try {
                if(connection != null) connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public NotaDTO getNotaPorAlunoEntrega(AlunoDTO alunoDTO, EntregaDTO entregaDTO){
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "SELECT * FROM nota WHERE idAluno = ? and idEntrega = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1,alunoDTO.getId());
            stmt.setLong(2,entregaDTO.getIdEntrega());
            rs = stmt.executeQuery();
            while (rs.next()){
                NotaDTO notaDTO = new NotaDTO(rs.getLong("id"), rs.getString("feedback"), rs.getDouble("valor"), rs.getLong("idAluno"), rs.getLong("idEntrega"));
                return notaDTO;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            try {
                if(connection != null){
                    connection.close();
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
