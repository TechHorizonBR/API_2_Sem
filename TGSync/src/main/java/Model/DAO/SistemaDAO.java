package Model.DAO;

import Model.ConexaoBD.ConexaoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SistemaDAO {
    private Connection connection;


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
            //System.out.println("Aluno adiconado com sucesso");

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
    }

    public List<AlunoDTO> getAllAlunos(){
        List<AlunoDTO> alunosEncontrados = new LinkedList<>();

        try{
            connection = ConexaoBD.ConexaoBD();
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM aluno");

            while(rs.next()){
                alunosEncontrados.add(new AlunoDTO(rs.getInt("id"), rs.getString("nome"), rs.getString("emailFatec"), rs.getString("emailPessoal"), rs.getInt("idOrientador")));
            }

        }catch (SQLException e){
            System.out.println("Houve algum erro no SQL"+e.getMessage());
        }catch (ClassNotFoundException e){
            System.out.println("Não foi possível encontrar o driver"+e.getMessage());
        }finally {
            try{
                connection.close();
            }catch (SQLException e){
                System.out.println("Houve algum erro no fechamento da conexão"+e.getMessage());
            }
        }
        return alunosEncontrados;
    }

    public void addOrientador(OrientadorDTO orientadorDTO){
        PreparedStatement stmt = null;
        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "INSERT INTO orientador(nome, emailFatec) VALUES(?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, orientadorDTO.getNome());
            stmt.setString(2, orientadorDTO.getEmailFatec());
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
                orientadoresEncontrados.add(new OrientadorDTO(rs.getInt("id"), rs.getString("nome"), rs.getString("emailFatec")));
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
}
