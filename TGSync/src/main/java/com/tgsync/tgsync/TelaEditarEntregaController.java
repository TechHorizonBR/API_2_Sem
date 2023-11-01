package com.tgsync.tgsync;

import Model.DAO.EntregaDAO;
import Model.DAO.TurmaDAO;
import Model.DTO.EntregaDTO;
import Model.DTO.TurmaDTO;
import Model.Service.TurmaService;
import Model.util.Alerts;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TelaEditarEntregaController {

    private TelaEntregasController telaEntregasController;
    public void setTelaEntregasController(TelaEntregasController telaEntregasController){
        this.telaEntregasController = telaEntregasController;
    }

    @FXML
    private Button ButtonCadastrar;

    @FXML
    private TextField FieldTitulo;

    @FXML
    private DatePicker campoData;

    @FXML
    private Text txtTipoTG;

    @FXML
    private Text txtTg;
    private Long id;
    private String tipoTG;

    @FXML
    public void setMessage(EntregaDTO entregaDTO){
        FieldTitulo.setText(entregaDTO.getTituloEntrega());
        txtTipoTG.setText(entregaDTO.getTipo());
        this.id = entregaDTO.getIdEntrega();
        this.tipoTG = entregaDTO.getTipo();
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
    public void updateEntrega() throws ParseException {
        String titulo = FieldTitulo.getText().trim();
        Integer tg = Integer.valueOf(txtTg.getText());
        LocalDate dataEntrega = campoData.getValue();
        LocalDate dataAtual = LocalDate.now();
        TurmaDTO turmaDTO = TurmaService.buscarTurmaComDataDoPC();
        turmaDTO.setDisciplina(tg);
        turmaDTO = TurmaDAO.getTurmaPorAtributo(turmaDTO);

        if(titulo.equals("")){
            Alerts.showAlert("ATENÇÃO", "", "É obrigatório o preenchimento do título.", Alert.AlertType.WARNING);
        } else if (dataEntrega == null) {
            Alerts.showAlert("ATENÇÃO", "", "É obrigatório o preenchimento da data.", Alert.AlertType.WARNING);
        } else if(dataEntrega.isBefore(dataAtual)){
            Alerts.showAlert("ATENÇÃO", "", "A data deve ser superior à data atual e não pode ser nula.", Alert.AlertType.WARNING);
        }else{
            EntregaDAO entregaDAO = new EntregaDAO();
            LocalDateTime localDateTime = dataEntrega.atStartOfDay();
            Date date = java.util.Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            entregaDAO.updateEntrega(new EntregaDTO(this.id, date, titulo, turmaDTO.getId(), tipoTG));
            telaEntregasController.updateTablesFromEditController();
            Alerts.showAlert("SUCESSO!", "","Atualização de entrega realizada com sucesso.",Alert.AlertType.CONFIRMATION );
        }
    }
}
