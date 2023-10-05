package Model.DAO;

import Model.ConexaoBD.ConexaoBD;
import Model.DTO.TurmaDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TurmaDAO {
    Connection connection = null;

    public void addTurma(TurmaDTO turmaDTO) {
        PreparedStatement stmt = null;

        try {
            connection = ConexaoBD.ConexaoBD();
            String sql = "INSERT INTO turma(ano, semestre, disciplina) VALUES(?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, turmaDTO.getAno());
            stmt.setInt(2, turmaDTO.getSemestre());
            stmt.setInt(3, turmaDTO.getDisciplina());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.close();
                stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public List<TurmaDTO> getAllTurmas() {
        List<TurmaDTO> listaTurmas = new LinkedList<>();

        try {
            connection = ConexaoBD.ConexaoBD();
            String sql = "SELECT * FROM turma";
            ResultSet rs = connection.createStatement().executeQuery(sql);

            while (rs.next()) {
                listaTurmas.add(new TurmaDTO(rs.getLong("id"), rs.getInt("ano"), rs.getInt("semestre"), rs.getInt("disciplina")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return listaTurmas;
    }

    public TurmaDTO getTurmaPorId(Long id){
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "SELECT * FROM turma WHERE id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            while(rs.next()){
                TurmaDTO turmaDTO = new TurmaDTO(rs.getLong("id"), rs.getInt("ano"), rs.getInt("semestre"), rs.getInt("disciplina"));
                return turmaDTO;
            }

        }catch (SQLException e){
            e.getMessage();
        }catch (ClassNotFoundException e){
            e.getMessage();
        }finally {
            try{
                if(connection != null) connection.close();
            }catch (SQLException e){
                e.getMessage();
            }
        }
        return null;
    }
}
