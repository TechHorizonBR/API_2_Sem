package Model.Service;

import Model.DAO.TurmaDAO;
import Model.DTO.TurmaDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TurmaService {
    public static void registrarTurma() throws ParseException {
        TurmaDAO turmadao = new TurmaDAO();

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

        turmadao.addTurma(new TurmaDTO(ano, semestre, 1));
        turmadao.addTurma(new TurmaDTO(ano, semestre, 2));
    }
}
