package Test;

import Model.DAO.TurmaDAO;
import Model.DTO.TurmaDTO;

public class TurmaTest {
    public static void main(String[] args) {
        TurmaDAO turmaDAO = new TurmaDAO();

        System.out.println(turmaDAO.getTurmaPorAtributo(new TurmaDTO(2023,1,1)));
    }
}
