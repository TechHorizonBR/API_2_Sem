package com.tgsync.tgsync;

import Model.DAO.SistemaDAO;
import Model.Service.TgService;
import Model.util.Alerts;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

//import static jdk.jfr.consumer.EventStream.openFile;

public class HelloController {

    @FXML
    private Button onOpenCSVButton;

    @FXML
    private Button onViewAllDataButton;
    @FXML
    private VBox vBox;

    final FileChooser fileChooser = new FileChooser();



    @FXML
    protected void onOpenCSVButton() {
        Scene mainScene = HelloApplication.getMainScene();
        onOpenCSVButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        configureFileChooser(fileChooser);
                        File file = fileChooser.showOpenDialog(mainScene.getWindow());
                        if (file != null) {
                            openFile(file);
                            TgService.registrarTg(file.getAbsolutePath());
                        }
                    }
                });


    }
    private void openFile(File file) {
        System.out.println("Caminho: "+ file.getAbsolutePath());
    }


    private static void configureFileChooser(final FileChooser fileChooser) {

        fileChooser.setTitle("Abrir CSV");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Arquivo CSV", "*.csv*"));
    }
    @FXML
    protected void onViewAllDataButton(){
        onViewAllDataButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        try {
                            loadView("seeAllSample.fxml");
                        } catch (IOException ex) {
                            Alerts.showAlert("ERRO","Erro","Erro ao tentar trocar tela", Alert.AlertType.ERROR);
                            throw new RuntimeException(ex);
                        }
                    }
                });
    }


    private void loadView(String absoluteName) throws IOException {

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(absoluteName));
        VBox newVBox = loader.load();


        Scene mainScene = HelloApplication.getMainScene();
        VBox mainVBox = (VBox) mainScene.getRoot();
        mainVBox.getChildren().clear();
                mainVBox.getChildren().addAll(newVBox.getChildren());


    }
}