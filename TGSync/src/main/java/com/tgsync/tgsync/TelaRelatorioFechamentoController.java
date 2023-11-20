package com.tgsync.tgsync;

import Model.DAO.AlunoDAO;
import Model.DAO.EntregaDAO;
import Model.DAO.NotaDAO;
import Model.DAO.TurmaDAO;

import Model.DTO.NotaDTO;
import Model.DTO.TurmaDTO;
import Model.util.Alerts;
import com.tgsync.tgsync.util.MudancaTelas;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.text.DecimalFormat;
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
    ObservableList<NotaDTO> obsNota = FXCollections.observableArrayList();
    List<Long> listMatricula = new LinkedList<>();
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
        obsNota.clear();
        tabelaMedia.setItems(obsNota);
        AlunoDAO alunoDAO = new AlunoDAO();
        TurmaDAO turmaDAO = new TurmaDAO();
        NotaDAO notaDAO = new NotaDAO();
        EntregaDAO entregaDAO = new EntregaDAO();
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
            List<Long> listIdEntregas = new LinkedList<>();
            TurmaDTO turmaDTO = turmaDAO.getTurmaPorAtributo(new TurmaDTO(ano, semestre, tg));
            List<NotaDTO> listNota = new LinkedList<>();

            if (turmaDTO != null){
                listMatricula = alunoDAO.getAllMatriculaPorIdTipoeIdTurma(tipoTg,turmaDTO);
                listIdEntregas = entregaDAO.getIdEntregasPorTurma(turmaDTO.getId(), tipoTg);

                if (!listMatricula.isEmpty()){
                    for (Long matricula: listMatricula){
                        List<Double> notas = new LinkedList<>();
                        notas = notaDAO.getMedia(listIdEntregas, matricula);
                        Double media = 0.0;

                        if(!notas.isEmpty()){
                            for(Double nota : notas){
                                media += nota;
                            }
                            media /= listIdEntregas.size();
                            DecimalFormat df = new DecimalFormat("#.##");
                            String numeroFormatado = df.format(media);
                            numeroFormatado = numeroFormatado.replace(",", ".");
                            media = Double.valueOf(numeroFormatado);
                        }

                        listNota.add(new NotaDTO(media, matricula));
                    }
                    for(NotaDTO notaDTO : listNota){
                        obsNota.add(notaDTO);
                    }

                colunaNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeAluno()));
                colunaMedia.setCellValueFactory(new PropertyValueFactory<>("media"));
                colunaMedia.setCellFactory(column -> {
                    return new TableCell<NotaDTO, Double>() {
                        @Override
                        protected void updateItem(Double media, boolean empty) {
                            super.updateItem(media, empty);

                            if (media == null || empty) {
                                setText(null);
                                setStyle("");
                            } else {
                                setText(String.valueOf(media));
                                setStyle("-fx-font-weight: bold;");
                                if (media < 6.0) {
                                    setTextFill(Color.RED);
                                } else {
                                    setTextFill(Color.GREEN);
                                }
                            }
                        }
                    };
                });
                tabelaMedia.setItems(obsNota);
                }
            }else{
                Alerts.showAlert("Atenção", "", "Esta turma não existe", Alert.AlertType.WARNING);
            }
        }
    }
}

