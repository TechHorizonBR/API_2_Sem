package com.tgsync.tgsync;

import Model.DAO.EntregaDAO;
import Model.DAO.NotaDAO;
import Model.DAO.OrientadorDAO;
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

    private TelaAcompanhamentoDeEntregasController telaAcompanhamentoDeEntregasController = new TelaAcompanhamentoDeEntregasController();

    public void injecaoEntregasAluno(TelaAcompanhamentoDeEntregasController telaAcompanhamentoDeEntregasController, TurmaDTO turmaDTO) {
        this.telaAcompanhamentoDeEntregasController = telaAcompanhamentoDeEntregasController;
        this.turma = turmaDTO;
    }

    @FXML
    private TableColumn<EntregaDTO, String> colunaEntrega;

    @FXML
    private TableColumn<EntregaDTO, String> colunaStatus;

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

    public void initialize(){
        this.turma = telaAcompanhamentoDeEntregasController.mandarDados();
        NotaDTO notaDTO = new NotaDTO();
        EntregaDAO entregaDAO = new EntregaDAO();

        obsEntrega.clear();
        tabelaNotas.setItems(null);
        List<EntregaDTO> listaEntrega = entregaDAO.getEntregasPorIdTurmaTipoTG(turma, tgdto);
        txtNome.setText(aluno.getNome());
        txtTipo.setText(tgdto.getTipo());

        for (EntregaDTO entrega : listaEntrega) {
            obsEntrega.add(entrega);
        }

        colunaEntrega.setCellValueFactory(new PropertyValueFactory<>("tituloEntrega"));
        colunaStatus.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getStatus(aluno.getId())));




        //System.out.println(obsListaNotas);
        obsEntrega = FXCollections.observableArrayList(obsEntrega);
        System.out.println(obsEntrega);
        tabelaNotas.setItems(obsEntrega);
    }
}