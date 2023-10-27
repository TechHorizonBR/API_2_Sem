package com.tgsync.tgsync;

import Model.DAO.EntregaDAO;
import Model.DAO.TurmaDAO;
import Model.DTO.EntregaDTO;
import Model.DTO.TurmaDTO;
import Model.Service.TurmaService;
import Model.util.Alerts;
import com.tgsync.tgsync.util.MudancaTelas;
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

    @FXML
    ObservableList<EntregaDTO> obsListEntregasTG2 = FXCollections.observableArrayList();

    public void initialize() {
        try {
            updateTableTG1();
            updateTableTG2();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void adicionarEntrega(ActionEvent event) {
        String titulo = textFieldTitulo.getText();
        LocalDate data = dateDataEntrega.getValue();
        Integer tg = comboBoxTG.getValue();
        LocalDate dataAtual = LocalDate.now();
        if (tg == null) {
            Alerts.showAlert("Atenção!", "", "É necessário selecionar o TG.", Alert.AlertType.WARNING);
        } else if (titulo.equals("")) {
            Alerts.showAlert("Atenção!", "", "Não é possível cadastrar uma entrega com o título vazio.", Alert.AlertType.WARNING);
        } else if (data.isBefore(dataAtual)) {
            Alerts.showAlert("Atenção!", "", "Não é possível inserir uma data anterior a atual", Alert.AlertType.WARNING);
        } else {
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
            entregaDAO.addEntrega(new EntregaDTO(date, titulo), turmaService);
            try {
                updateTableTG1();
                updateTableTG2();
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

        if (!listEntrega.isEmpty()) {
            for (EntregaDTO entrega : listEntrega) {
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
            updateTableTG1();
            updateTableTG2();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTableTG2() throws ParseException {
        EntregaDAO entregaDAO = new EntregaDAO();
        TurmaDTO turmaDTO = TurmaService.buscarTurmaComDataDoPC();
        turmaDTO.setDisciplina(2); // Substitua '2' pelo valor apropriado para TG2
        turmaDTO = TurmaDAO.getTurmaPorAtributo(turmaDTO);

        obsListEntregasTG2.clear();
        tabelaEntregasTG2.setItems(null);

        List<EntregaDTO> listEntrega = entregaDAO.getEntregasPorIdTurma(turmaDTO);
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoSaida = new SimpleDateFormat("dd/MM/yyyy");

        if (!listEntrega.isEmpty()) {
            for (EntregaDTO entrega : listEntrega) {
                obsListEntregasTG2.add(entrega);
            }
        }

        obsListEntregasTG2 = FXCollections.observableArrayList(obsListEntregasTG2);
        colunaTituloTG2.setCellValueFactory(new PropertyValueFactory<>("tituloEntrega"));
        colunaDataEntregaTG2.setCellValueFactory(new PropertyValueFactory<>("dataEntregaFormatada"));
        tabelaEntregasTG2.setItems(obsListEntregasTG2);
    }

    @FXML
    void tableClickTG2(MouseEvent event) {
        int i = tabelaEntregasTG2.getSelectionModel().getSelectedIndex();
        EntregaDTO entregaDTO = (EntregaDTO) tabelaEntregasTG2.getItems().get(i);
        openTelaEditarEntregaTG2(entregaDTO);
    }

    public void openTelaEditarEntregaTG2(EntregaDTO entregaDTO) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaEditarEntrega.fxml"));
            Parent root = loader.load();

            TelaEditarEntregaController editarEntregaController = loader.getController();
            editarEntregaController.setTelaEntregasController(this);
            editarEntregaController.setMessage(entregaDTO);

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Editar Entrega para TG2");
            Scene scene = new Scene(root);
            popupStage.setScene(scene);

            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }


        }


    }