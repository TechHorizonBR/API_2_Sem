package com.tgsync.tgsync;

import Model.DAO.OrientadorDAO;
import Model.DAO.TurmaDAO;
import Model.DTO.OrientadorDTO;
import Model.DTO.TurmaDTO;
import Model.Service.TurmaService;
import Model.util.Alerts;
import com.tgsync.tgsync.util.MudancaTelas;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

public class TelaRelatorioCertificadoOrientadoresController extends MudancaTelas {

    private TurmaDAO turmaDAO;
    private OrientadorDAO orientadorDAO;

    public void onOkButton(ActionEvent event) throws ParseException {
        TurmaDTO turmaDTO1 = TurmaService.buscarTurmaComDataDoPC();
        turmaDTO1.setDisciplina(1);

        TurmaDTO turmaDTO2 = TurmaService.buscarTurmaComDataDoPC();
        turmaDTO2.setDisciplina(2);

        turmaDTO1 = TurmaDAO.getTurmaPorAtributo(turmaDTO1);
        turmaDTO2 = TurmaDAO.getTurmaPorAtributo(turmaDTO2);

        if(turmaDTO1 != null || turmaDTO2 != null){
            List<Long> idsOrientadores = orientadorDAO.getIdsOrientadores();
            if(!idsOrientadores.isEmpty()){

            }else{
                Alerts.showAlert("ATENÇÃO!", "", "Não há orientadores aptos a certificar no momento.", Alert.AlertType.CONFIRMATION);
            }

        }
    }
}
