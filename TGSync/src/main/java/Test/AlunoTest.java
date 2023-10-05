package Test;

import Model.DAO.AlunoDAO;

public class AlunoTest {
    public static void main(String[] args) {
        AlunoDAO alunoDAO = new AlunoDAO();

        System.out.println(alunoDAO.getAlunoPorId(Long.valueOf(12)));

        System.out.println(alunoDAO.getAlunoPorEmail("jhony.santos3@fatec.sp.gov.br"));
    }
}
