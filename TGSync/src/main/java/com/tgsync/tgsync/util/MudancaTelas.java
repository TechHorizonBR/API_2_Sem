package com.tgsync.tgsync.util;

import Model.util.Alerts;
import com.tgsync.tgsync.HelloApplication;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MudancaTelas {
    @FXML
    public MenuItem visualizarAlunos;
    @FXML
    public MenuItem telaOrientadores;
    @FXML
    public MenuItem entregas;
    @FXML
    public MenuItem relatorioDefesa;
    @FXML
    public MenuItem relatorioEntrega;
    @FXML
    public MenuItem relatorioFechamento;
    @FXML
    public MenuItem relatorioFeedbacks;
    @FXML
    public MenuItem telaInicial;
    @FXML
    public MenuItem sair;
    @FXML
    public ComboBox<Integer> comboBoxTG;
    @FXML
    public ComboBox<String> comboBoxTipoTG;
    @FXML
    ObservableList<Integer> observableListTG = FXCollections.observableArrayList();
    @FXML
    void carregarMatriculaTG() {
        observableListTG.clear();
        comboBoxTG.setItems(null);
        String tipo = "";
        if (comboBoxTipoTG != null) {
            tipo = comboBoxTipoTG.getValue();
        }
        if ("Portfólio".equals(tipo)) { // Verifique se o valor é igual a "Portfólio"
            observableListTG.add(1);
            observableListTG.add(2);
            comboBoxTG.setItems(observableListTG);
        }
    }

    @FXML
    public void onVisualizarAlunosClicked() {
        try {
            loadView("TelaAlunos.fxml");
        } catch (IOException ex) {
            Alerts.showAlert("ERRO","Erro","Erro ao tentar trocar tela", Alert.AlertType.ERROR);
            throw new RuntimeException(ex);
        }
    }

    public void onNotasFeedbacksClick(ActionEvent event){
        try{
            loadView("TelaFeedback.fxml");
        } catch (IOException ex){
            Alerts.showAlert("ERRO","Erro","Erro ao tentar trocar tela", Alert.AlertType.ERROR);
            throw new RuntimeException(ex);
        }
    }
    @FXML
    public void onOrientadores(ActionEvent event) {
        try {
            loadView("TelaOrientador.fxml");
        } catch (IOException ex) {
            Alerts.showAlert("ERRO","Erro","Erro ao tentar trocar tela", Alert.AlertType.ERROR);
            throw new RuntimeException(ex);
        }
    }
    @FXML
    public void onTelaInicial(ActionEvent event){
        try {
            loadView("telaMain.fxml");
        } catch (IOException ex) {
            Alerts.showAlert("ERRO","Erro","Erro ao tentar trocar tela", Alert.AlertType.ERROR);
            throw new RuntimeException(ex);
        }
    }
    @FXML
    public void onAlunosAptosClick(ActionEvent event){

        try{
            loadView("TelaRelatorioAlunoApto.fxml");
        }catch (IOException e){
            Alerts.showAlert("Erro", "", "Erro ao exibir tela", Alert.AlertType.ERROR);
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void encerrarSistema(ActionEvent event){
        Platform.exit();
    }
    @FXML
    public void telaEntregas(ActionEvent event){
        try{
            loadView("telaEntregas.fxml");
        }catch (IOException e){
            Alerts.showAlert("ERRO","Erro","Erro ao tentar trocar tela", Alert.AlertType.ERROR);
            throw new RuntimeException(e);
        }
    }
    public void loadView(String absoluteName) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(absoluteName));
        AnchorPane pane = loader.load();
        Scene mainScene = HelloApplication.getMainScene();
        VBox mainVBox = (VBox) mainScene.getRoot();
        mainVBox.getChildren().clear();
        mainVBox.getChildren().addAll(pane.getChildren());
    }

}
