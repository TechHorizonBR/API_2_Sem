package com.tgsync.tgsync;

import Model.DAO.EntregaDAO;
import Model.DAO.TurmaDAO;
import Model.DTO.AlunoDTO;
import Model.DTO.EntregaDTO;
import Model.DTO.TGDTO;
import Model.DTO.TurmaDTO;
import Model.util.Alerts;
import com.tgsync.tgsync.util.MudancaTelas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.io.IOException;
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
    private ObservableList<Integer> obsSemestre = FXCollections.observableArrayList();

    private EntregaDAO entregaDAO = new EntregaDAO();

    public void encontraAlunoApto(ActionEvent event){
        try {
            if (anoComboBox.getValue() != null && semestreComboBox.getValue() != null) {
                TurmaDTO turmaDTO1 = TurmaDAO.getTurmaPorAtributo(new TurmaDTO(anoComboBox.getValue(), semestreComboBox.getValue(), 1));
                TurmaDTO turmaDTO2 = TurmaDAO.getTurmaPorAtributo(new TurmaDTO(anoComboBox.getValue(), semestreComboBox.getValue(), 2));

                TGDTO tgdto = new TGDTO();
                tgdto.setTipo("Portfólio");
                if (turmaDTO1 != null && turmaDTO2 != null) {
                    List<EntregaDTO> listEntregas = entregaDAO.getEntregasPorIdTurmaTipoTG(turmaDTO1, tgdto);
                    List<EntregaDTO> listAuxiliar = entregaDAO.getEntregasPorIdTurmaTipoTG(turmaDTO2, tgdto);

                    for (EntregaDTO entrega : listAuxiliar) {
                        listEntregas.add(entrega);
                        System.out.println(entrega);
                    }
                }
                Alerts.showAlert("Sucesso!","","Gerado com sucesso!", Alert.AlertType.CONFIRMATION);
                System.out.println("Chegou aqui!");
            }
        }
        catch (NullPointerException e){
            Alerts.showAlert("Erro","","Não existem entregas para esse período!", Alert.AlertType.ERROR);
        }
    }

    public void exibirSemestre( ){
        TurmaDAO turmaDAO = new TurmaDAO();
        List<TurmaDTO> turmasCadastradas = turmaDAO.getAllTurmas();
        int ano = anoComboBox.getValue();

        for(TurmaDTO turma: turmasCadastradas){
            if(turma.getAno() == ano && turma.getSemestre() == 1 && !obsSemestre.contains(1)){
                obsSemestre.add(1);
            }
            if(turma.getAno() == ano && turma.getSemestre() == 2 & !obsSemestre.contains(2)){
                obsSemestre.add(2);
            }
        }
        semestreComboBox.setItems(obsSemestre);
        semestreComboBox.setValue(obsSemestre.get(0));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TurmaDAO turmaDAO = new TurmaDAO();
        List<TurmaDTO> turmasCadastradas = turmaDAO.getAllTurmas();

        for(TurmaDTO turma: turmasCadastradas){
            int ano = turma.getAno();
            if(!obsAno.contains((int) ano)){
                obsAno.add(ano);
            }
        }
        anoComboBox.setItems(obsAno);
    }
}