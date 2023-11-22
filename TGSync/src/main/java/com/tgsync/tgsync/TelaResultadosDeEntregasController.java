package com.tgsync.tgsync;

import Model.DTO.AlunoDTO;
import com.tgsync.tgsync.util.MudancaTelas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;

public class TelaResultadosDeEntregasController extends MudancaTelas {

    @FXML
    private TableColumn<AlunoDTO, String> colunaEntrega;
    private TableColumn<AlunoDTO, String> colunaFeedback;

    @FXML
    private ImageView imgLogo;

    ObservableList<AlunoDTO> listEntrega = FXCollections.observableArrayList();



}
