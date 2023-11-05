package Model.DTO;

import Model.DAO.NotaDAO;
import Model.DAO.TurmaDAO;
import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;
import java.util.Date;
public class EntregaDTO {
     private Long idEntrega;
     private Date dataEntrega;
     private String tituloEntrega;
     private Long idTurma;
     private String tipo;

     public EntregaDTO(Long idEntrega, Date dataEntrega, String tituloEntrega, Long idTurma, String tipo) {
          this.idEntrega = idEntrega;
          this.dataEntrega = dataEntrega;
          this.tituloEntrega = tituloEntrega;
          this.idTurma = idTurma;
          this.tipo = tipo;
     }

     public EntregaDTO( Date dataEntrega, String tituloEntrega, Long idTurma, String tipo) {
          this.dataEntrega = dataEntrega;
          this.tituloEntrega = tituloEntrega;
          this.idTurma = idTurma;
          this.tipo = tipo;
     }

     public EntregaDTO(Long idEntrega, Date dataEntrega, String tituloEntrega, String tipo) {
          this.idEntrega = idEntrega;
          this.dataEntrega = dataEntrega;
          this.tituloEntrega = tituloEntrega;
          this.tipo = tipo;
     }
     public EntregaDTO(Date dataEntrega, String tituloEntrega, String tipo) {
          this.dataEntrega = dataEntrega;
          this.tituloEntrega = tituloEntrega;
          this.tipo = tipo;
     }

     public EntregaDTO() {

     }

     public Long getIdEntrega(){
          return idEntrega;
     }
     public void setIdEntrega(Long idEntrega) {
          this.idEntrega = idEntrega;
     }
     public Date getDataEntrega(){
          return this.dataEntrega;
     }
     public void setDataEntrega (Date dataEntrega){
          this.dataEntrega = dataEntrega;
     }
     public String getTituloEntrega() {
          return tituloEntrega;
     }
     public void setTituloEntrega (String tituloEntrega) {
          this.tituloEntrega = tituloEntrega;
     }
     public Long getIdTurmas() {
          return this.idTurma;
     }
     public void setIdTurmas (Long idTurma) {
          this.idTurma = idTurma;
     }
     public String getTipo(){
          return this.tipo;
     }
     public void setTipo(String tipo){
          this.tipo = tipo;
     }
     public String getNotaAlunos(Long idAluno){
          NotaDAO notaDAO = new NotaDAO();
          NotaDTO notaDTO = notaDAO.getNotaPorAlunoEntrega(idAluno, this.idEntrega);
          if(notaDTO == null){
               return "-";
          }else{
               return (notaDTO.getValor()).toString();
          }
     }

     public String getFeedbackAlunos(Long idAluno){
          NotaDAO notaDAO = new NotaDAO();
          NotaDTO notaDTO = notaDAO.getNotaPorAlunoEntrega(idAluno, this.idEntrega);
          if(notaDTO == null){
               return "-";
          }else{
               return (notaDTO.getFeedback());
          }
     }
     @Override
     public String toString(){
          return String.format("Id Entrega: %s\nTitulo Entrega: %s\nData entrega: %s\nId turma: %s", this.idEntrega, this.tituloEntrega, this.dataEntrega, this.idTurma);
     }
     public SimpleStringProperty dataEntregaFormatadaProperty() {
          SimpleDateFormat formatoSaida = new SimpleDateFormat("dd/MM/yyyy");
          String dataFormatada = formatoSaida.format(dataEntrega);
          return new SimpleStringProperty(dataFormatada);
     }
     public String getMatriculaTG(){
          TurmaDAO turmaDAO = new TurmaDAO();
          TurmaDTO turmaDTO = turmaDAO.getTurmaPorId(this.idTurma);
          if(this.tipo.equals("Artigo Científico") || this.tipo.equals("Relatório Técnico")){
               return "1/2";
          }else{
               return String.valueOf(turmaDTO.getDisciplina());
          }
     }
}


