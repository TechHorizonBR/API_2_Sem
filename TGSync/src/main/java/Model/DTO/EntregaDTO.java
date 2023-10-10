package Model.DTO;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EntregaDTO {
     private Long idEntrega;
     private Date dataEntrega;
     private String tituloEntrega;
     private List<Integer> idTurmas = new LinkedList<>();

     public EntregaDTO(Long idEntrega, Date dataEntrega, String tituloEntrega, LinkedList<Integer> idTurmas) {
          this.idEntrega = idEntrega;
          this.dataEntrega = dataEntrega;
          this.tituloEntrega = tituloEntrega;
          this.idTurmas = idTurmas;
     }

     public EntregaDTO( Date dataEntrega, String tituloEntrega, LinkedList<Integer> idTurmas) {
          this.dataEntrega = dataEntrega;
          this.tituloEntrega = tituloEntrega;
          this.idTurmas = idTurmas;
     }

     public EntregaDTO(Long idEntrega, Date dataEntrega, String tituloEntrega) {
          this.idEntrega = idEntrega;
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
     public List<Integer> getIdTurmas() {
          return this.idTurmas;
     }
     public void setIdTurmas (Integer idTurmas) {
          this.idTurmas.add(idTurmas);
     }
}


