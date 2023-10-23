package com.tgsync.tgsync;

import Model.DAO.TurmaDAO;
import Model.DTO.EntregaDTO;
import Model.DTO.TurmaDTO;
import Model.util.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class EditarEntregaController {

    @FXML
    private Button ButtonCadastrar;

    @FXML
    private TextField FieldTitulo;

    @FXML
    private DatePicker campoData;

    @FXML
    private Text txtId;

    @FXML
    private Text txtTg;

    @FXML
    public void setMessage(EntregaDTO entregaDTO){
        FieldTitulo.setText(entregaDTO.getTituloEntrega());
        txtId.setText(entregaDTO.getIdEntrega().toString());
        TurmaDAO turmaDAO = new TurmaDAO();
        TurmaDTO turmaDTO = turmaDAO.getTurmaPorId(entregaDTO.getIdTurmas());
        if(turmaDTO!=null){
            int tg = turmaDTO.getDisciplina();
            String tgString = String.valueOf(tg);
            txtTg.setText(tgString);
        }
        Date utilDate = new Date(entregaDTO.getDataEntrega().getTime());
        Instant dataInstant = utilDate.toInstant();
        LocalDate dataLocalDate = dataInstant.atZone(ZoneId.systemDefault()).toLocalDate();
        campoData.setValue(dataLocalDate);
    }

    @FXML
    public void updateEntrega(){
        Long id = Long.valueOf(txtId.getText());
        String titulo = FieldTitulo.getText().trim();
        Integer tg = Integer.valueOf(txtTg.getText());
        LocalDate dataEntrega = campoData.getValue();
        LocalDate dataAtual = LocalDate.now();
        System.out.println(dataEntrega);
        System.out.println(dataAtual);

        if(titulo.equals("")){
            Alerts.showAlert("ATENÇÃO", "", "É obrigatório o preenchimento do título.", Alert.AlertType.WARNING);
        } else if (dataEntrega == null) {
            Alerts.showAlert("ATENÇÃO", "", "É obrigatório o preenchimento da data.", Alert.AlertType.WARNING);
        } else if(dataEntrega.isBefore(dataAtual)){
            Alerts.showAlert("ATENÇÃO", "", "A data deve ser superior à data atual e não pode ser nula.", Alert.AlertType.WARNING);
        }else{
            System.out.println("UPDATE DA ENTREGA");
            Alerts.showAlert("SUCESSO!", "","Atualização de entrega realizada com sucesso.",Alert.AlertType.CONFIRMATION );
        }
    }
}
