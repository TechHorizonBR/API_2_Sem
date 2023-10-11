package com.tgsync.tgsync;

import Model.DAO.AlunoDAO;
import Model.DAO.TurmaDAO;
import Model.DTO.AlunoDTO;
import Model.DTO.TurmaDTO;
import Model.util.Alerts;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class TelaAlunosController {

    @FXML
    private Button btnAlunos;

    @FXML
    private Button btnCadastro;

    @FXML
    private Button btnConfiguracoes;

    @FXML
    private Button btnEmailInstitucional;

    @FXML
    private Button btnEmailOrientador;

    @FXML
    private Button btnEmailPessoal;

    @FXML
    private Button btnNome;

    @FXML
    private Button btnNomeOrientador;

    @FXML
    private Button onOkButton;

    @FXML
    private Button btnRelatorios;

    @FXML
    private ImageView imgLogo;

    @FXML
    private ListView<?> lvwEmailInstitucional;

    @FXML
    private ListView<?> lvwEmailOrientador;

    @FXML
    private ListView<?> lvwEmailPessoal;

    @FXML
    private ListView<?> lvwNome;

    @FXML
    private ListView<?> lvwNomeOrientador;

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private TextField txtAno;

    @FXML
    private TextField txtSemestre;

    @FXML
    private TextField txtTG;

    public void OnOkButton(ActionEvent event) {
        Integer ano = Integer.parseInt(txtAno.getText());
        Integer semestre = Integer.parseInt(txtSemestre.getText());
        Integer tg = Integer.parseInt(txtTG.getText());

        TurmaDAO turmaDAO = new TurmaDAO();
        AlunoDAO alunoDAO = new AlunoDAO();

        List<Long> listMatricula = new LinkedList<>();
        List<AlunoDTO> listAlunos = new LinkedList<>();
        TurmaDTO turmaDTO = turmaDAO.getTurmaPorAtributo(new TurmaDTO(ano, semestre, tg));


        if (turmaDTO != null){
            listMatricula = alunoDAO.getAllMatriculaPorIdTurma(turmaDTO);

            if (!listMatricula.isEmpty()){
                for (Long matricula: listMatricula){
                    listAlunos.add(alunoDAO.getAlunoPorId(matricula));
                }
                System.out.println(listAlunos);
            }
        } else{
            Alerts.showAlert("Atenção!","", "Essa turma não existe!", Alert.AlertType.WARNING);
        }

    }
}