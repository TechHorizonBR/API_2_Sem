package com.tgsync.tgsync;

import Model.DAO.SistemaDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;

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
                        }
                    }
                });

    }
    private void openFile(File file) {
        System.out.println("Caminho: "+ file.getAbsolutePath());
    }


    private static void configureFileChooser(final FileChooser fileChooser) {

        fileChooser.setTitle("Abrir CSV");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home"))
        );
    }
    @FXML
    protected void onViewAllDataButton(){
        System.out.println("Visualizar dados");
    }
}