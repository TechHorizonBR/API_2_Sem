package com.tgsync.tgsync;

import Model.DAO.EntregaDAO;
import Model.DAO.NotaDAO;
import Model.DAO.TGDAO;
import Model.DTO.*;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.ParseException;
import java.util.List;

public class TelaResultadosDeEntregasController extends MudancaTelas {

    private TelaAcompanhamentoDeEntregasController telaAcompanhamentoDeEntregasController;
    public void injecaoEntregasAluno(TelaAcompanhamentoDeEntregasController telaAcompanhamentoDeEntregasController){
        this.telaAcompanhamentoDeEntregasController = telaAcompanhamentoDeEntregasController;
    }
    @FXML
    private TableColumn<EntregaDTO, String> colunaEntrega;

    @FXML
    private TableColumn<EntregaDTO, String> colunaNota;

    @FXML
    private TableColumn<EntregaDTO, String> colunaFeedback;

    @FXML
    private ImageView imgLogo;

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private Text txtNome;

    @FXML
    private Text txtTipo;

    @FXML
    private TableView<EntregaDTO> tabelaNotas;


    ObservableList<EntregaDTO> obsEntrega = FXCollections.observableArrayList();

    private AlunoDTO aluno = new AlunoDTO();
    private TurmaDTO turma = new TurmaDTO();
    private TGDTO tgdto = new TGDTO();

    @FXML
    void onVisualizarAlunosClicked(ActionEvent event) {

    }


    public void receberDados(AlunoDTO aluno, TurmaDTO turma) {

        this.aluno = aluno;
        this.turma = turma;
    }}