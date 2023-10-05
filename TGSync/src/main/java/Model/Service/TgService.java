package Model.Service;
import Model.DAO.AlunoDAO;
import Model.DAO.TGDAO;
import Model.DTO.AlunoDTO;
import Model.DTO.TGDTO;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class TgService {
    public static void registrarTg(String caminhoCSV) {
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(caminhoCSV))
                .withSkipLines(1) // PULA UMA LINHA - CABEÇALHO
                .build()) {
            List<String[]> linhas = csvReader.readAll();

            AlunoDAO alunoDAO = new AlunoDAO();
            TGDAO tgdao = new TGDAO();
            AlunoDTO alunoDTO;

            for(String[] linha : linhas){
                String tipo = linha[7];
                String discplina = linha[10];
                String problema = linha[8];
                String empresa = linha[9];
                String email1 = linha[1];
                String email2 = linha[2];



                if (linha[2].isEmpty()){
                    System.out.println(alunoDAO.getAlunoPorEmail(email1));

                }else{
                    System.out.println(alunoDAO.getAlunoPorEmail(email2));
                }


                /* System.out.println("\nTipo: "+tipo+
                        "\nDiscplina: "+discplina+
                        "\nProblema: "+problema+
                        "\nEmpresa: "+empresa+
                        "\nEmail Aluno: "+linha[2]);*/
/*
                if (alunoDTO != null) {
                    Long idAluno = alunoDTO.getId();
                    tgdao.addTg(new TGDTO(tipo, discplina, problema, empresa, idAluno));
                    System.out.println("encontrou o jhony e deu certo o registro");
                } else {
                    System.err.println("Aluno não encontrado para o email fornecido: " + linha[1]);
                }*/
            }
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }

    }
}
