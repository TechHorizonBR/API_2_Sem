package Test;

import Model.DAO.SistemaDAO;
import Model.DTO.AlunoDTO;
import Model.DTO.OrientadorDTO;
import Model.DTO.TurmaDTO;

public class SistemaTest {
    public static void main(String[] args){
        SistemaDAO sistemaDAO = new SistemaDAO();

        //sistemaDAO.addAluno(new AlunoDTO("Jhony", "jhony.santos8@fatec.sp.gov.br", "santosjhony@email.com", 2));
      // System.out.println(sistemaDAO.getAllAlunos());
       // sistemaDAO.addOrientador(new OrientadorDTO("Professor5", "professor5@fatec.sp.gov.br"));
        /*OrientadorDTO orientadorDTO = new OrientadorDTO();
        orientadorDTO.setNome("Testando novo professor");
        orientadorDTO.setEmail("meunovoprofessor@email.com");*
        //sistemaDAO.addOrientador(orientadorDTO);
        //System.out.println(sistemaDAO.getAllOrientador());

        //sistemaDAO.addTurma(turmaDTO);

*/

        //AlunoDTO alunoDTO = new AlunoDTO(13L, "André", "andre@email.com", "andre@fatec.sp.gov.br", Long.valueOf(3));

        //sistemaDAO.updateAluno(alunoDTO);

        //System.out.println("Aluno: "+sistemaDAO.getAlunoPorEmail("teset@fatec.com"));
        //System.out.println("Orientador: "+sistemaDAO.getOrientadorPorEmail("professor@email.com"));
        System.out.println("Pegando Orientador por id: "+sistemaDAO.getOrientadorPorId(2L));
    }
}
