package com.tgsync.tgsync;

import Model.DAO.AlunoDAO;
import Model.DAO.TGDAO;
import Model.DAO.TurmaDAO;
import Model.DTO.*;
import Model.Service.TurmaService;
import Model.util.Alerts;
import com.tgsync.tgsync.util.MudancaTelas;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class TelaAcompanhamentoDeEntregasController extends MudancaTelas {

    @FXML
    private TableColumn<AlunoDTO, String> colunaNome;
    @FXML
    private ImageView imgLogo;
    @FXML
    private Button onOkButton;
    @FXML
    private AnchorPane pnlPrincipal;
    @FXML
    private TableView<AlunoDTO> tabelaAlunos;
    @FXML
    private ComboBox<Integer> tgCombo;
    @FXML
    private ComboBox<String> tipoCombo;
    ObservableList<AlunoDTO> listAlunos = FXCollections.observableArrayList();
    ObservableList<Integer> listTG = FXCollections.observableArrayList();

    @FXML
    void OnOkButton(ActionEvent event) throws ParseException {
        listAlunos.clear();
        tabelaAlunos.setItems(null);
        tabelaAlunos.setItems(listAlunos);
        AlunoDAO alunoDAO = new AlunoDAO();
        Integer tg = tgCombo.getValue();
        String tipoTg = tipoCombo.getValue();
        if (tipoTg == null) {
            Alerts.showAlert("Atenção", "", "Preenchimento de todos os campos é obrigatório", Alert.AlertType.WARNING);
        } else {

            TurmaDTO turmaDTO1 = TurmaService.buscarTurmaComDataDoPC();
            TurmaDTO turmaDTO2 = TurmaService.buscarTurmaComDataDoPC();
            if (tg == null) {
                turmaDTO1.setDisciplina(1);
                turmaDTO2.setDisciplina(2);
            } else if (tg == 1) {
                turmaDTO1.setDisciplina(1);
            } else if (tg == 2) {
                turmaDTO1.setDisciplina(2);
            }
            turmaDTO1 = TurmaDAO.getTurmaPorAtributo(turmaDTO1);
            turmaDTO2 = TurmaDAO.getTurmaPorAtributo(turmaDTO2);
            List<Long> listMatriculas = new LinkedList<>();
            List<Long> listMatriculas2 = new LinkedList<>();
            if (turmaDTO1 != null || turmaDTO2 != null) {
                if (turmaDTO1 != null) {
                    listMatriculas = alunoDAO.getAllMatriculaPorIdTipoeIdTurma(tipoTg, turmaDTO1);
                }
                if (turmaDTO2 != null) {
                    listMatriculas2 = alunoDAO.getAllMatriculaPorIdTipoeIdTurma(tipoTg, turmaDTO2);
                    for (Long matricula : listMatriculas2) {
                        listMatriculas.add(matricula);
                    }
                }
                for (Long matricula : listMatriculas) {
                    listAlunos.add(alunoDAO.getAlunoPorId(matricula));
                }
                colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
                tabelaAlunos.setItems(listAlunos);
            } else {
                Alerts.showAlert("Atenção", "", "Essa turma não existe", Alert.AlertType.WARNING);
            }
        }
    }

    @FXML
    void carregarTipoTG(ActionEvent event) {
        listTG.clear();
        tgCombo.setItems(null);
        String tipo = tipoCombo.getValue();
        if (tipo.equals("Portfólio")) {
            listTG.add(1);
            listTG.add(2);
            tgCombo.setItems(listTG);
            tgCombo.setValue(listTG.get(0));
        }
    }
    TurmaDTO turmaMandarDados = new TurmaDTO();
    TGDTO tgdtoMandarDados = new TGDTO();
    AlunoDTO alunoDTODados = new AlunoDTO();

    @FXML
    void onTableClickEntrega(MouseEvent event) throws ParseException {
        int i = tabelaAlunos.getSelectionModel().getSelectedIndex();
        AlunoDTO alunoDTO = tabelaAlunos.getItems().get(i);
        TurmaDTO turmaDTO = TurmaService.buscarTurmaComDataDoPC();
        if (tipoCombo.getValue().contains("Artigo") || tipoCombo.getValue().contains("Relatório")){
            turmaDTO.setDisciplina(1);
        }
        else {
            turmaDTO.setDisciplina(tgCombo.getValue());
        }

        turmaDTO = TurmaDAO.getTurmaPorAtributo(turmaDTO);

        if (turmaDTO != null) {
            this.turmaMandarDados = turmaDTO;
            TGDTO tgdto = new TGDTO();
            tgdto.setTipo(tipoCombo.getValue());
            this.tgdtoMandarDados = tgdto;
            this.alunoDTODados = alunoDTO;
            openTelaEntregasAluno(this.alunoDTODados, turmaMandarDados, tgdtoMandarDados); // Ajuste para passar apenas aluno e turma
        } else {
            Alerts.showAlert("Atenção!", "", "Alguma coisa ocorreu errado.", Alert.AlertType.WARNING);
        }
    }

    public TurmaDTO mandarDados(){
        return this.turmaMandarDados;
    }
    public TGDTO getTgdtoMandarDados(){
        return this.tgdtoMandarDados;
    }
    public AlunoDTO getAlunoDTO(){
        return this.alunoDTODados;
    }

    public void openTelaEntregasAluno(AlunoDTO alunoDTO, TurmaDTO turmaDTO, TGDTO tgdto) {
        try {
            this.turmaMandarDados = turmaDTO;
            this.tgdtoMandarDados = tgdto;
            this.alunoDTODados = alunoDTO;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaResultadosDeEntregas.fxml"));
            Parent root = loader.load();

            TelaResultadosDeEntregasController telaResultadosDeEntregasController = loader.getController();
            telaResultadosDeEntregasController.injecaoEntregasAluno(this, turmaDTO, tgdto, alunoDTO);
            telaResultadosDeEntregasController.initialize();

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("TGSync");
            Scene scene = new Scene(root);
            popupStage.setScene(scene);

            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    }

