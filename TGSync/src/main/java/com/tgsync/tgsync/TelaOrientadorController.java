package com.tgsync.tgsync;

import Model.DTO.AlunoDTO;
import Model.util.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import Model.DAO.OrientadorDAO;
import Model.DTO.OrientadorDTO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

        telaOrientador = new Scene(fxmlLoader.load(), 900, 500);

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
    @FXML
    private ImageView imgLogo;

    // TextField
    @FXML
    private TextField textFieldNomeOrientador;
    @FXML
    private TextField textFieldEmail;

    // ListView
    @FXML
    private TableColumn<OrientadorDTO, String> colunaNome;

    @FXML
    private TableColumn<OrientadorDTO, String> colunaEmail;

    @FXML
    private TableView<OrientadorDTO> tabelaOrientadores;
    ObservableList<OrientadorDTO> orientadores = FXCollections.observableArrayList();
    // Data Base
    private OrientadorDAO orientadorDAO;
    public void initialize() {
        orientadorDAO = new OrientadorDAO();
        List<OrientadorDTO> listaOrientadores = orientadorDAO.getAllOrientador();
        if(!listaOrientadores.isEmpty()){
            for(OrientadorDTO orientadorDTO : listaOrientadores){
                orientadores.add(orientadorDTO);
            }
        }
        // Carregar dados do banco de dados para as listas

        orientadores = FXCollections.observableArrayList(orientadores);
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tabelaOrientadores.setItems(orientadores);
    }
    @FXML
    private void adicionarOrientador(ActionEvent event) {

        String nome = textFieldNomeOrientador.getText();
        String email = textFieldEmail.getText();
        OrientadorDTO searchOrientador = orientadorDAO.getOrientadorPorEmail(email);
        if (searchOrientador != null) {
            Alerts.showAlert("ERRO!","","O Orientador já existe.", Alert.AlertType.ERROR);
        } else if (email.equals("")||nome.equals("")) {
            Alerts.showAlert("ERRO!","","Preenchimento obrigatório.", Alert.AlertType.ERROR);
        } else {
            orientadores.clear();
            tabelaOrientadores.setItems(null);
            OrientadorDTO orientador = new OrientadorDTO(null, nome, email); // Use os valores dos campos de texto
            orientadorDAO.addOrientador(orientador);

            List<OrientadorDTO> listaOrientadores = orientadorDAO.getAllOrientador();
            if(!listaOrientadores.isEmpty()){
                for(OrientadorDTO orientadorDTO : listaOrientadores){
                    orientadores.add(orientadorDTO);
                }
            }
            // Carregar dados do banco de dados para as listas

            orientadores = FXCollections.observableArrayList(orientadores);
            colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            tabelaOrientadores.setItems(orientadores);

            // Limpe os campos de entrada após adicionar
            textFieldNomeOrientador.clear();
            textFieldEmail.clear();

            Alerts.showAlert("SUCESSO!","","Orientador cadastrado com sucesso.", Alert.AlertType.CONFIRMATION);

        }


    }
}