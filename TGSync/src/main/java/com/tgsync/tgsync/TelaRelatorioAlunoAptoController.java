package com.tgsync.tgsync;

import Model.DAO.*;
import Model.DTO.*;
import Model.util.Alerts;
import com.tgsync.tgsync.util.MudancaTelas;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private List<Long> listMatriculaTurma1 = new LinkedList<>();
    private List<Long> listMatriculaTurma2 = new LinkedList<>();

    private List<Long> listAlunosAptos = new LinkedList<>();
    private ObservableList<AlunoDTO> obsAlunosAptos = FXCollections.observableArrayList();
    @FXML
    private TableColumn<AlunoDTO,String> colunaNome;

    @FXML
    private TableColumn<AlunoDTO,String> colunaTipoTg;

    @FXML
    private TableColumn<AlunoDTO,String> colunaOrientador;

    @FXML
    private TableView<AlunoDTO> tableAlunos;
    private TGDAO tgdao = new TGDAO();

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



                String tipo = "Relatório";

                listMatriculaTurma2 = alunoDAO.getAllMatriculaPorIdTipoeIdTurma(tipo, turmaDTO1);
                listIdEntregas = entregaDAO.getIdEntregasPorTurma(turmaDTO1.getId(), tipo);

                boolean validacao = false;
                if (!listMatriculaTurma2.isEmpty()) {
                    for (Long matricula : listMatriculaTurma2) {
                        for (Long idEntrega : listIdEntregas) {
                            NotaDTO notaDTO = notaDAO.getNotaPorAlunoEntrega(matricula, idEntrega);
                            if (notaDTO != null) {
                                if (!listAlunosAptos.contains(matricula)) {
                                    listAlunosAptos.add(matricula);
                                }
                                validacao = true;
                            }
                            else{
                                validacao = false;
                            }
                            if (!validacao) {
                                try {
                                    listAlunosAptos.remove(matricula);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                        }
                    }
                }

                for(Long idAluno:listAlunosAptos){
                    List<Double> notas = notaDAO.getMedia(listIdEntregas,idAluno);
                    Double media = 0.0;
                    if (!notas.isEmpty()) {
                        for (Double nota : notas) {
                            media += nota;
                        }

                        media /= listIdEntregas.size();
                        if(media >= 6.0 ){
                            obsAlunosAptos.add(alunoDAO.getAlunoPorId(idAluno));
                        }
                    }
                }

                tipo = "Portfólio";

                listMatriculaTurma2 = alunoDAO.getAllMatriculaPorIdTipoeIdTurma(tipo, turmaDTO2);
                listIdEntregas = entregaDAO.getIdEntregasPorTurma(turmaDTO2.getId(), tipo);

                validacao = false;
                if (!listMatriculaTurma2.isEmpty()) {
                    for (Long matricula : listMatriculaTurma2) {
                        for (Long idEntrega : listIdEntregas) {
                            NotaDTO notaDTO = notaDAO.getNotaPorAlunoEntrega(matricula, idEntrega);
                            if (notaDTO != null) {
                                if (!listAlunosAptos.contains(matricula)) {
                                    listAlunosAptos.add(matricula);
                                }
                                validacao = true;
                            }
                            else{
                                validacao = false;
                            }
                            if (!validacao) {
                                try {
                                    listAlunosAptos.remove(matricula);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                        }
                    }
                }

                for(Long idAluno:listAlunosAptos){
                    List<Double> notas = notaDAO.getMedia(listIdEntregas,idAluno);
                    Double media = 0.0;
                    if (!notas.isEmpty()) {
                        for (Double nota : notas) {
                            media += nota;
                        }

                        media /= listIdEntregas.size();
                        if(media >= 6.0 ){
                            obsAlunosAptos.add(alunoDAO.getAlunoPorId(idAluno));
                        }
                    }
                }
            }

            colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colunaOrientador.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeOrientador()));
            colunaTipoTg.setCellValueFactory(cellData -> {
                AlunoDTO aluno = cellData.getValue();
                List<TGDTO> tgDoAluno = tgdao.getTgsPorIdAluno(aluno.getId());
                if (!tgDoAluno.isEmpty()) {
                    for(TGDTO tgdto : tgDoAluno){
                        return new SimpleStringProperty(tgdto.getTipo());
                    }
                } else {
                    return new SimpleStringProperty("");
                }
                return null;
            });
            tableAlunos.setItems(obsAlunosAptos);


            Alerts.showAlert("Sucesso!", "", "Gerado com sucesso!", Alert.AlertType.CONFIRMATION);

        } catch (Exception e){
            Alerts.showAlert("Erro",e.getMessage(),"Erro ao gerar csv", Alert.AlertType.ERROR);
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