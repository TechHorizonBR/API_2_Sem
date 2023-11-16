package com.tgsync.tgsync;

import Model.DAO.AlunoDAO;
import Model.DAO.NotaDAO;
import Model.DAO.TurmaDAO;
import Model.DTO.AlunoDTO;
import Model.DTO.NotaDTO;
import Model.DTO.TurmaDTO;
import Model.util.Alerts;
import com.tgsync.tgsync.util.MudancaTelas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.LinkedList;
import java.util.List;

public class TelaRelatorioFechamentoController extends MudancaTelas {

    @FXML
    private TextField txtAno;
    @FXML
    private TableColumn<NotaDTO, Double> colunaMedia;

    @FXML
    private TableColumn<NotaDTO, String> colunaNome;
    @FXML
    private TableView<NotaDTO> tabelaMedia;

    @FXML
    private ComboBox<Integer> tgCombo;
    ObservableList<Integer> listTG = FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> tipoCombo;
    @FXML
    private ComboBox<Integer> semestreCombo;
    @FXML
    void carregarTipoTG(ActionEvent event) {
        listTG.clear();
        tgCombo.setItems(null);
        String tipo = tipoCombo.getValue();
        if(tipo.equals("Portfólio")){
            listTG.add(1);
            listTG.add(2);
            tgCombo.setItems(listTG);
            tgCombo.setValue(listTG.get(0));
        }
    }
    @FXML
    public void onOkButton(){
        AlunoDAO alunoDAO = new AlunoDAO();
        TurmaDAO turmaDAO = new TurmaDAO();
        NotaDAO notaDAO = new NotaDAO();
        Integer tg = null;
        if(tgCombo.getValue() == null){
            tg = 1;
        }else{
            tg = tgCombo.getValue();
        }

        String tipoTg = tipoCombo.getValue();

        if (txtAno.getText().isEmpty() || semestreCombo.getValue() == null || tipoTg == null){
            Alerts.showAlert("Atenção", "", "Preenchimento de todos os campos é obrigatório", Alert.AlertType.WARNING);
        }else if(txtAno.getText().matches(".*[a-zA-Z].*")) {
            Alerts.showAlert("Atenção", "", "Os campos não aceitam letras, apenas números!", Alert.AlertType.WARNING);
        }else{
            Integer ano = Integer.parseInt(txtAno.getText());
            Integer semestre = semestreCombo.getValue();


            List<Long> listMatricula = new LinkedList<>();
            TurmaDTO turmaDTO = turmaDAO.getTurmaPorAtributo(new TurmaDTO(ano, semestre, tg));
            List<NotaDTO> listNota = new LinkedList<>();

            if (turmaDTO != null){
                listMatricula = alunoDAO.getAllMatriculaPorIdTipoeIdTurma(tipoTg, tg, turmaDTO);


                if (!listMatricula.isEmpty()) {
                    for(Long matricula : listMatricula){
                        //listNota.add(notaD)
                    }
                }
                }

    }
}
}

