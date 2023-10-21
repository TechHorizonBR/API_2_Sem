package com.tgsync.tgsync;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.Date;

public class EditarEntregaController {

    @FXML
    private Button ButtonCadastrar;

    @FXML
    private TextField FieldTitulo;

    @FXML
    private DatePicker campoData;


    @FXML
    private Text txtId;

    @FXML
    private Text txtTg;


    @FXML
    public void setMessage(String text, Long id, Integer tg){
        FieldTitulo.setText(text);
        txtId.setText(id.toString());
        txtTg.setText(tg.toString());
    }
}
