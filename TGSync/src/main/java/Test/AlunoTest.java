package Test;

import Model.DAO.AlunoDAO;

public class AlunoTest {
    public static void main(String[] args) {
        AlunoDAO alunoDAO = new AlunoDAO();
        //System.out.println(alunoDAO.getAllAlunos());
        System.out.println(alunoDAO.getAlunoPorEmail("novoemailFatec@fatec.com"));
        System.out.println(alunoDAO.getAlunoPorId(12L));

    }
}
