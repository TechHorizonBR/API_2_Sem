package Model.DTO;

import java.util.LinkedList;

public class AlunoDTO {
    private int id;
    private String nome;
    private String email;
    private String emailFatec;
    private int idOrientador;
    private int idTurma;

//    private List<Nota> listaNotas = new LinkedList<Nota>();

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmailFatec(){
        return this.emailFatec;
    }

    public void setEmailFatec(String emailFatec){
        this.emailFatec = emailFatec;
    }

    public int getIdOrientador(){
        return this.idOrientador;
    }

    public void setIdOrientador(int idOrientador){
        this.idOrientador = idOrientador;
    }

    public int getIdTurma(){
        return this.idTurma;
    }
    public void setIdTurma(int idTurma){
        this.idTurma = idTurma;
    }

//    public void addNota(Nota nota){
//        listaNotas.add(nota);
//    }
//
//    public List<Nota> getNotas(){
//        return listaNotas;
//    }
}
