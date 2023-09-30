package Model.DAO;

import Model.ConexaoBD.ConexaoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SistemaDAO {
    private Connection connection;
    /*

    public void addAluno(AlunoDTO aluno){
        PreparedStatement stmt = null;

        try{
            connection = ConexaoBD.ConexaoBD();

            String sql = "INSERT INTO aluno(nome, emailFatec, emailPessoal, idOrientador) VALUES (?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmailFatec());
            stmt.setString(3, aluno.getEmailPessoal());
            stmt.setInt(4, aluno.getIdOrientador());
            stmt.executeUpdate();
            System.out.println("Aluno adiconado com sucesso");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                stmt.close();
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }*/
}
