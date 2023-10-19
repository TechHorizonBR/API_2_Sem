package Model.DTO;

import java.util.LinkedList;
import java.util.List;

public class NotaDTO {
    private Long id;
    private String feedback;

    private Double valor;

    private Long idAluno;

    private Long idEntrega;

    public NotaDTO(Long id, String feedback, Double valor, Long idAluno, Long idEntrega){
        this.id = id;
        this.feedback = feedback;
        this.valor = valor;
        this.idAluno = idAluno;
        this.idEntrega = idEntrega;

    }

    public NotaDTO(String feedback, Double valor, Long idAluno, Long idEntrega) {
        this.feedback = feedback;
        this.valor = valor;
        this.idAluno = idAluno;
        this.idEntrega = idEntrega;
    }

    public Long getId() {
        return this.id;
    }
    public void setId (Long id) {
        this.id = id;
    }
    public String getFeedback(){
        return this.feedback;
    }
    public void setFeedback (String feedback) {
        this.feedback = feedback;
    }
    public Double getValor(){
        return this.valor;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }
    public Long getIdAluno(){
        return this.idAluno;
    }
    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }

    public Long getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(Long idEntrega) {
        this.idEntrega = idEntrega;
    }
}