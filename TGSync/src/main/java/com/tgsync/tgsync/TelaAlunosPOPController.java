package com.tgsync.tgsync;

import Model.DAO.*;
import Model.DTO.AlunoDTO;
import Model.DTO.OrientadorDTO;
import com.tgsync.tgsync.util.MudancaTelas;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

public class TelaAlunosPOPController extends MudancaTelas {
    TelaRelatorioCertificadoOrientadoresController telaRelatorioCertificadoOrientadoresController = new TelaRelatorioCertificadoOrientadoresController();
    @FXML
    private TableColumn<AlunoDTO, String> colunaNome;
    @FXML
    private TableColumn<AlunoDTO, String> colunaEmailFatec;
    @FXML
    private TableColumn<AlunoDTO, String> colunaEmailPessoal;
    @FXML
    private TableView<AlunoDTO> tabelaListaAlunos;
    private OrientadorDTO orientadorDTO = new OrientadorDTO();

    private TurmaDAO turmaDAO = new TurmaDAO();
    private OrientadorDAO orientadorDAO = new OrientadorDAO();
    private AlunoDAO alunoDAO = new AlunoDAO();
    private EntregaDAO entregaDAO = new EntregaDAO();
    private NotaDAO notaDAO = new NotaDAO();
    public void injecaoTela(TelaRelatorioCertificadoOrientadoresController telaRelatorioCertificadoOrientadoresController, OrientadorDTO orientadorDTO){
        this.telaRelatorioCertificadoOrientadoresController = telaRelatorioCertificadoOrientadoresController;
        this.orientadorDTO = orientadorDTO;
    }
    ObservableList<AlunoDTO> obsAluno = FXCollections.observableArrayList();

    public void initialize() {
        this.orientadorDTO = telaRelatorioCertificadoOrientadoresController.mandarDados();
        obsAluno.clear();
        tabelaListaAlunos.setItems(obsAluno);
        List<Long> idsAlunos = new LinkedList<>();

        if(this.orientadorDTO.getId() != null){
            idsAlunos = alunoDAO.getIdsAlunosPorIdOrientador(orientadorDTO.getId());
        }
        if (!idsAlunos.isEmpty()){
            for(Long idAluno : idsAlunos){
                obsAluno.add(alunoDAO.getAlunoPorId(idAluno));
            }

            colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colunaEmailPessoal.setCellValueFactory(new PropertyValueFactory<>("emailPessoal"));
            colunaEmailFatec.setCellValueFactory(new PropertyValueFactory<>("emailFatec"));
            tabelaListaAlunos.setItems(obsAluno);
        }
    }
}
