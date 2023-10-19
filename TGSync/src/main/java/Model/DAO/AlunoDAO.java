package Model.DAO;

import Model.ConexaoBD.ConexaoBD;
import Model.DTO.AlunoDTO;
import Model.DTO.TurmaDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class    AlunoDAO {
    Connection connection = null;
    public void addAluno(AlunoDTO aluno){
        PreparedStatement stmt = null;

        try{
            connection = ConexaoBD.ConexaoBD();

            String sql = "INSERT INTO aluno(nome, emailFatec, emailPessoal, idOrientador) VALUES (?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmailFatec());
            stmt.setString(3, aluno.getEmailPessoal());
            if(aluno.getIdOrientador() != null){
                stmt.setLong(4, aluno.getIdOrientador());
            }else{
                stmt.setNull(4, java.sql.Types.BIGINT);
            }
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
    }
    public List<AlunoDTO> getAllAlunos() {
        Map<Long, AlunoDTO> alunosMap = new HashMap<>();
        List<AlunoDTO> alunos = new LinkedList<>();

        try {
            connection = ConexaoBD.ConexaoBD();
            String sql = "SELECT a.id, a.nome, a.emailFatec, a.emailPessoal, a.idOrientador, m.idTurma FROM aluno a INNER JOIN matricula m ON a.id = m.idAluno";//
            ResultSet rs = connection.createStatement().executeQuery(sql);

            while (rs.next()) {
                Long alunoId = rs.getLong("a.id");

                if (!alunosMap.containsKey(alunoId)) {
                    AlunoDTO aluno = new AlunoDTO(alunoId, rs.getString("a.nome"), rs.getString("a.emailPessoal"), rs.getString("a.emailFatec"), rs.getLong("a.idOrientador"));
                    alunosMap.put(alunoId, aluno);
                }
                alunosMap.get(alunoId).setIdTurma(rs.getLong("m.idTurma"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>(alunosMap.values());
    }

    public void updateAluno(AlunoDTO alunoDTO){
        PreparedStatement stmt = null;

        try{
            connection = ConexaoBD.ConexaoBD();

            String sql = "UPDATE aluno SET nome = ?, emailFatec = ?, emailPessoal = ?, idOrientador = ? WHERE id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, alunoDTO.getNome());
            stmt.setString(2, alunoDTO.getEmailFatec());
            stmt.setString(3, alunoDTO.getEmailPessoal());
            stmt.setLong(4, alunoDTO.getIdOrientador());
            stmt.setLong(5, alunoDTO.getId());
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

    public AlunoDTO getAlunoPorEmail(String emailFatec) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = ConexaoBD.ConexaoBD();

            String sql = "SELECT a.*, m.idTurma FROM aluno a INNER JOIN matricula m ON m.idAluno = a.id WHERE a.emailFatec = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, emailFatec);
            rs = stmt.executeQuery();

            AlunoDTO alunoDTO = null;

            while (rs.next()) {
                if (alunoDTO == null) {
                    alunoDTO = new AlunoDTO(
                            rs.getLong("a.id"),
                            rs.getString("a.nome"),
                            rs.getString("a.emailPessoal"),
                            rs.getString("a.emailFatec"),
                            rs.getLong("a.idOrientador")
                    );
                }

                alunoDTO.setIdTurma(rs.getLong("m.idTurma"));
            }

            return alunoDTO;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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

    public AlunoDTO getAlunoPorId(Long id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = ConexaoBD.ConexaoBD();
            String sql = "SELECT a.*, m.idTurma FROM aluno a INNER JOIN matricula m ON m.idAluno = a.id WHERE a.id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            AlunoDTO alunoDTO = null;
            List<Long> idTurmas = new ArrayList<>();

            while (rs.next()) {
                if (alunoDTO == null) {
                    alunoDTO = new AlunoDTO(
                            rs.getLong("a.id"),
                            rs.getString("a.nome"),
                            rs.getString("a.emailPessoal"),
                            rs.getString("a.emailFatec"),
                            rs.getLong("a.idOrientador")
                    );
                }

                Long idTurma = rs.getLong("m.idTurma");
                alunoDTO.setIdTurma(idTurma);
            }

            return alunoDTO;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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

    public void addMatriculaAluno(AlunoDTO alunoDTO, TurmaDTO turmaDTO){
        PreparedStatement stmt = null;

        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "INSERT INTO matricula VALUES (?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, alunoDTO.getId());
            stmt.setLong(2, turmaDTO.getId());
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

    public List<Long> getAllMatriculaPorIdTurma(TurmaDTO turmaDTO){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Long> matriculasEncontradas = new LinkedList<>();

        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "SELECT * FROM matricula WHERE idTurma = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, turmaDTO.getId());
            rs = stmt.executeQuery();

            while(rs.next()){
                matriculasEncontradas.add(rs.getLong("idAluno"));
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
        return matriculasEncontradas;
    }

    public AlunoDTO getAlunoPorEmailSemMatricula(String emailFatec) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = ConexaoBD.ConexaoBD();

            String sql = "SELECT * FROM aluno WHERE emailFatec = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, emailFatec);
            rs = stmt.executeQuery();

            AlunoDTO alunoDTO = null;

            while (rs.next()) {
                alunoDTO = new AlunoDTO(
                            rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getString("emailPessoal"),
                            rs.getString("emailFatec"),
                            rs.getLong("idOrientador")
                    );

            }

            return alunoDTO;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
}
