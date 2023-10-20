package com.tgsync.tgsync;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class TelaFeedbackController {

    @FXML
    private TableColumn<?, ?> colunaNome;

    @FXML
    private TableColumn<?, ?> colunaNome1;

    @FXML
    private TableColumn<?, ?> colunaProblema;

    @FXML
    private ImageView imgLogo;

    @FXML
    private Button onVoltar;

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private MenuItem sair;

    @FXML
    private TableView<?> tabelaAlunos;

    @FXML
    private MenuItem telaInicial;

    @FXML
    private MenuItem telaOrientadores;

    @FXML
    private MenuItem visualizarAlunos;

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

    @FXML
    void onVoltarMain(ActionEvent event) {

    }

}
