package Model.DAO;

import Model.ConexaoBD.ConexaoBD;
import Model.DTO.TGDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TGDAO {
    Connection connection = null;

    public void addTg(TGDTO tgdto){
        PreparedStatement stmt = null;

        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "INSERT INTO tg(tipo, disciplina, problema, empresa, idAluno) VALUES (?, ?, ?, ?, ?)";
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

    public List<TGDTO> getAllTgs(){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<TGDTO> listaTgs = new LinkedList<>();

        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "SELECT * FROM tg";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()){
                listaTgs.add(new TGDTO(rs.getLong("id"), rs.getString("tipo"), rs.getString("disciplina"), rs.getString("problema"), rs.getString("empresa"), rs.getLong("idAluno")));
            }
        }catch (SQLException e){
            e.getMessage();
        }catch (ClassNotFoundException e){
            e.getMessage();
        }finally {
            try{
                if(connection!=null) connection.close();
            }catch (SQLException e){
                e.getMessage();
            }
        }
        return listaTgs;
    }

    public TGDTO getTgPorIdAluno(Long id){
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            connection = ConexaoBD.ConexaoBD();

            String sql = "SELECT * FROM tg WHERE idAluno = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            while(rs.next()){
                TGDTO tgdto = new TGDTO(rs.getLong("id"), rs.getString("tipo"), rs.getString("disciplina"), rs.getString("problema"), rs.getString("empresa"), rs.getLong("idAluno"));
                return tgdto;
            }
        }catch (SQLException e){
            e.getMessage();
        }catch (ClassNotFoundException e){
            e.getMessage();
        }finally {
            try{
                if(connection!=null) connection.close();
            }catch (SQLException e){
                e.getMessage();
            }
        }
        return null;
    }

    public List<TGDTO> getTgsPorIdAluno(Long id){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<TGDTO> tgsEncontrados = new LinkedList<>();

        try{
            connection = ConexaoBD.ConexaoBD();

            String sql = "SELECT * FROM tg WHERE idAluno = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            while(rs.next()){
                tgsEncontrados.add(new TGDTO(rs.getLong("id"), rs.getString("tipo"), rs.getString("disciplina"), rs.getString("problema"), rs.getString("empresa"), rs.getLong("idAluno")));
            }
        }catch (SQLException e){
            e.getMessage();
        }catch (ClassNotFoundException e){
            e.getMessage();
        }finally {
            try{
                if(connection!=null) connection.close();
            }catch (SQLException e){
                e.getMessage();
            }
        }
        return tgsEncontrados;
    }

    public void deletePorIdAluno(Long id){
        PreparedStatement stmt = null;

        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "DELETE FROM tg WHERE idAluno = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            try{
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
