package com.tgsync.tgsync;

import Model.DAO.AlunoDAO;
import Model.DAO.TurmaDAO;
import Model.DTO.AlunoDTO;
import Model.DTO.OrientadorDTO;
import Model.DTO.TGDTO;
import Model.DTO.TurmaDTO;
import Model.util.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.util.LinkedList;
import java.util.List;

public class TelaAlunosController {

    private TurmaDAO turmaDAO;


    private AlunoDAO alunoDAODep;
    public void TelaAlunosController(AlunoDAO alunoDAO, TurmaDAO turmaDAO){
        this.turmaDAO = turmaDAO;
        this.alunoDAODep = alunoDAO;
    }

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
    private AnchorPane pnlPrincipal;

    @FXML
    private TableView<AlunoDTO> tabelaAlunos;

    @FXML
    private TableColumn<AlunoDTO, String> colunaEmail;

    @FXML
    private TableColumn<AlunoDTO, String> colunaEmailFatec;

    @FXML
    private TableColumn<OrientadorDTO, String> colunaEmailOrientador;

    @FXML
    private TableColumn<AlunoDTO, String> colunaNome;

    @FXML
    private TableColumn<OrientadorDTO, String> colunaNomeOrientador;

    @FXML
    private TableColumn<TGDTO, String> colunaTipoTG;

    @FXML
    private TextField txtAno;

    @FXML
    private TextField txtSemestre;

    @FXML
    private TextField txtTG;

    public void OnOkButton(ActionEvent event) {
        if (alunoDAODep == null) {
            throw new IllegalStateException("Service is null");
        }

        if (txtAno.getText().equals("") || txtSemestre.getText().equals("") || txtTG.getText().equals("")){
            Alerts.showAlert("Atenção", "", "Preenchimento de todos os campos é obrigatório", Alert.AlertType.WARNING);
        }else{

            Integer ano = Integer.parseInt(txtAno.getText());
            Integer semestre = Integer.parseInt(txtSemestre.getText());
            Integer tg = Integer.parseInt(txtTG.getText());

            List<Long> listMatricula = new LinkedList<>();
            List<AlunoDTO> listAlunos = new LinkedList<>();
            TurmaDTO turmaDTO = turmaDAO.getTurmaPorAtributo(new TurmaDTO(ano, semestre, tg));


            if (turmaDTO != null){
                listMatricula = alunoDAODep.getAllMatriculaPorIdTurma(turmaDTO);

                if (!listMatricula.isEmpty()){
                    for (Long matricula: listMatricula){
                        listAlunos.add(alunoDAODep.getAlunoPorId(matricula));
                    }
                    obsAluno = FXCollections.observableArrayList(listAlunos);
                    colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
                    tabelaAlunos.setItems(obsAluno);
                }
            } else{
                Alerts.showAlert("Atenção!","", "Essa turma não existe!", Alert.AlertType.WARNING);
            }

        }

    }

    private ObservableList<AlunoDTO> obsAluno;
    private ObservableList<OrientadorDTO> obsOrientador;
    private ObservableList<TGDTO> obsTG;

}