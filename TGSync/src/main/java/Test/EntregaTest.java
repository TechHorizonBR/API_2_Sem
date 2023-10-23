package Test;

import Model.DAO.EntregaDAO;
import Model.DTO.EntregaDTO;
import Model.DTO.TurmaDTO;
import javafx.scene.chart.PieChart;

import java.time.LocalDate;
import java.util.Date;

public class EntregaTest {
    public static void main(String[] args) {
        EntregaDAO entregaDAO = new EntregaDAO();
        Date dataAtual = new Date();

        //entregaDAO.addEntrega(new EntregaDTO(dataAtual,"Nova Entrega"), new TurmaDTO(49L, 2023,1,2));
        //System.out.println(entregaDAO.getEntregasPorIdTurma(new TurmaDTO(49L, 2023,1,2)));

        System.out.println(entregaDAO.getEntregaPorId(1L));
        entregaDAO.updateEntrega(new EntregaDTO(1L, dataAtual, "Sprint 2", 1L ));

        System.out.println(entregaDAO.getEntregaPorId(1L));

    }
}
