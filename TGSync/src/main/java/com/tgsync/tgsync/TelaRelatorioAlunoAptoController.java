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
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;

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
    private OrientadorDAO orientadorDAO = new OrientadorDAO();

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

    @FXML
    public void gerarCSV() throws IOException {
        if (!obsAlunosAptos.isEmpty()) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Escolher Diretório para Salvar Arquivo");

            File selectedDirectory = directoryChooser.showDialog(null);

            if (selectedDirectory != null) {
                String directoryPath = selectedDirectory.getAbsolutePath();
                String filePath = directoryPath + File.separator + "RelatorioAlunosAptosDefesa.csv";
                Files.createFile(Paths.get(filePath));

                List<String> existentes = LinhaExistentes(filePath);
                String linha = "Nome do Aluno: ;Email Fatec do aluno: ;Email pessoal do aluno: ;Nome orientador: ;Email do orientador: ;";
                existentes.add(linha);

                for (AlunoDTO aluno : obsAlunosAptos) {
                    OrientadorDTO orientadorDTO = orientadorDAO.getOrientadorPorId(aluno.getIdOrientador());
                    linha = aluno.getNome()+";"+aluno.getEmailFatec()+";"+aluno.getEmailPessoal()+";"+orientadorDTO.getNome() + ";" + orientadorDTO.getEmail() + ";";
                    existentes.add(linha);
                }

                String todasLinhas = unicaLinha(existentes);

                try (FileWriter arquivoCSV = new FileWriter(filePath)) {
                    arquivoCSV.write(todasLinhas);
                    Alerts.showAlert("SUCESSO!", "", "Arquivo CSV gerado com sucesso na pasta "+filePath, Alert.AlertType.CONFIRMATION);
                } catch (IOException e) {
                    Alerts.showAlert("ATENÇÃO!", "", "Alguma coisa não ocorreu bem! Verifique se já existe o arquivo no local destinado, ou entre em contato com seu administrador.", Alert.AlertType.ERROR);
                    e.printStackTrace();
                }
            }
        } else {
            Alerts.showAlert("ATENÇÃO!", "", "Não existe alunos aptos!", Alert.AlertType.WARNING);
        }
    }

    public String unicaLinha(List<String> existentes) {
        Set<String> linhasUnicas = new LinkedHashSet<>(existentes);
        return String.join("\n", linhasUnicas);
    }

    public List<String> LinhaExistentes(String url){
        List<String> result = new ArrayList<String>();
        try{
            Path path = Paths.get(url);
            result = Files.readAllLines(path);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}