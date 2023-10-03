package Test;

import Model.DAO.SistemaDAO;
import Model.DTO.OrientadorDTO;
import Model.DTO.TurmaDTO;

public class SistemaTest {
    public static void main(String[] args){
        SistemaDAO sistemaDAO = new SistemaDAO();

        //sistemaDAO.addAluno(new AlunoDTO("Jhony", "jhony.santos8@fatec.sp.gov.br", "santosjhony@email.com", 2));
       // System.out.println(sistemaDAO.getAllAlunos());
       // sistemaDAO.addOrientador(new OrientadorDTO("Professor5", "professor5@fatec.sp.gov.br"));
        OrientadorDTO orientadorDTO = new OrientadorDTO();
        orientadorDTO.setNome("Testando novo professor");
        orientadorDTO.setEmail("meunovoprofessor@email.com");
        //sistemaDAO.addOrientador(orientadorDTO);
        System.out.println(sistemaDAO.getAllOrientador());
        TurmaDTO turmaDTO = new TurmaDTO();
        turmaDTO.setAno(2023);
        turmaDTO.setDisciplina(1);
        turmaDTO.setSemestre(2);
        sistemaDAO.addTurma(turmaDTO);

    }
}
