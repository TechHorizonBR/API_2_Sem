package Model.DAO;

import Model.ConexaoBD.ConexaoBD;
import Model.DTO.AlunoDTO;
import Model.DTO.OrientadorDTO;
import Model.DTO.TurmaDTO;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SistemaDAO {
    private Connection connection;

    public void addAluno(AlunoDTO aluno, TurmaDTO turmaDTO){
        PreparedStatement stmtAluno = null;
        PreparedStatement stmtMatricula = null;

        try{
            connection = ConexaoBD.ConexaoBD();

            String sql = "INSERT INTO aluno(nome, emailFatec, emailPessoal, idOrientador) VALUES (?, ?, ?, ?)";
            stmtAluno = connection.prepareStatement(sql);
            stmtAluno.setString(1, aluno.getNome());
            stmtAluno.setString(2, aluno.getEmailFatec());
            stmtAluno.setString(3, aluno.getEmailPessoal());
            stmtAluno.setInt(4, aluno.getIdOrientador());
            stmtAluno.executeUpdate();
            //System.out.println("Aluno adiconado com sucesso");

            ResultSet generatedKeys = stmtAluno.getGeneratedKeys();
            long alunoId = -1;

            if(generatedKeys.next()){
                alunoId = generatedKeys.getLong(1);
            }

            String sqlMatricula = "INSERT INTO matricula VALUES(?, ?)";
            stmtMatricula.setLong(1, alunoId);
            stmtMatricula.setLong(2, turmaDTO.getId());
            stmtMatricula.executeUpdate();

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
    }

    public List<AlunoDTO> getAllAlunos(){
        List<AlunoDTO> alunosEncontrados = new LinkedList<>();

        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "SELECT a.*, m.idTurma FROM aluno a INNER JOIN matricula m WHERE a.id = m.idAluno";
            ResultSet rs = connection.createStatement().executeQuery(sql);

            while(rs.next()){
                alunosEncontrados.add(new AlunoDTO(rs.getLong("a.id"), rs.getString("a.nome"), rs.getString("a.emailFatec"), rs.getString("a.emailPessoal"), rs.getInt("a.idOrientador"), rs.getInt("m.idTurma"));
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
*/
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
   /* public List<OrientadorDTO> getAllOrientador(){
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
    }*/

    public void addTurma(TurmaDTO turmaDTO){
        PreparedStatement stmt = null;

        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "INSERT INTO turma(ano, semestre, disciplina) VALUES(?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, turmaDTO.getAno());
            stmt.setInt(2, turmaDTO.getSemestre());
            stmt.setInt(3, turmaDTO.getDisciplina());
            stmt.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }finally {
            try{
                connection.close();
                stmt.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
/*
    public List<TurmaDTO> getAllTurmas(){
        List<TurmaDTO> listaTurmas = new LinkedList<>();

        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "SELECT * FROM turma";
            ResultSet rs = connection.createStatement().executeQuery(sql);

            while(rs.next()){
                listaTurmas.add(new TurmaDTO(rs.getLong("id"), rs.getInt("ano"), rs.getInt("semestre"), rs.getInt("disciplina")));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return listaTurmas;
    }
*/
}
