package Test;

import Model.DAO.EntregaDAO;
import Model.DAO.NotaDAO;
import Model.DTO.AlunoDTO;
import Model.DTO.EntregaDTO;
import Model.DTO.NotaDTO;
import Model.DTO.TurmaDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class NotaTest {
    public static void main(String[] args) {
        NotaDAO notaDAO = new NotaDAO();
        EntregaDAO entregaDAO = new EntregaDAO();

        NotaDTO notaDTO = notaDAO.getNotaPorAlunoEntrega(538L, 34L);
        System.out.println(notaDTO);
    }
}
