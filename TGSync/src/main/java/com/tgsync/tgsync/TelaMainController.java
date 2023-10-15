package com.tgsync.tgsync;


import Model.util.Alerts;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class TelaMainController{

    @FXML
    private MenuItem visualizarAlunos;
    @FXML
    private MenuItem pendencias;
    @FXML
    private MenuItem notasFeedbacks;
    @FXML
    private MenuItem orientadores;
    @FXML
    private MenuItem entregas;
    @FXML
    private MenuItem relatorioDefesa;
    @FXML
    private MenuItem relatorioEntrega;
    @FXML
    private MenuItem relatorioFechamento;
    @FXML
    private MenuItem relatorioFeedbacks;
    @FXML
    private MenuItem telaInicial;
    @FXML
    private MenuItem sair;

    @FXML
    private ImageView imgLogo;
    @FXML
    private Parent root;

    @FXML
    private void onVisualizarAlunosClicked() {
        System.out.println("Tela Alunos");
        visualizarAlunos.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        try {
                            loadView("TelaAlunos.fxml");
                        } catch (IOException ex) {
                            Alerts.showAlert("ERRO","Erro","Erro ao tentar trocar tela", Alert.AlertType.ERROR);
                            throw new RuntimeException(ex);
                        }
                    }
                });
    }
    @FXML
    private void onOrientadores() {
        System.out.println("Tela Orientadores");
        orientadores.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        try {
                            loadView("TelaOrientador.fxml");
                        } catch (IOException ex) {
                            Alerts.showAlert("ERRO","Erro","Erro ao tentar trocar tela", Alert.AlertType.ERROR);
                            throw new RuntimeException(ex);
                        }
                    }
                });
    }

    @FXML
    private void onTelaInicial(){
        System.out.println("Tela Inicial/Upload");
        telaInicial.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        /*try {
                            loadView("hello-view.fxml");
                        } catch (IOException ex) {
                            Alerts.showAlert("ERRO","Erro","Erro ao tentar trocar tela", Alert.AlertType.ERROR);
                            throw new RuntimeException(ex);
                        }*/
                    }
                });
    }
    @FXML
    private void encerrarSistema(ActionEvent event){
        Platform.exit();;
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
