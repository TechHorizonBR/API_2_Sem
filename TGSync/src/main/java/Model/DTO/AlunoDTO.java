package Model.DTO;

//import java.util.LinkedList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AlunoDTO {
    private Long id;
    private String nome;
    private String emailPessoal;
    private String emailFatec;
    private Long idOrientador;
    private List<Integer> idTurmas = new LinkedList<>();

    public AlunoDTO(Long id, String nome, String emailPessoal, String emailFatec, Long idOrientador, List<Integer> idTurmas){
        this.id = id;
        this.nome = nome;
        this.emailPessoal = emailPessoal;
        this.emailFatec = emailFatec;
        this.idOrientador = idOrientador;
        this.idTurmas = idTurmas;
    }

    public AlunoDTO(String nome, String emailPessoal, String emailFatec, Long idOrientador, List<Integer> idTurmas){
        this.nome = nome;
        this.emailPessoal = emailPessoal;
        this.emailFatec = emailFatec;
        this.idOrientador = idOrientador;
        this.idTurmas = idTurmas;
    }

    public AlunoDTO(Long id, String nome, String emailPessoal, String emailFatec, Long idOrientador) {
        this.id = id;
        this.nome = nome;
        this.emailPessoal = emailPessoal;
        this.emailFatec = emailFatec;
        this.idOrientador = idOrientador;
    }
//    private List<Nota> listaNotas = new LinkedList<Nota>();

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getEmailPessoal(){
        return this.emailPessoal;
    }

    public void setEmailPessoal(String emailPessoal){
        this.emailPessoal = emailPessoal;
    }

    public String getEmailFatec(){
        return this.emailFatec;
    }

    public void setEmailFatec(String emailFatec){
        this.emailFatec = emailFatec;
    }

    public Long getIdOrientador(){
        return this.idOrientador;
    }

    public void setIdOrientador(Long idOrientador){
        this.idOrientador = idOrientador;
    }

    public List<Integer> getIdTurmas(){
        return this.idTurmas;
    }
    public void setIdTurma(Integer idTurmas){
        this.idTurmas.add(idTurmas);
    }

    public String toString(){
        return "\n"+this.nome+"\n"+this.emailFatec+"\n"+this.idTurmas+"\n";
    }

//    public void addNota(Nota nota){
//        listaNotas.add(nota);
//    }
//
//    public List<Nota> getNotas(){
//        return listaNotas;
//    }
}
