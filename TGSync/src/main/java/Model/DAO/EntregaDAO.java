package Model.DAO;

import Model.ConexaoBD.ConexaoBD;
import Model.DTO.EntregaDTO;
import Model.DTO.NotaDTO;
import Model.DTO.TGDTO;
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
            String sql = "INSERT INTO entrega(titulo, dataEntrega, idTurma, tipo) VALUES(?, ?, ?, ?); ";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, entregaDTO.getTituloEntrega());
            java.util.Date utilDate = entregaDTO.getDataEntrega();
            stmt.setTimestamp(2, new Timestamp(utilDate.getTime()));
            stmt.setLong(3, turmaDTO.getId());
            stmt.setString(4, entregaDTO.getTipo());
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
                entregasEncontradas.add(new EntregaDTO(rs.getLong("id"), rs.getDate("dataEntrega"), rs.getString("titulo"), rs.getLong("idTurma"), rs.getString("tipo")));
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
    public List<EntregaDTO> getEntregasPorAnoSemestre(TurmaDTO turmaDTOTG1, TurmaDTO turmaDTOTG2){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<EntregaDTO> entregasEncontradas = new LinkedList<>();

        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "SELECT * FROM entrega WHERE idTurma = ? or idTurma = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, turmaDTOTG1.getId());
            stmt.setLong(2, turmaDTOTG2.getId());
            rs = stmt.executeQuery();

            while(rs.next()){
                entregasEncontradas.add(new EntregaDTO(rs.getLong("id"), rs.getDate("dataEntrega"), rs.getString("titulo"), rs.getLong("idTurma"), rs.getString("tipo")));
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
    public List<EntregaDTO> getEntregasPorIdTurmaTipoTG(TurmaDTO turmaDTO, TGDTO tgdto){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<EntregaDTO> entregasEncontradas = new LinkedList<>();

        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "SELECT * FROM entrega WHERE idTurma = ? and tipo like ? ";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, turmaDTO.getId());
            String tipoTG = "";
            if(tgdto.getTipo().contains("Portfólio")){
                tipoTG = "Portfólio";
            } else if (tgdto.getTipo().contains("Relatório")) {
                tipoTG = "Relatório";
            } else if (tgdto.getTipo().contains("Artigo")) {
                tipoTG = "Artigo";
            }
            stmt.setString(2, "%" + tipoTG + "%");
            rs = stmt.executeQuery();

            while(rs.next()){
                entregasEncontradas.add(new EntregaDTO(rs.getLong("id"), rs.getDate("dataEntrega"), rs.getString("titulo"), rs.getLong("idTurma"), rs.getString("tipo")));
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

    public List<Long> getIdEntregasPorTurma(Long idTurma, String tipo){
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Long> listaIds = new LinkedList<>();

        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "SELECT id FROM entrega WHERE idTurma = ? and tipo LIKE ?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, idTurma);
            stmt.setString(2, "%" + tipo + "%");
            rs = stmt.executeQuery();

            while(rs.next()){
                listaIds.add(rs.getLong("id"));
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
        return listaIds;
    }

    public List<Long> getIdEntregasPorTurma(Long idTurma){
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Long> listaIds = new LinkedList<>();

        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "SELECT id FROM entrega WHERE idTurma = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, idTurma);
            rs = stmt.executeQuery();

            while(rs.next()){
                listaIds.add(rs.getLong("id"));
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
        return listaIds;
    }

    public EntregaDTO getEntregaPorId(Long id){
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = ConexaoBD.ConexaoBD();
            String sql = "SELECT * FROM entrega WHERE id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            while(rs.next()) {
                EntregaDTO entregaDTO = new EntregaDTO(rs.getLong("id"), rs.getDate("dataEntrega"), rs.getString("titulo"), rs.getLong("idTurma"), rs.getString("tipo"));
                return entregaDTO;
            }
        } catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public EntregaDTO updateEntrega (EntregaDTO entregaDTO) {
        PreparedStatement stmt = null;


        try {
            connection = ConexaoBD.ConexaoBD();

            String sql = "UPDATE entrega SET titulo = ?, dataEntrega = ? WHERE id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, entregaDTO.getTituloEntrega());
            java.util.Date utilDate = entregaDTO.getDataEntrega();
            stmt.setTimestamp(2, new Timestamp(utilDate.getTime()));
            stmt.setLong(3, entregaDTO.getIdEntrega());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();

            }
        }
        return entregaDTO;
    }
}