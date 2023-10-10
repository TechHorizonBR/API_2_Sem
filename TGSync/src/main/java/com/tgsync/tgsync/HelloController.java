package com.tgsync.tgsync;

import Model.DAO.SistemaDAO;
import Model.Service.TgService;
import Model.util.Alerts;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

//import static jdk.jfr.consumer.EventStream.openFile;

public class HelloController {

    @FXML
    private Button onOpenCSVButton;

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
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Arquivo CSV", "*.csv*")
        );

    }
    @FXML
    protected void onViewAllDataButton(){
        System.out.println("Visualizar dados");
    }


    private void loadView(String absoluteName) {


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            AnchorPane newAnchorPane = loader.load();
            Scene mainScene = HelloApplication.getMainScene();

        }
        catch (IOException e){
            Alerts.showAlert("IO Exeception","Erro carregar página", e.getMessage(), Alert.AlertType.ERROR);

        }
    }
}