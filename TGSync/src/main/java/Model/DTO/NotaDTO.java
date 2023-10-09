package Model.DTO;

import java.util.LinkedList;
import java.util.List;

public class NotaDTO {
    private Long idNota;
    private String feedback;
    private List<Long> idEntrega = new LinkedList<>();
    private List<Long> id = new LinkedList<>(); //id = idAluno

    public NotaDTO(Long idNota, String feedback, LinkedList<Long> idEntrega, LinkedList<Long> id){
        this.idNota = idNota;
        this.feedback = feedback;
        this.idEntrega = idEntrega;
        this.id = id;
    }

    public NotaDTO(String feedback, LinkedList<Long>idEntrega, LinkedList<Long> id){
        this.feedback =  feedback;
        this.idEntrega = idEntrega;
        this.id = id;
    }

    public Long getIdNota() {
        return this.idNota;
    }
    public void setIdNota (Long idNota) {
        this.idNota = idNota;
    }
    public String getFeedback(){
        return this.feedback;
    }
    public void setFeedback (String feedback) {
        this.feedback = feedback;
    }
    public List<Long> getIdEntrega(){
        return this.idEntrega;
    }
    public void setIdEntrega(Long idEntrega){
        this.idEntrega.add(idEntrega);
    }
    public List<Long> getid (){
        return this.id;
    }
    public void setId (Long id){
        this.id.add(id);
    }

}


