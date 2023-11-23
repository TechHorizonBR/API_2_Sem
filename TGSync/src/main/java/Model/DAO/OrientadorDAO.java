package Model.DAO;

import Model.ConexaoBD.ConexaoBD;
import Model.DTO.OrientadorDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class OrientadorDAO {
    private Connection connection;
    public void addOrientador(OrientadorDTO orientadorDTO){
        PreparedStatement stmt = null;
        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "INSERT INTO orientador(nome, emailFatec) VALUES(?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, orientadorDTO.getNome());
            stmt.setString(2, orientadorDTO.getEmail());
            stmt.executeUpdate();

        }catch (SQLException e){
            System.out.println("Houve um erro de SQL"+e.getMessage());
        }catch (ClassNotFoundException e){
            System.out.println("Não foi possível encontrar o Driver");
        }finally {
            try{
                connection.close();
                stmt.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    public List<OrientadorDTO> getAllOrientador(){
        List<OrientadorDTO> orientadoresEncontrados = new LinkedList<>();

        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "SELECT * FROM orientador";
            ResultSet rs = connection.createStatement().executeQuery(sql);

            while(rs.next()){
                orientadoresEncontrados.add(new OrientadorDTO(rs.getLong("id"), rs.getString("nome"), rs.getString("emailFatec")));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }finally {
            try{
                connection.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }

        return orientadoresEncontrados;
    }

    public OrientadorDTO getOrientadorPorEmail(String email){
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "SELECT * FROM orientador WHERE emailFatec = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            while(rs.next()){
                OrientadorDTO orientadorDTO = new OrientadorDTO(rs.getLong("id"), rs.getString("emailFatec"), rs.getString("nome"));
                return orientadorDTO;
            }
        }catch (SQLException e){
            e.getMessage();
        }catch (ClassNotFoundException e){
            e.getMessage();
        }finally {
            try{
                connection.close();
            }catch (SQLException e){
                e.getMessage();
            }
        }
        return null;
    }

    public OrientadorDTO getOrientadorPorId(Long id){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "SELECT * FROM orientador WHERE id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            while (rs.next()){
                OrientadorDTO orientadorDTO = new OrientadorDTO(rs.getLong("id"), rs.getString("nome"), rs.getString("emailFatec"));
                return orientadorDTO;
            }

        }catch (SQLException e){
            e.getMessage();
        }catch (ClassNotFoundException e){
            e.getMessage();
        }finally {
            try{
                if(connection!= null) connection.close();
            }catch (SQLException e){
                e.getMessage();
            }
        }
        return null;
    }
    public List<Long> getIdsOrientadores(){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Long> idsOrientadores = new LinkedList<>();

        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "SELECT id FROM orientador";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()){
                idsOrientadores.add(rs.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return idsOrientadores;
    }

}
