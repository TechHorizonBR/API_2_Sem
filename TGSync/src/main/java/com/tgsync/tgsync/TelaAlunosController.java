package com.tgsync.tgsync;

import Model.DAO.AlunoDAO;
import Model.DAO.TGDAO;
import Model.DAO.TurmaDAO;
import Model.DTO.AlunoDTO;
import Model.DTO.OrientadorDTO;
import Model.DTO.TGDTO;
import Model.DTO.TurmaDTO;
import Model.util.Alerts;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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
    private Button btnRelatorios;
    @FXML
    private TableColumn<AlunoDTO, String> colunaNome;

    @FXML
    private TableColumn<AlunoDTO, String> colunaEmail;

    @FXML
    private TableColumn<AlunoDTO, String> colunaEmailFatec;

    @FXML
    private TableColumn<AlunoDTO, String> colunaEmailOrientador;


    @FXML
    private TableColumn<AlunoDTO, String> colunaNomeOrientador;

    @FXML
    private TableColumn<AlunoDTO, String> colunaTipoTG;

    @FXML
    private ImageView imgLogo;

    @FXML
    private Button onOkButton;

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private TableView<AlunoDTO> tabelaAlunos;

    @FXML
    private TextField txtAno;

    @FXML
    private TextField txtSemestre;

    @FXML
    private TextField txtTG;
    @FXML
    private TableColumn<AlunoDTO, String> colunaProblema;
    @FXML
    private TableColumn<AlunoDTO, String> colunaEmpresa;
    @FXML
    private TableColumn<AlunoDTO, String> colunaDiscplina;


    ObservableList<AlunoDTO> listAlunos = FXCollections.observableArrayList();



    @FXML
    void OnOkButton(ActionEvent event) {
        listAlunos.clear();
        tabelaAlunos.setItems(null);
        tabelaAlunos.setItems(listAlunos);
        AlunoDAO alunoDAO = new AlunoDAO();
        TurmaDAO turmaDAO = new TurmaDAO();
        TGDAO tgdao = new TGDAO();

        if (txtAno.getText().isEmpty() || txtSemestre.getText().isEmpty() || txtTG.getText().isEmpty()){
            Alerts.showAlert("Atenção", "", "Preenchimento de todos os campos é obrigatório", Alert.AlertType.WARNING);
        }else if(txtAno.getText().matches(".*[a-zA-Z].*")||txtTG.getText().matches(".*[a-zA-Z].*")||txtSemestre.getText().matches(".*[a-zA-Z].*")) {
            Alerts.showAlert("Atenção", "", "Os campos não aceita letras, apenas números!", Alert.AlertType.WARNING);
        }else{
            Integer ano = Integer.parseInt(txtAno.getText());
            Integer semestre = Integer.parseInt(txtSemestre.getText());
            Integer tg = Integer.parseInt(txtTG.getText());


            List<Long> listMatricula = new LinkedList<>();
            TurmaDTO turmaDTO = turmaDAO.getTurmaPorAtributo(new TurmaDTO(ano, semestre, tg));

            if (turmaDTO != null){
                listMatricula = alunoDAO.getAllMatriculaPorIdTurma(turmaDTO);

                if (!listMatricula.isEmpty()){
                    for (Long matricula: listMatricula){
                        listAlunos.add(alunoDAO.getAlunoPorId(matricula));
                    }
                    for (AlunoDTO aluno : listAlunos) {
                        aluno.getNomeOrientador();
                        aluno.getEmailOrientador();
                    }
                    listAlunos = FXCollections.observableArrayList(listAlunos);
                    colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
                    colunaEmail.setCellValueFactory(new PropertyValueFactory<>("emailPessoal"));
                    colunaEmailFatec.setCellValueFactory(new PropertyValueFactory<>("emailFatec"));
                    colunaNomeOrientador.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeOrientador()));
                    colunaEmailOrientador.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmailOrientador()));
                    colunaTipoTG.setCellValueFactory(cellData -> {
                        AlunoDTO aluno = cellData.getValue();
                        TGDTO tgDoAluno = tgdao.getTgPorIdAluno(aluno.getId());
                        if (tgDoAluno != null) {
                            return new SimpleStringProperty(tgDoAluno.getTipo());
                        } else {
                            return new SimpleStringProperty("");
                        }
                    });
                    colunaDiscplina.setCellValueFactory(cellData -> {
                        AlunoDTO aluno = cellData.getValue();
                        TGDTO tgdto = tgdao.getTgPorIdAluno(aluno.getId());
                        if(tgdto!=null){
                            return new SimpleStringProperty(tgdto.getDisciplina());
                        }else {
                            return new SimpleStringProperty("");
                        }
                    });
                    colunaEmpresa.setCellValueFactory(cellData -> {
                        AlunoDTO aluno = cellData.getValue();
                        TGDTO tgdto = tgdao.getTgPorIdAluno(aluno.getId());
                        if(tgdto!=null){
                            return new SimpleStringProperty(tgdto.getEmpresa());
                        }else {
                            return new SimpleStringProperty("");
                        }
                    });
                    colunaProblema.setCellValueFactory(cellData -> {
                        AlunoDTO aluno = cellData.getValue();
                        TGDTO tgdto = tgdao.getTgPorIdAluno(aluno.getId());
                        if(tgdto!=null){
                            return new SimpleStringProperty(tgdto.getProblema());
                        }else {
                            return new SimpleStringProperty("");
                        }
                    });


                    tabelaAlunos.setItems(listAlunos);
                }
            } else{
                Alerts.showAlert("Atenção!","", "Essa turma não existe!", Alert.AlertType.WARNING);
            }

        }



    }
    @FXML
    public void onVoltarMain(ActionEvent event){

    }
    public void initialize(URL url, ResourceBundle rb){
        
    }
}