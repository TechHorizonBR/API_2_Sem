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
import java.util.LinkedList;
import java.util.List;

public class AlunoService {

    public static void registrarAluno (String caminhoCSV, TurmaDTO turmaDTO) {
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(caminhoCSV))
                .withSkipLines(1)
                .build()) {
            List<String[]> linhas = csvReader.readAll();

            AlunoDAO alunoDAO = new AlunoDAO();
            OrientadorDAO orientadorDAO = new OrientadorDAO();
            OrientadorDTO orientadorDTO = new OrientadorDTO();
            AlunoDTO alunoDTO;
            String emailFatec = null;

            for (String[] linha : linhas) {
                if (linha[2].isEmpty()) {
                    emailFatec = linha[1];
                } else {
                    emailFatec = linha[2];
                }
                String emailOrientador = linha[5].trim();

                alunoDTO = alunoDAO.getAlunoPorEmailSemMatricula(emailFatec);
                orientadorDTO = orientadorDAO.getOrientadorPorEmail(emailOrientador);

                Long idOrientador = null;
                if(orientadorDTO!=null){
                    idOrientador = orientadorDTO.getId();
                }else{
                    orientadorDAO.addOrientador(new OrientadorDTO(linha[4], emailOrientador));
                    orientadorDTO = orientadorDAO.getOrientadorPorEmail(emailOrientador);
                    idOrientador = orientadorDTO.getId();
                }

                if (alunoDTO != null) {
                    AlunoDTO updateAluno = new AlunoDTO(alunoDTO.getId(), linha[3], linha[1], emailFatec, idOrientador);
                    alunoDAO.updateAluno(updateAluno);

                    if(linha[6].equals("TG1")){
                        turmaDTO.setDisciplina(1);
                        AlunoDTO alunoMatricula = alunoDAO.getAlunoPorEmail(updateAluno.getEmailFatec());
                        turmaDTO = TurmaDAO.getTurmaPorAtributo(turmaDTO);
                        for(Long idMatricula : alunoMatricula.getIdTurmas()){
                            if(!(idMatricula == turmaDTO.getId())){
                                alunoDAO.addMatriculaAluno(updateAluno, turmaDTO);
                                break;
                            }
                        }
                    } else if (linha[6].equals("TG2")) {
                        if(linha[7].contains("Relatório") || linha[7].contains("Artigo")){
                            turmaDTO.setDisciplina(1);
                        }else{
                            turmaDTO.setDisciplina(2);
                        }
                        AlunoDTO alunoMatricula = alunoDAO.getAlunoPorEmail(updateAluno.getEmailFatec());
                        turmaDTO = TurmaDAO.getTurmaPorAtributo(turmaDTO);
                        for(Long idMatricula : alunoMatricula.getIdTurmas()){
                            if(!(idMatricula == turmaDTO.getId())){
                                alunoDAO.addMatriculaAluno(updateAluno, turmaDTO);
                                break;
                            }
                        }
                    }else if(linha[6].equals("TG1 e TG2")){
                        if(linha[7].contains("Relatório") || linha[7].contains("Artigo")){
                            turmaDTO.setDisciplina(1);
                            AlunoDTO alunoMatricula = alunoDAO.getAlunoPorEmail(updateAluno.getEmailFatec());
                            turmaDTO = TurmaDAO.getTurmaPorAtributo(turmaDTO);
                            for(Long idMatricula : alunoMatricula.getIdTurmas()){
                                if(!(idMatricula == turmaDTO.getId())){
                                    alunoDAO.addMatriculaAluno(updateAluno, turmaDTO);
                                    break;
                                }
                            }
                        }else{
                            turmaDTO.setDisciplina(1);
                            AlunoDTO alunoMatricula = alunoDAO.getAlunoPorEmail(updateAluno.getEmailFatec());
                            turmaDTO = TurmaDAO.getTurmaPorAtributo(turmaDTO);
                            for(Long idMatricula : alunoMatricula.getIdTurmas()){
                                if(!(idMatricula == turmaDTO.getId())){
                                    alunoDAO.addMatriculaAluno(updateAluno, turmaDTO);
                                    break;
                                }
                            }
                            turmaDTO.setDisciplina(2);
                            alunoMatricula = alunoDAO.getAlunoPorEmail(updateAluno.getEmailFatec());
                            turmaDTO = TurmaDAO.getTurmaPorAtributo(turmaDTO);
                            for(Long idMatricula : alunoMatricula.getIdTurmas()){
                                if(!(idMatricula == turmaDTO.getId())){
                                    alunoDAO.addMatriculaAluno(updateAluno, turmaDTO);
                                    break;
                                }
                            }
                        }
                    }
                }else{
                    if(linha[6].equals("TG1")){
                        turmaDTO.setDisciplina(1);
                        AlunoDTO novoAluno = new AlunoDTO(linha[3], linha[1], emailFatec, idOrientador);
                        alunoDAO.addAluno(novoAluno);
                        AlunoDTO buscaAluno = alunoDAO.getAlunoPorEmailSemMatricula(novoAluno.getEmailFatec());

                        if(buscaAluno!=null){
                            turmaDTO = TurmaDAO.getTurmaPorAtributo(turmaDTO);
                            alunoDAO.addMatriculaAluno(buscaAluno, turmaDTO);
                        }
                    } else if (linha[6].equals("TG2")) {
                        if(linha[7].contains("Relatório") || linha[7].contains("Artigo")){
                            turmaDTO.setDisciplina(1);
                        }else{
                            turmaDTO.setDisciplina(2);
                        }
                        turmaDTO = TurmaDAO.getTurmaPorAtributo(turmaDTO);
                        AlunoDTO novoAluno = new AlunoDTO(linha[3], linha[1], emailFatec, idOrientador);
                        alunoDAO.addAluno(novoAluno);
                        AlunoDTO buscaAluno = alunoDAO.getAlunoPorEmailSemMatricula(novoAluno.getEmailFatec());
                        if(buscaAluno!=null){
                            alunoDAO.addMatriculaAluno(buscaAluno, turmaDTO);
                        }
                    }else if(linha[6].equals("TG1 e TG2")){
                        boolean val = false;
                        if(linha[7].contains("Relatório") || linha[7].contains("Artigo")){
                            val = true;
                        }
                        turmaDTO.setDisciplina(1);
                        turmaDTO = TurmaDAO.getTurmaPorAtributo(turmaDTO);
                        AlunoDTO novoAluno = new AlunoDTO(linha[3], linha[1], emailFatec, idOrientador);
                        alunoDAO.addAluno(novoAluno);
                        AlunoDTO buscaAluno = alunoDAO.getAlunoPorEmailSemMatricula(novoAluno.getEmailFatec());
                        if(buscaAluno!=null){
                            alunoDAO.addMatriculaAluno(buscaAluno, turmaDTO);
                            if(!val){
                                turmaDTO.setDisciplina(2);
                                turmaDTO = TurmaDAO.getTurmaPorAtributo(turmaDTO);
                                alunoDAO.addMatriculaAluno(buscaAluno, turmaDTO);
                            }
                        }
                    }
                }

                }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }

    }
}
