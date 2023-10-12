package com.tgsync.tgsync;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaMainController extends Application {

    @FXML
    private MenuItem visualizarAlunos;
    @FXML
    private MenuItem pendencias;
    // ... outros campos FXML ...

    @FXML
    private ImageView imgLogo;
    @FXML
    private Parent root;
    private Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("telaMain.fxml"));
            Parent root = loader.load();
            Scene mainScene = new Scene(root);

            primaryStage.setScene(mainScene);
            primaryStage.setTitle("TGSync");
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-tgsync.png")));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onVisualizarAlunosClicked() {
        System.out.println("trocando de tela");
    }
    @FXML
    private void onOrientadores() {
        System.out.println("trocando de tela orientadores");
    }

}
