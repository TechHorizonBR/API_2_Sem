package com.tgsync.tgsync;

import Model.DTO.AlunoDTO;
import Model.util.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class TelaOrientadorController{

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
    private Button buttonVoltar;
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

    @FXML
    public void onVoltar(ActionEvent event){
        buttonVoltar.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        try {
                            loadView("telaMain.fxml");
                        } catch (IOException ex) {
                            Alerts.showAlert("ERRO","Erro","Erro ao tentar trocar tela", Alert.AlertType.ERROR);
                            throw new RuntimeException(ex);
                        }
                    }
                });
    }


    private void loadView(String absoluteName) throws IOException {

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(absoluteName));
        AnchorPane pane = loader.load();


        Scene mainScene = HelloApplication.getMainScene();
        VBox mainVBox = (VBox) mainScene.getRoot();
        mainVBox.getChildren().clear();
        mainVBox.getChildren().addAll(pane.getChildren());


    }
}