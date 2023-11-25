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

public class TelaFeedbackController extends MudancaTelas {

    private TelaAlunosController telaAlunosController;
    public void injecaoDepFeedback(TelaAlunosController telaAlunosController){
        this.telaAlunosController = telaAlunosController;
    }

    @FXML
    private Button btnFeedbackSave;

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

    @FXML
    void onCellClick(MouseEvent event) {
        int i = tabelaNotas.getSelectionModel().getSelectedIndex();
        EntregaDTO entrega = tabelaNotas.getItems().get(i);
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaEditarNotas.fxml"));
            Parent root = loader.load();

            TelaEditarNotaController telaEditarNotaController = loader.getController();
            telaEditarNotaController.injecaoDepFeedback(this);
            telaEditarNotaController.receberDados(aluno, turma, entrega, tgdto);

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("TGSync");
            Scene scene = new Scene(root);
            popupStage.setScene(scene);

            popupStage.showAndWait();

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    ObservableList<EntregaDTO> obsEntrega = FXCollections.observableArrayList();

    private AlunoDTO aluno = new AlunoDTO();
    private TurmaDTO turma = new TurmaDTO();
    private TGDTO tgdto = new TGDTO();

    @FXML
    void onVisualizarAlunosClicked(ActionEvent event) {

    }


    public void receberDados(AlunoDTO aluno, TurmaDTO turma, TGDTO tgdto){

        this.aluno = aluno;
        this.turma = turma;
        this.tgdto = tgdto;

        try{
            updateEntregaNota();
        } catch (ParseException e){
            e.printStackTrace();
        }
    }
    public void updateEntregaNota() throws ParseException{
        NotaDTO notaDTO = new NotaDTO();
        EntregaDAO entregaDAO = new EntregaDAO();

        obsEntrega.clear();
        tabelaNotas.setItems(null);
        List<EntregaDTO> listaEntrega = entregaDAO.getEntregasPorIdTurmaTipoTG(turma, tgdto );
        txtNome.setText(aluno.getNome());
        txtTipo.setText(tgdto.getTipo());

        for(EntregaDTO entrega : listaEntrega){
            obsEntrega.add(entrega);
        }

        colunaEntrega.setCellValueFactory(new PropertyValueFactory<>("tituloEntrega"));
        colunaNota.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNotaAlunos(aluno.getId())));
        colunaFeedback.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFeedbackAlunos(aluno.getId())));

        obsEntrega = FXCollections.observableArrayList(obsEntrega);
        tabelaNotas.setItems(obsEntrega);
    }
}
