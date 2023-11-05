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
        //notaDAO.addNota(new NotaDTO("DEU CERTO", 2.0, 504L,2L));
        List<Long> lista = new LinkedList<>();
     //   System.out.println(notaDAO.getNotaPorAlunoEntrega(new AlunoDTO(504L, "TETSE", "TESTE", "djfbj", 2L,lista ));
        System.out.println(notaDAO.getNotaPorId(1L));
        notaDAO.updateNota(new NotaDTO(1L, "Muito bom", 7.0, 1L, 1L));
        System.out.println(notaDAO.getNotaPorId(1L));
    }
}
