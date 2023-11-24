package com.tgsync.tgsync;

import Model.DAO.AlunoDAO;
import Model.DAO.EntregaDAO;
import Model.DAO.NotaDAO;
import Model.DAO.TurmaDAO;
import Model.DTO.*;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
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
    List<Long> listMatricula = new LinkedList<>();

    private EntregaDAO entregaDAO = new EntregaDAO();
    public Double calculaMedia(List<Double> listaNotas){
        Double soma =0.0;
        for(Double nota: listaNotas){
            soma += nota;
        }
        return soma/listaNotas.size();
    }

    public void encontraAlunoApto(ActionEvent event) {
        AlunoDAO alunoDAO = new AlunoDAO();
        TurmaDAO turmaDAO = new TurmaDAO();
        NotaDAO notaDAO = new NotaDAO();
        EntregaDAO entregaDAO = new EntregaDAO();
        List<Long> listIdEntregas = null;
        try {
            if (anoComboBox.getValue() != null && semestreComboBox.getValue() != null) {
                TurmaDTO turmaDTO1 = TurmaDAO.getTurmaPorAtributo(new TurmaDTO(anoComboBox.getValue(), semestreComboBox.getValue(), 1));
                TurmaDTO turmaDTO2 = TurmaDAO.getTurmaPorAtributo(new TurmaDTO(anoComboBox.getValue(), semestreComboBox.getValue(), 2));
                List<EntregaDTO> listEntregasPort = new ArrayList<>();
                List<EntregaDTO> listEntregasRelatorio = new ArrayList<>();
                TGDTO tgdto = new TGDTO();
                tgdto.setTipo("Portfólio");
                if (turmaDTO1 != null && turmaDTO2 != null) {
                    listEntregasPort.addAll(entregaDAO.getEntregasPorIdTurmaTipoTG(turmaDTO1, tgdto));
                    listEntregasPort.addAll(entregaDAO.getEntregasPorIdTurmaTipoTG(turmaDTO2, tgdto));
                    System.out.println(listEntregasPort.size());
                }
                tgdto.setTipo("Relatório Técnico");
                if (turmaDTO1 != null && turmaDTO2 != null) {
                    listEntregasRelatorio.addAll(entregaDAO.getEntregasPorIdTurmaTipoTG(turmaDTO1, tgdto));
                    listEntregasRelatorio.addAll(entregaDAO.getEntregasPorIdTurmaTipoTG(turmaDTO2, tgdto));
                }
                List<String> tipoTg = new ArrayList<>();
                tipoTg.add("Portfólio");
                tipoTg.add("Relatório Técnico");
                List<NotaDTO> listNota = new LinkedList<>();

                for (String tipo : tipoTg) {
                    listMatricula = alunoDAO.getAllMatriculaPorIdTipoeIdTurma(tipo, turmaDTO1);
                    listIdEntregas = entregaDAO.getIdEntregasPorTurma(turmaDTO1.getId(), tipo);

                    if (!listMatricula.isEmpty()) {
                        for (Long matricula : listMatricula) {
                            List<Double> notas = new LinkedList<>();
                            notas = notaDAO.getMedia(listIdEntregas, matricula);
                            Double media = 0.0;

                            if (!notas.isEmpty()) {
                                for (Double nota : notas) {
                                    media += nota;
                                }
                                media /= listIdEntregas.size();
                                DecimalFormat df = new DecimalFormat("#.##");
                                String numeroFormatado = df.format(media);
                                numeroFormatado = numeroFormatado.replace(",", ".");
                                media = Double.valueOf(numeroFormatado);
                                System.out.println(media);
                            }
                            listNota.add(new NotaDTO(media, matricula));
                        }
                    }
                }
                Alerts.showAlert("Sucesso!", "", "Gerado com sucesso!", Alert.AlertType.CONFIRMATION);
            }
        } catch (Exception e){
            Alerts.showAlert("Erro","","Erro ao gerar csv", Alert.AlertType.ERROR);
        }
    }
    public void exibirSemestre(){
        TurmaDAO turmaDao = new TurmaDAO();
        List<TurmaDTO> turmasCadastradas = turmaDao.getAllTurmas();
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