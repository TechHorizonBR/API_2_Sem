package Model.Service;

import Model.DAO.OrientadorDAO;
import Model.DTO.OrientadorDTO;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class OrientadorService {
    public static void registrarOrientador(String caminhoCSV){
        try(CSVReader csvReader = new CSVReaderBuilder(new FileReader(caminhoCSV)).withSkipLines(1).build()){
            List<String[]> linhas = csvReader.readAll();

            OrientadorDAO orientadorDAO = new OrientadorDAO();
            OrientadorDTO orientadorDTO = new OrientadorDTO();

            for (String[] linha : linhas) {
                if(orientadorDAO.getOrientadorPorEmail(linha[5]) == null){
                    orientadorDTO = new OrientadorDTO(linha[4], linha[5]);
                    orientadorDAO.addOrientador(orientadorDTO);
                }
            }


        }
        catch (IOException e){

        } catch (CsvException e) {
            throw new RuntimeException(e);
        }


    }
}
