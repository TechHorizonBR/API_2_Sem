package com.tgsync.tgsync;

import Model.DAO.EntregaDAO;
import Model.DAO.TurmaDAO;
import Model.DTO.EntregaDTO;
import Model.DTO.TurmaDTO;
import Model.Service.TurmaService;
import Model.util.Alerts;
import com.tgsync.tgsync.util.MudancaTelas;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class TelaEntregasController extends MudancaTelas {

    @FXML
    private Button ButtonCadastrar;

    @FXML
    private TableColumn<EntregaDTO, Date> colunaDataEntregaTG1;

    @FXML
    private TableColumn<EntregaDTO, String> colunaTituloTG1;

    @FXML
    private TableView<EntregaDTO> tabelaEntregasTG1;
    @FXML
    private TableColumn<EntregaDTO, Date> colunaDataEntregaTG2;

    @FXML
    private TableColumn<EntregaDTO, String> colunaTituloTG2;

    @FXML
    private TableView<EntregaDTO> tabelaEntregasTG2;

    @FXML
    private ComboBox<Integer> comboBoxTG;

    @FXML
    private DatePicker dateDataEntrega;

    @FXML
    private TextField textFieldTitulo;
    @FXML
    ObservableList<EntregaDTO> obsListEntregasTG1 = FXCollections.observableArrayList();

    public void initialize(){
        try{
            updateTableTG1();
        }catch (ParseException e) {
            System.out.println("Alguma coisa deu erradi");
        }
    }

    @FXML
    public void adicionarEntrega(ActionEvent event){
        String titulo = textFieldTitulo.getText();
        LocalDate data = dateDataEntrega.getValue();
        Integer tg = comboBoxTG.getValue();
        System.out.println(titulo);
        LocalDate dataAtual = LocalDate.now();
        if(tg == null){
            Alerts.showAlert("Atenção!", "", "É necessário selecionar o TG.", Alert.AlertType.WARNING);
        } else if (titulo.equals("")){
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

            LocalDateTime localDateTime = data.atStartOfDay();
            Date date = java.util.Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            entregaDAO.addEntrega(new EntregaDTO(date,titulo), turmaService);
            try{
                updateTableTG1();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            textFieldTitulo.setText("");
            dateDataEntrega.setValue(null);
            comboBoxTG.setValue(null);
            Alerts.showAlert("SUCESSO!", "", "Entrega adicionada com sucesso", Alert.AlertType.CONFIRMATION);
        }

    }
    public void updateTableTG1() throws ParseException {
        EntregaDAO entregaDAO = new EntregaDAO();
        TurmaDTO turmaDTO = TurmaService.buscarTurmaComDataDoPC();
        turmaDTO.setDisciplina(1);
        turmaDTO = TurmaDAO.getTurmaPorAtributo(turmaDTO);

        obsListEntregasTG1.clear();
        tabelaEntregasTG1.setItems(null);

        List<EntregaDTO> listEntrega = entregaDAO.getEntregasPorIdTurma(turmaDTO);
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoSaida = new SimpleDateFormat("dd/MM/yyyy");

        if(!listEntrega.isEmpty()){
            for(EntregaDTO entrega : listEntrega){
                obsListEntregasTG1.add(entrega);
            }
        }

        obsListEntregasTG1 = FXCollections.observableArrayList(obsListEntregasTG1);
        colunaTituloTG1.setCellValueFactory(new PropertyValueFactory<>("tituloEntrega"));
        colunaDataEntregaTG1.setCellValueFactory(new PropertyValueFactory<>("dataEntregaFormatada"));
        tabelaEntregasTG1.setItems(obsListEntregasTG1);
    }
    @FXML
    void tableClickTG1(MouseEvent event) {
        int i = tabelaEntregasTG1.getSelectionModel().getSelectedIndex();
        EntregaDTO entregaDTO = (EntregaDTO)tabelaEntregasTG1.getItems().get(i);
        System.out.println(entregaDTO);


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditarEntrega.fxml"));
            Parent root = loader.load();

            EditarEntregaController editarEntregaController = loader.getController();

            // Passe a mensagem para o controlador da tela de pop-up
            editarEntregaController.setMessage(entregaDTO.getTituloEntrega(), entregaDTO.getIdEntrega(), 1);

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Editar Entrega");
            Scene scene = new Scene(root);
            popupStage.setScene(scene);

            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

