package com.tgsync.tgsync;

import Model.DAO.EntregaDAO;
import Model.DAO.NotaDAO;
import Model.DTO.AlunoDTO;
import Model.DTO.EntregaDTO;
import Model.DTO.NotaDTO;
import Model.DTO.TurmaDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.text.ParseException;
import java.util.List;

public class TelaFeedbackController {

    private TelaAlunosController telaAlunosController;
    public void injecaoDepFeedback(TelaAlunosController telaAlunosController){
        this.telaAlunosController = telaAlunosController;
    }

    @FXML
    private Button btnFeedbackSave;

    @FXML
    private TableColumn<EntregaDTO, String> colunaEntrega;

    @FXML
    private TableColumn<NotaDTO, String> colunaFeedback;

    @FXML
    private TableColumn<NotaDTO, Double> colunaNota;

    @FXML
    private ImageView imgLogo;

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private MenuItem sair;

    @FXML
    private TableView<NotaDTO> tabelaNotas;

    @FXML
    private MenuItem telaInicial;

    @FXML
    private MenuItem telaOrientadores;

    @FXML
    private MenuItem visualizarAlunos;

    @FXML
    ObservableList<NotaDTO> obsListaNotas = FXCollections.observableArrayList();
    private AlunoDTO aluno = new AlunoDTO();
    private TurmaDTO turma = new TurmaDTO();

    public void initialize(){
        try{
            updateEntregaNota();
        } catch (ParseException e){
            e.printStackTrace();
        }
    }

    @FXML
    void encerrarSistema(ActionEvent event) {

    }

    @FXML
    void onOrientadores(ActionEvent event) {

    }

    @FXML
    void onTelaInicial(ActionEvent event) {

    }

    @FXML
    void onVisualizarAlunosClicked(ActionEvent event) {

    }

    public void receberAluno(AlunoDTO aluno){
        this.aluno = aluno;
    }

    public void receberTurma(TurmaDTO turma){
        this.turma = turma;
    }
    public void updateEntregaNota() throws ParseException{
//        NotaDAO notaDAO = new NotaDAO();
        EntregaDAO entregaDAO = new EntregaDAO();

        obsListaNotas.clear();
        tabelaNotas.setItems(null);



//        NotaDTO notaDTO = notaDAO.getNotaPorId(1L);


        obsListaNotas = FXCollections.observableArrayList(obsListaNotas);
        tabelaNotas.setItems(obsListaNotas);
    }

}
