package com.tgsync.tgsync;

import Model.DAO.*;
import Model.DTO.NotaDTO;
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
import java.util.concurrent.ExecutionException;

public class TelaRelatorioCertificadoOrientadoresController extends MudancaTelas {

    private TurmaDAO turmaDAO = new TurmaDAO();
    private OrientadorDAO orientadorDAO = new OrientadorDAO();
    private AlunoDAO alunoDAO = new AlunoDAO();
    private EntregaDAO entregaDAO = new EntregaDAO();
    private NotaDAO notaDAO = new NotaDAO();

    public void onOkButton() throws ParseException {
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
                List<Long[]> idsOrientadoresAptos = new LinkedList<>();

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
                    System.out.println("Aluno: "+idsAlunos);

                    for(Long idAluno : idsAlunos){
                        Long[] array = new Long[2];
                        array[0] = idOrientador;
                        array[1] = idAluno;
                        for(Long idEntrega : idsEntregas){
                            NotaDTO notaDTO = notaDAO.getNotaPorAlunoEntrega(idAluno, idEntrega);
                            if(notaDTO != null) {
                                if (!idsOrientadoresAptos.contains(array)) idsOrientadoresAptos.add(array);
                                validacao = true;
                            }
                            }
                        if(!validacao){
                            try{
                                idsOrientadores.remove(array);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                    idsAlunos.clear();
                }

                for(Long[] ids : idsOrientadoresAptos){
                    for(Long id : ids){
                        System.out.println(id);
                    }
                }
            }else{
                Alerts.showAlert("ATENÇÃO!", "", "Não há orientadores aptos a certificar no momento.", Alert.AlertType.CONFIRMATION);
            }

        }
    }

    public static void main(String[] args) throws ParseException {
        TelaRelatorioCertificadoOrientadoresController tl = new TelaRelatorioCertificadoOrientadoresController();
        tl.onOkButton();
    }
}
