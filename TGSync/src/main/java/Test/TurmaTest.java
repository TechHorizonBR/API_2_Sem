package Test;

import Model.DAO.TurmaDAO;

public class TurmaTest {
    public static void main(String[] args) {
        TurmaDAO turmaDAO = new TurmaDAO();

        System.out.println(turmaDAO.getTurmaPorId(8L));
    }
}
