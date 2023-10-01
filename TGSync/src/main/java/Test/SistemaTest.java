package Test;

import Model.DAO.SistemaDAO;

public class SistemaTest {
    public static void main(String[] args){
        SistemaDAO sistemaDAO = new SistemaDAO();

        //sistemaDAO.addAluno(new AlunoDTO("Jhony", "jhony.santos8@fatec.sp.gov.br", "santosjhony@email.com", 2));
        System.out.println(sistemaDAO.getAllAlunos());
       // sistemaDAO.addOrientador(new OrientadorDTO("Professor5", "professor5@fatec.sp.gov.br"));

        System.out.println(sistemaDAO.getAllOrientador());
    }
}
