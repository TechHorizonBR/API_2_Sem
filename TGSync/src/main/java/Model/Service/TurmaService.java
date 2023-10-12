package Model.Service;

import Model.DAO.TurmaDAO;
import Model.DTO.TurmaDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TurmaService {
    public static void registrarTurma() throws ParseException {
        TurmaDAO turmadao = new TurmaDAO();
        List<TurmaDTO> todasTurmas;
        int novaturma = 0;


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date hoje = new Date(); //Recebe data do computador com horario

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date hoje = cal.getTime(); //Recebe data do computador sem horario

        Integer ano = cal.get(Calendar.YEAR); //Recebe apenas o ano
        int semestre = 0;

        Date dia_esp = sdf.parse((ano+"-06-30"));

        if((hoje.equals(dia_esp))||(hoje.before(dia_esp))){
            semestre = 1;
        }else{
            semestre = 2;
        }

        todasTurmas = turmadao.getAllTurmas();
        //Verifica se ja existem turmas com mesmo semestre e ano
        for(TurmaDTO dados : todasTurmas){
            if(dados.getSemestre() == semestre && dados.getAno() == ano){
                novaturma = 1;
            }
        }

        if(novaturma == 0){
            turmadao.addTurma(new TurmaDTO(ano, semestre, 1));
            turmadao.addTurma(new TurmaDTO(ano, semestre, 2));
        }
    }
}
