package com.tgsync.tgsync;

import Model.DAO.EntregaDAO;
import Model.DAO.TurmaDAO;
import Model.DTO.AlunoDTO;
import Model.DTO.EntregaDTO;
import Model.DTO.TGDTO;
import Model.DTO.TurmaDTO;
import com.tgsync.tgsync.util.MudancaTelas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TelaRelatorioAlunoAptoController extends MudancaTelas implements Initializable {

    @FXML
    public ComboBox<Integer> anoComboBox;
    @FXML
    public ComboBox<Integer> semestreComboBox;
    @FXML
    public Button gerarCsvButton;
    private ObservableList<Integer> obsAno = FXCollections.observableArrayList();

    private EntregaDAO entregaDAO = new EntregaDAO();

    public void encontraAlunoApto(ActionEvent event){

        TurmaDTO turmaDTO1= TurmaDAO.getTurmaPorAtributo(new TurmaDTO(anoComboBox.getValue(),semestreComboBox.getValue(),1));
        TurmaDTO turmaDTO2= TurmaDAO.getTurmaPorAtributo(new TurmaDTO(anoComboBox.getValue(),semestreComboBox.getValue(),2));

        TGDTO tgdto = new TGDTO();
        tgdto.setTipo("Portfólio");

        List<EntregaDTO> listEntregas = entregaDAO.getEntregasPorIdTurmaTipoTG(turmaDTO1,tgdto);
        List<EntregaDTO> listAuxiliar = entregaDAO.getEntregasPorIdTurmaTipoTG(turmaDTO2, tgdto);

        for(EntregaDTO entrega: listAuxiliar){
            listEntregas.add(entrega);
            System.out.println(entrega);
        }




    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        obsAno.add(2023);
        anoComboBox.setItems(obsAno);

    }
}
