package Model.DAO;

import Model.ConexaoBD.ConexaoBD;
import Model.DTO.EntregaDTO;
import Model.DTO.TurmaDTO;

import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EntregaDAO {

    Connection connection = null;

    public void addEntrega(EntregaDTO entregaDTO, TurmaDTO turmaDTO){
        PreparedStatement stmt = null;

        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "INSERT INTO entrega(titulo, dataEntrega, idTurma) VALUES(?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, entregaDTO.getTituloEntrega());
            java.util.Date utilDate = entregaDTO.getDataEntrega();
            stmt.setTimestamp(2, new Timestamp(utilDate.getTime()));
            stmt.setLong(3, turmaDTO.getId());
            stmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            try{
                if(connection!=null) connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public List<EntregaDTO> getEntregasPorIdTurma(TurmaDTO turmaDTO){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<EntregaDTO> entregasEncontradas = new LinkedList<>();

        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "SELECT * FROM entrega WHERE idTurma = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, turmaDTO.getId());
            rs = stmt.executeQuery();

            while(rs.next()){
                entregasEncontradas.add(new EntregaDTO(rs.getLong("id"), rs.getDate("dataEntrega"), rs.getString("titulo"), rs.getLong("idTurma")));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            try{
                if(connection!=null) connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return entregasEncontradas;
    }
}