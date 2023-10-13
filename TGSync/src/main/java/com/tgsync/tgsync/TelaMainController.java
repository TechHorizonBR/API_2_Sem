package com.tgsync.tgsync;

import Model.util.Alerts;
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

public class TelaMainController{

    @FXML
    private MenuItem visualizarAlunos;
    @FXML
    private MenuItem pendencias;
    // ... outros campos FXML ...

    @FXML
    private ImageView imgLogo;
    @FXML
    private Parent root;

    @FXML
    private void onVisualizarAlunosClicked() {
        System.out.println("Tela Alunos");
    }
    @FXML
    private void onOrientadores() {
        System.out.println("Tela Orientadores");
    }

    @FXML
    private void onTelaInicial(){
        System.out.println("Tela Inicial/Upload");
    }
}
