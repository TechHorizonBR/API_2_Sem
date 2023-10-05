package Model.Service;

import Model.DAO.AlunoDAO;
import Model.DAO.OrientadorDAO;
import Model.DAO.TurmaDAO;
import Model.DTO.AlunoDTO;
import Model.DTO.OrientadorDTO;
import Model.DTO.TurmaDTO;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class AlunoService {

    public static void registrarAluno (String caminhoCSV, TurmaDTO turmaDTO) {
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(caminhoCSV))
                .withSkipLines(1)
                .build()) {
            List<String[]> linhas = csvReader.readAll();

            AlunoDAO alunoDAO = new AlunoDAO();
            TurmaDAO turmaDAO = new TurmaDAO();
            OrientadorDAO orientadorDAO = new OrientadorDAO();
            OrientadorDTO orientadorDTO = new OrientadorDTO();
            AlunoDTO alunoDTO;

            String emailFatec = null;

            for ( String[] linha: linhas){
                if (linha[2].isEmpty()){
                    emailFatec= linha[1];
                }
                else {
                    emailFatec = linha[2];
                }

                alunoDTO = alunoDAO.getAlunoPorEmail(emailFatec);

                orientadorDTO = orientadorDAO.getOrientadorPorEmail(linha[5]);

                if (alunoDTO != null){
                    alunoDAO.updateAluno(new AlunoDTO(linha[3], linha[1], emailFatec, orientadorDTO.getId(), turmaDTO.getId()));

                }
                else {
                    alunoDAO.addAluno(new AlunoDTO(linha[3], linha[1], emailFatec, orientadorDTO.getId(), turmaDTO.getId());
            }







        }catch (FileNotFoundException e){
            e.getMessage();
        }catch (IOException e) {
            e.getMessage();
        }catch(CsvException e){
            e.getMessage();
        }
    }

}
