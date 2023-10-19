package com.tgsync.tgsync;

import Model.DAO.EntregaDAO;
import Model.DAO.TurmaDAO;
import Model.DTO.EntregaDTO;
import Model.DTO.TurmaDTO;
import Model.Service.TurmaService;
import Model.util.Alerts;
import com.tgsync.tgsync.util.MudancaTelas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;

public class TelaEntregasController extends MudancaTelas {

    @FXML
    private Button ButtonCadastrar;

    @FXML
    private TableColumn<EntregaDTO, Date> colunaDataEntrega;

    @FXML
    private TableColumn<EntregaDTO, String> colunaTitulo;

    @FXML
    private TableView<EntregaDTO> tabelaEntregas;

    @FXML
    private ComboBox<Integer> comboBoxTG;

    @FXML
    private DatePicker dateDataEntrega;

    @FXML
    private TextField textFieldTitulo;


    @FXML
    public void adicionarEntrega(ActionEvent event){
        String titulo = textFieldTitulo.getText();
        LocalDate data = dateDataEntrega.getValue();
        Integer tg = comboBoxTG.getValue();

        LocalDate dataAtual = LocalDate.now();

        if (titulo.equals("")){
            Alerts.showAlert("Atenção!", "", "Não é possível cadastrar uma entrega com o título vazio.", Alert.AlertType.WARNING);
        }else if (data.isBefore(dataAtual)){
            Alerts.showAlert("Atenção!","","Não é possível inserir uma data anterior a atual", Alert.AlertType.WARNING);
        }else {
            EntregaDAO entregaDAO = new EntregaDAO();
            TurmaDAO turmaDAO = new TurmaDAO();
            TurmaDTO turmaService = new TurmaDTO();

            try {
                turmaService = TurmaService.buscarTurmaComDataDoPC();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            turmaService.setDisciplina(tg);
            turmaService = TurmaDAO.getTurmaPorAtributo(turmaService);

            entregaDAO.addEntrega(new EntregaDTO(data, titulo));


        }

    }

}

