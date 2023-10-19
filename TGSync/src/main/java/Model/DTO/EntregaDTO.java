package Model.DTO;

import java.time.LocalDate;
import java.util.Date;
public class EntregaDTO {
     private Long idEntrega;
     private Date dataEntrega;
     private String tituloEntrega;
     private Long idTurma;

     public EntregaDTO(Long idEntrega, Date dataEntrega, String tituloEntrega, Long idTurma) {
          this.idEntrega = idEntrega;
          this.dataEntrega = dataEntrega;
          this.tituloEntrega = tituloEntrega;
          this.idTurma = idTurma;
     }

     public EntregaDTO( Date dataEntrega, String tituloEntrega, Long idTurma) {
          this.dataEntrega = dataEntrega;
          this.tituloEntrega = tituloEntrega;
          this.idTurma = idTurma;
     }

     public EntregaDTO(Long idEntrega, Date dataEntrega, String tituloEntrega) {
          this.idEntrega = idEntrega;
          this.dataEntrega = dataEntrega;
          this.tituloEntrega = tituloEntrega;
     }
     public EntregaDTO(LocalDate dataEntrega, String tituloEntrega) {
          this.dataEntrega = dataEntrega;
          this.tituloEntrega = tituloEntrega;
     }

     public Long getIdEntrega(){
          return idEntrega;
     }
     public void setIdEntrega(Long idEntrega) {
          this.idEntrega = idEntrega;
     }
     public Date getDataEntrega(){
          return dataEntrega;
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
     @Override
     public String toString(){
          return String.format("Id Entrega: %s\nTitulo Entrega: %s\nData entrega: %s\nId turma: %s", this.idEntrega, this.tituloEntrega, this.dataEntrega, this.idTurma);
     }
}


