package com.tgsync.tgsync;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import Model.DAO.OrientadorDAO;
import Model.DTO.OrientadorDTO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class TelaOrientadorController extends Application {
    private static Scene telaOrientador;

    // Configuração da Tela
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("TelaOrientador.fxml"));

        telaOrientador = new Scene(fxmlLoader.load(), 600, 400);

        stage.setTitle("TGSync");
        stage.setScene(telaOrientador);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-tgsync.png")));

        stage.show();
    }

    public static Scene getMainScene(){
        return telaOrientador;
    }
    public static void main(String[] args) {
        launch();
    }
    private static Scene abrirTelaAlunos;

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

    // TextField
    @FXML
    private TextField textFieldNomeOrientador;
    @FXML
    private TextField textFieldEmail;

    // ListView
    @FXML
    private ListView<String> listOrientadores;
    @FXML
    private ListView<String> listEmail;

    // Data Base
    private OrientadorDAO orientadorDAO;
    public void initialize() {
        orientadorDAO = new OrientadorDAO();

        // Carregar dados do banco de dados para as listas
        List<OrientadorDTO> orientadores = orientadorDAO.getAllOrientador();
        List<String> emails = new LinkedList<>();
        List<String> nomes = new LinkedList<>();
        for(OrientadorDTO orientadorDTO:orientadores){
            emails.add(orientadorDTO.getEmail());
            nomes.add(orientadorDTO.getNome());
        }

        listOrientadores.getItems().addAll(nomes);
        listEmail.getItems().addAll(emails);
    }
    @FXML
    private void adicionarOrientador(ActionEvent event) {
        String nome = textFieldNomeOrientador.getText();
        String email = textFieldEmail.getText();
        OrientadorDTO searchOrientador = orientadorDAO.getOrientadorPorEmail(email);
        if (searchOrientador != null) {

        }

        OrientadorDTO orientador = new OrientadorDTO(null, nome, email); // Use os valores dos campos de texto
        orientadorDAO.addOrientador(orientador);

        // Atualize as listas após adicionar um novo orientador
        List<OrientadorDTO> orientadores = orientadorDAO.getAllOrientador();
        List<String> emails = new LinkedList<>();
        List<String> nomes = new LinkedList<>();
        for(OrientadorDTO orientadorDTO:orientadores){
            emails.add(orientadorDTO.getEmail());
            nomes.add(orientadorDTO.getNome());
        }

        listOrientadores.getItems().clear();
        listEmail.getItems().clear();
        listOrientadores.getItems().addAll(nomes);
        listEmail.getItems().addAll(emails);

        // Limpe os campos de entrada após adicionar
        textFieldNomeOrientador.clear();
        textFieldEmail.clear();
    }
}