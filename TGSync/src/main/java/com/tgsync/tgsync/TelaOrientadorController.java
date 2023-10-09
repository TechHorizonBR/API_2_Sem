package com.tgsync.tgsync;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaOrientadorController {

    // Buttons
    @FXML
    private Button ButtonAlunos;
    @FXML
    private void abrirTelaAlunos(ActionEvent event) {
        try {
            // Carregue o arquivo FXML da tela de alunos
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            // Crie uma nova janela (Stage)
            Stage alunosStage = new Stage();
            alunosStage.setTitle("Tela de Alunos");

            // Defina a cena da nova janela com o conteúdo do arquivo FXML carregado
            Scene scene = new Scene(root);
            alunosStage.setScene(scene);

            // Mostre a nova janela
            alunosStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button ButtonCadastro;
    @FXML
    private Button ButtonRelatorios;
    @FXML
    private Button ButtonConfiguracoes;
    @FXML
    private Button ButtonCadastrar;

    // TextField
    @FXML
    private TextField textFieldNomeOrientador;
    @FXML
    private TextField textFieldEmail;

    // ListView
    @FXML
    private ListView listOrientadorCadastrados;
    @FXML
    private ListView listEmail;
}
