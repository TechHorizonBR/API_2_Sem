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
        if (tipoTg == null){
            Alerts.showAlert("Atenção", "", "Preenchimento de todos os campos é obrigatório", Alert.AlertType.WARNING);
        }else{

            TurmaDTO turmaDTO1 = TurmaService.buscarTurmaComDataDoPC();
            TurmaDTO turmaDTO2 = TurmaService.buscarTurmaComDataDoPC();
            if(tg == null){
                turmaDTO1.setDisciplina(1);
                turmaDTO2.setDisciplina(2);
            }else if(tg == 1){
                turmaDTO1.setDisciplina(1);
            }else if(tg == 2){
                turmaDTO1.setDisciplina(2);
            }
            turmaDTO1 = TurmaDAO.getTurmaPorAtributo(turmaDTO1);
            turmaDTO2 = TurmaDAO.getTurmaPorAtributo(turmaDTO2);
            List<Long> listMatriculas = new LinkedList<>();
            List<Long> listMatriculas2 = new LinkedList<>();
            if (turmaDTO1 != null || turmaDTO2 != null){
                if(turmaDTO1 != null){
                    listMatriculas = alunoDAO.getAllMatriculaPorIdTipoeIdTurma(tipoTg, turmaDTO1);
                }
                if(turmaDTO2 != null){
                    listMatriculas2 = alunoDAO.getAllMatriculaPorIdTipoeIdTurma(tipoTg, turmaDTO2);
                    for (Long matricula: listMatriculas2){
                        listMatriculas.add(matricula);
                    }
                }
                for (Long matricula: listMatriculas){
                    listAlunos.add(alunoDAO.getAlunoPorId(matricula));
                }
                colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
                tabelaAlunos.setItems(listAlunos);
            }else{
                Alerts.showAlert("Atenção", "", "Essa turma não existe", Alert.AlertType.WARNING);
            }
        }
    }

    @FXML
    void carregarTipoTG(ActionEvent event) {
        listTG.clear();
        tgCombo.setItems(null);
        String tipo = tipoCombo.getValue();
        if(tipo.equals("Portfólio")){
            listTG.add(1);
            listTG.add(2);
            tgCombo.setItems(listTG);
            tgCombo.setValue(listTG.get(0));
        }
    }



}