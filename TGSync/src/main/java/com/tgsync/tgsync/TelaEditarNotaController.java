package com.tgsync.tgsync;

import Model.DAO.EntregaDAO;
import Model.DAO.NotaDAO;
import Model.DTO.AlunoDTO;
import Model.DTO.EntregaDTO;
import Model.DTO.NotaDTO;
import Model.DTO.TurmaDTO;
import Model.util.Alerts;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.ParseException;
import java.util.List;

public class TelaEditarNotaController {

    private TelaFeedbackController telaFeedbackController;
    public void injecaoDepFeedback(TelaFeedbackController telaFeedbackController){
        this.telaFeedbackController = telaFeedbackController;
    }


    @FXML
    private Button ButtonSalvarNota;

    @FXML
    private TextArea FieldFeedback;

    @FXML
    private TextField FieldNota;

    @FXML
    private ImageView imgLogo;

    @FXML
    private Text txtEntrega;

    @FXML
    void updateEntrega(ActionEvent event) {

    }

    @FXML
    void onSaveClick(MouseEvent event) {
        Double nota = null;
        String feedback = null;
        Stage stage = (Stage) ButtonSalvarNota.getScene().getWindow();
        int success;
        if(!((FieldNota.getText()).trim().equals(""))){
            if((FieldNota.getText().matches(".*[a-zA-Z].*")) || (Double.parseDouble(FieldNota.getText()) < 0) || (Double.parseDouble(FieldNota.getText()) > 10)){
                Alerts.showAlert("Atenção", "", "Por favor, insira uma nota válida.", Alert.AlertType.WARNING);
            }else{
                nota = Double.parseDouble(FieldNota.getText());
            }
        }else{
            Alerts.showAlert("Atenção", "", "Por favor, insira uma nota válida.", Alert.AlertType.WARNING);
        }

        if(!(FieldFeedback.getText().trim().equals(""))){
            feedback = FieldFeedback.getText().trim();
        }else{
            Alerts.showAlert("Atenção", "", "Por favor, insira um Feedback válido.", Alert.AlertType.WARNING);
        }

        if(!(nota == null) && !(feedback == null)){
            NotaDAO notaDAO = new NotaDAO();
            NotaDTO aluno_nota = notaDAO.getNotaPorAlunoEntrega(aluno.getId(), entrega.getIdEntrega());
            if(aluno_nota == null){
                success = notaDAO.addNota(new NotaDTO(feedback, nota, aluno.getId(), entrega.getIdEntrega()));
                if(success == 1){
                    Alerts.showAlert("Erro", "", "Um erro ocorreu no sistema, por favor tente novamente mais tarde.", Alert.AlertType.ERROR);
                }else{
                    Alerts.showAlert("Sucesso", "", "Informações adicionadas com sucesso!", Alert.AlertType.INFORMATION);
                    telaFeedbackController.receberDados(aluno, turma);
                    stage.close();
                }
            }else{
                success = notaDAO.updateNota(new NotaDTO(aluno_nota.getId(), feedback, nota, aluno.getId(), entrega.getIdEntrega()));
                if(success == 1){
                    Alerts.showAlert("Erro", "", "Um erro ocorreu no sistema, por favor tente novamente mais tarde.", Alert.AlertType.ERROR);
                }else{
                    Alerts.showAlert("Sucesso", "", "Informações adicionadas com sucesso!", Alert.AlertType.INFORMATION);
                    telaFeedbackController.receberDados(aluno, turma);
                    stage.close();
                }
            }
        }

    }

    private AlunoDTO aluno = new AlunoDTO();
    private TurmaDTO turma = new TurmaDTO();
    private EntregaDTO entrega = new EntregaDTO();

    public void receberDados(AlunoDTO aluno, TurmaDTO turma, EntregaDTO entrega){
        this.aluno = aluno;
        this.turma = turma;
        this.entrega = entrega;

        txtEntrega.setText(entrega.getTituloEntrega());
    }

}
