package com.tgsync.tgsync;

import Model.DAO.*;
import Model.DTO.AlunoDTO;
import Model.DTO.NotaDTO;
import Model.DTO.OrientadorDTO;
import Model.DTO.TurmaDTO;
import Model.Service.TurmaService;
import Model.util.Alerts;
import com.tgsync.tgsync.util.MudancaTelas;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.module.ModuleDescriptor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class TelaRelatorioCertificadoOrientadoresController extends MudancaTelas {
    @FXML
    private TableColumn<OrientadorDTO, String> colunaNome;
    @FXML
    private TableView<OrientadorDTO> tabelaOrientadores;
    private TurmaDAO turmaDAO = new TurmaDAO();
    private OrientadorDAO orientadorDAO = new OrientadorDAO();
    private AlunoDAO alunoDAO = new AlunoDAO();
    private EntregaDAO entregaDAO = new EntregaDAO();
    private NotaDAO notaDAO = new NotaDAO();

    ObservableList<OrientadorDTO> obslistOrientador = FXCollections.observableArrayList();
    List<Long> listOrientadoresAptosIds = new LinkedList<>();

    @FXML
    public void onOkButton() throws IOException {
        if (!listOrientadoresAptosIds.isEmpty()) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Escolher Diretório para Salvar Arquivo");

            File selectedDirectory = directoryChooser.showDialog(null);

            if (selectedDirectory != null) {
                String directoryPath = selectedDirectory.getAbsolutePath();
                String filePath = directoryPath + File.separator + "meuArquivo.csv";
                Files.createFile(Paths.get(filePath));

                List<String> existentes = LinhaExistentes(filePath);

                for (Long idOrientador : listOrientadoresAptosIds) {
                    List<Long> idsAlunos = alunoDAO.getIdsAlunosPorIdOrientador(idOrientador);
                    List<AlunoDTO> listAluno = new LinkedList<>();
                    OrientadorDTO orientadorDTO = orientadorDAO.getOrientadorPorId(idOrientador);

                    for (Long idAluno : idsAlunos) {
                        listAluno.add(alunoDAO.getAlunoPorId(idAluno));
                    }

                    for (AlunoDTO aluno : listAluno) {
                        String linha = orientadorDTO.getNome() + ";" + orientadorDTO.getEmail() + ";" + aluno.getNome() + ";" + aluno.getEmailFatec() + ";";
                        existentes.add(linha);
                    }
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
            Alerts.showAlert("ATENÇÃO!", "", "Não existe orientadores aptos!", Alert.AlertType.WARNING);
        }
    }

    public String unicaLinha(List<String> existentes) {
        Set<String> linhasUnicas = new LinkedHashSet<>(existentes); // Usando um Set para garantir linhas únicas
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


    public void initialize() throws ParseException {
        TurmaDTO turmaDTO1 = TurmaService.buscarTurmaComDataDoPC();
        turmaDTO1.setDisciplina(1);

        TurmaDTO turmaDTO2 = TurmaService.buscarTurmaComDataDoPC();
        turmaDTO2.setDisciplina(2);

        turmaDTO1 = TurmaDAO.getTurmaPorAtributo(turmaDTO1);
        turmaDTO2 = TurmaDAO.getTurmaPorAtributo(turmaDTO2);

        if(turmaDTO1 != null || turmaDTO2 != null){
            List<Long> idsOrientadores = new LinkedList<>();
            idsOrientadores = orientadorDAO.getIdsOrientadores();

            if(!idsOrientadores.isEmpty()){
                List<Long> idsAlunos =  new LinkedList<>();
                List<Long> idsEntregas = new LinkedList<>();
                List<Long> idsEntregasAux = new LinkedList<>();
                List<Long> idsOrientadoresAptos = new LinkedList<>();

                for(Long idOrientador : idsOrientadores){
                    idsAlunos = alunoDAO.getIdsAlunosPorIdOrientador(idOrientador);
                    if(turmaDTO1 != null && turmaDTO2 != null){
                        idsEntregas = entregaDAO.getIdEntregasPorTurma(turmaDTO1.getId());
                        idsEntregasAux = entregaDAO.getIdEntregasPorTurma(turmaDTO2.getId());

                        for(Long id : idsEntregasAux){
                            idsEntregas.add(id);
                        }
                    } else if (turmaDTO1 != null) {
                        idsEntregas = entregaDAO.getIdEntregasPorTurma(turmaDTO1.getId());
                    }else if (turmaDTO2 != null){
                        idsEntregas = entregaDAO.getIdEntregasPorTurma(turmaDTO1.getId());
                    }

                    boolean validacao = false;

                    for(Long idAluno : idsAlunos){
                        for(Long idEntrega : idsEntregas){
                            NotaDTO notaDTO = notaDAO.getNotaPorAlunoEntrega(idAluno, idEntrega);
                            if(notaDTO != null) {
                                if (!idsOrientadoresAptos.contains(idOrientador)) {
                                    idsOrientadoresAptos.add(idOrientador);
                                }
                                validacao = true;
                            }
                        }
                        if(!validacao){
                            try{
                                idsOrientadoresAptos.remove(idOrientador);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            break;
                        }
                        validacao = false;
                    }
                    idsAlunos.clear();
                }
                this.listOrientadoresAptosIds = idsOrientadoresAptos;
                for(Long id : idsOrientadoresAptos){
                    obslistOrientador.add(orientadorDAO.getOrientadorPorId(id));
                }

                colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
                tabelaOrientadores.setItems(obslistOrientador);


            }else{
                Alerts.showAlert("ATENÇÃO!", "", "Não há orientadores aptos a certificar no momento.", Alert.AlertType.CONFIRMATION);
            }

        }
    }
    OrientadorDTO orientadorMandarDados = new OrientadorDTO();
    @FXML
    void onTableClick(MouseEvent event) {
        int i = tabelaOrientadores.getSelectionModel().getSelectedIndex();
        OrientadorDTO orientadorDTO = tabelaOrientadores.getItems().get(i);
        this.orientadorMandarDados = orientadorDTO;
        openTelaPopUp(orientadorDTO);
    }
    public OrientadorDTO mandarDados(){
        return this.orientadorMandarDados;
    }
    public void openTelaPopUp(OrientadorDTO orientadorDTO){
        try {
            this.orientadorMandarDados = orientadorDTO;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaListaAlunosPOP.fxml"));
            Parent root = loader.load();

            TelaAlunosPOPController telaAlunosPOPController = loader.getController();
            telaAlunosPOPController.injecaoTela(this, orientadorDTO);
            telaAlunosPOPController.initialize();

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("TGSync");
            Scene scene = new Scene(root);
            popupStage.setScene(scene);

            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
