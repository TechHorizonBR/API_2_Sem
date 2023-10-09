package com.tgsync.tgsync;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class TelaOrientadorController {

    // Buttons
    @FXML
    private Button ButtonAlunos;
    @FXML
    private Button ButtonCadastro;
    @FXML
    private Button ButtonRelatorios;
    @FXML
    private Button ButtonConfiguracoes;
    @FXML
    private Button ButtonCadastrar;

    // LabelText
    @FXML
    private Label labelNomeOrientador;
    @FXML
    private Label labelEmail;

    // ListView
    @FXML
    private ListView listOrientadorCadastrados;
    @FXML
    private ListView listEmail;
}
