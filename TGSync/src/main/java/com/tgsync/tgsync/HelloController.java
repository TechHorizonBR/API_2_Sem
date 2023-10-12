package com.tgsync.tgsync;

import Model.DAO.SistemaDAO;
import Model.Service.AlunoService;
import Model.Service.TgService;
import Model.Service.TurmaService;
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
import java.text.ParseException;
import java.util.zip.DataFormatException;

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
                        try{
                            if(TurmaService.registrarTurma()){
                                configureFileChooser(fileChooser);
                                File file = fileChooser.showOpenDialog(mainScene.getWindow());
                                if (file != null) {
                                    openFile(file);
                                    AlunoService.registrarAluno(file.getAbsolutePath(), TurmaService.buscarTurmaComDataDoPC());
                                    TgService.registrarTg(file.getAbsolutePath());
                                    Alerts.showAlert("Sucesso!", "", "Upload realizado com sucesso!", Alert.AlertType.CONFIRMATION);
                                }
                            }else{
                                Alerts.showAlert("ATENÇÃO!!", "", "Não é possível realizar duas vezes o upload no mesmo semestre/ano.", Alert.AlertType.WARNING);
                            }
                        } catch (ParseException ex) {
                            Alerts.showAlert("ATENÇÃO!", "", "Alguma coisa não ocorreu bem! Entre em contato com o seu administrador.", Alert.AlertType.WARNING);
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
                            loadView("TelaAlunos.fxml");
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