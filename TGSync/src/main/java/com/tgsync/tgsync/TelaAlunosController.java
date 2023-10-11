package com.tgsync.tgsync;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TelaAlunosController implements Initializable {

    //@Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("TelaAlunos.fxml"));

        Scene alunosScene = new Scene(fxmlLoader.load(), 1200, 800);

        stage.setTitle("TGSync");
        stage.setScene(alunosScene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-tgsync.png")));

        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public class TelaAlunos {

        @FXML
        private Button btnAlunos;

        @FXML
        private Button btnCadastro;

        @FXML
        private Button btnConfiguracoes;

        @FXML
        private Button btnEmailInstitucional;

        @FXML
        private Button btnEmailOrientador;

        @FXML
        private Button btnEmailPessoal;

        @FXML
        private Button btnNome;

        @FXML
        private Button btnNomeOrientador;

        @FXML
        private Button btnOK;

        @FXML
        private Button btnRelatorios;

        @FXML
        private ImageView imgLogo;

        @FXML
        private ListView<?> lvwEmailInstitucional;

        @FXML
        private ListView<?> lvwEmailOrientador;

        @FXML
        private ListView<?> lvwEmailPessoal;

        @FXML
        private ListView<?> lvwNome;

        @FXML
        private ListView<?> lvwNomeOrientador;

        @FXML
        private AnchorPane pnlPrincipal;

        @FXML
        private TextField txtAno;

        @FXML
        private TextField txtSemestre;

        @FXML
        private TextField txtTG;

    }
}