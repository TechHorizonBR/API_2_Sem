package com.tgsync.tgsync;

import Model.DAO.EntregaDAO;
import Model.DAO.TurmaDAO;
import Model.DTO.AlunoDTO;
import Model.DTO.EntregaDTO;
import Model.DTO.TurmaDTO;
import Model.Service.TurmaService;
import Model.util.Alerts;
import com.tgsync.tgsync.util.MudancaTelas;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TelaEntregasController extends MudancaTelas {

    @FXML
    private Button ButtonCadastrar;

    @FXML
    private TableColumn<EntregaDTO, Date> colunaDataEntregaTG;
    @FXML
    private TableColumn<EntregaDTO, String> colunaMatriculaTG;

    @FXML
    private TableColumn<EntregaDTO, String> colunaTituloTG;
    @FXML
    private TableColumn<EntregaDTO, String> colunaTipoTG;

    @FXML
    private TableView<EntregaDTO> tabelaEntregasTG1;

    @FXML
    private ComboBox<Integer> comboBoxTG;
    @FXML
    private ComboBox<String> comboBoxTipoTG;


    @FXML
    private DatePicker dateDataEntrega;

    @FXML
    private TextField textFieldTitulo;

    @FXML
    ObservableList<EntregaDTO> obsListEntregasTG1 = FXCollections.observableArrayList();
    @FXML
    ObservableList<Integer> observableListTG = FXCollections.observableArrayList();

    public void initialize() {
        try {
            updateTableTG();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void adicionarEntrega(ActionEvent event) {
        String titulo = textFieldTitulo.getText();
        LocalDate data = dateDataEntrega.getValue();
        Integer tg = comboBoxTG.getValue();
        String tipoTg = comboBoxTipoTG.getValue();
        LocalDate dataAtual = LocalDate.now();
        boolean verificador = true;
        if (titulo.equals("")) {
            Alerts.showAlert("Atenção!", "", "Não é possível cadastrar uma entrega com o título vazio.", Alert.AlertType.WARNING);
            verificador = false;
        } else if (data.isBefore(dataAtual)) {
            Alerts.showAlert("Atenção!", "", "Não é possível inserir uma data anterior a atual.", Alert.AlertType.WARNING);
            verificador = false;
        } else if (tipoTg.equals("")) {
            Alerts.showAlert("Atenção!", "", "É obrigatório selecionar um tipo de TG.", Alert.AlertType.WARNING);
            verificador = false;
        } else if (tipoTg.equals("Portfólio")) {
            if(tg == null){
                Alerts.showAlert("Atenção!", "", "É necessário selecionar o TG.", Alert.AlertType.WARNING);
                verificador = false;
            }
        }
        if (verificador == true){
            EntregaDAO entregaDAO = new EntregaDAO();
            TurmaDAO turmaDAO = new TurmaDAO();
            TurmaDTO turmaService = new TurmaDTO();

            try {
                turmaService = TurmaService.buscarTurmaComDataDoPC();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if(tg!=null) {
                turmaService.setDisciplina(tg);
            }else{
                turmaService.setDisciplina(1);
            }
            turmaService = TurmaDAO.getTurmaPorAtributo(turmaService);

            LocalDateTime localDateTime = data.atStartOfDay();
            Date date = java.util.Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            entregaDAO.addEntrega(new EntregaDTO(date, titulo, tipoTg), turmaService);
            try {
                updateTableTG();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            textFieldTitulo.setText("");
            dateDataEntrega.setValue(null);
            comboBoxTG.setValue(null);
            comboBoxTipoTG.setValue(null);
            Alerts.showAlert("SUCESSO!", "", "Entrega adicionada com sucesso", Alert.AlertType.CONFIRMATION);
        }

    }

    public void updateTableTG() throws ParseException {
        EntregaDAO entregaDAO = new EntregaDAO();
        TurmaDTO turmaDTOTG1 = TurmaService.buscarTurmaComDataDoPC();
        turmaDTOTG1.setDisciplina(1);
        turmaDTOTG1 = TurmaDAO.getTurmaPorAtributo(turmaDTOTG1);

        TurmaDTO turmaDTOTG2 = TurmaService.buscarTurmaComDataDoPC();
        turmaDTOTG2.setDisciplina(2);
        turmaDTOTG2 = TurmaDAO.getTurmaPorAtributo(turmaDTOTG2);


        obsListEntregasTG1.clear();
        tabelaEntregasTG1.setItems(null);

        List<EntregaDTO> listEntrega = entregaDAO.getEntregasPorAnoSemestre(turmaDTOTG1, turmaDTOTG2);
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoSaida = new SimpleDateFormat("dd/MM/yyyy");

        if (!listEntrega.isEmpty()) {
            for (EntregaDTO entrega : listEntrega) {
                obsListEntregasTG1.add(entrega);
            }
            for (EntregaDTO entregaDTO : listEntrega) {
                entregaDTO.getMatriculaTG();
            }
        }

        obsListEntregasTG1 = FXCollections.observableArrayList(obsListEntregasTG1);
        colunaTituloTG.setCellValueFactory(new PropertyValueFactory<>("tituloEntrega"));
        colunaDataEntregaTG.setCellValueFactory(new PropertyValueFactory<>("dataEntregaFormatada"));
        colunaTipoTG.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colunaMatriculaTG.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMatriculaTG()));
        tabelaEntregasTG1.setItems(obsListEntregasTG1);
    }

    @FXML
    void tableClickTG(MouseEvent event) {
        int i = tabelaEntregasTG1.getSelectionModel().getSelectedIndex();
        EntregaDTO entregaDTO = (EntregaDTO) tabelaEntregasTG1.getItems().get(i);
        openTelaEditarEntrega(entregaDTO);
    }

    public void openTelaEditarEntrega(EntregaDTO entregaDTO) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaEditarEntrega.fxml"));
            Parent root = loader.load();

            TelaEditarEntregaController editarEntregaController = loader.getController();
            editarEntregaController.setTelaEntregasController(this);
            editarEntregaController.setMessage(entregaDTO);

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

    public void updateTablesFromEditController() {
        try {
            updateTableTG();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void carregarMatriculaTG(){
        observableListTG.clear();
        comboBoxTG.setItems(null);
        String tipo = comboBoxTipoTG.getValue();
        if(tipo.equals("Portfólio")){
            observableListTG.add(1);
            observableListTG.add(2);
            comboBoxTG.setItems(observableListTG);
        }
    }
    }