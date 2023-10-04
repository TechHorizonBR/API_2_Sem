package Model.DTO;

//import java.util.LinkedList;

public class AlunoDTO {
    private Long id;
    private String nome;
    private String emailPessoal;
    private String emailFatec;
    private Long idOrientador;
    private Long idTurma;

    public AlunoDTO(Long id, String nome, String emailPessoal, String emailFatec, Long idOrientador, Long idTurma){
        this.id = id;
        this.nome = nome;
        this.emailPessoal = emailPessoal;
        this.emailFatec = emailFatec;
        this.idOrientador = idOrientador;
        this.idTurma = idTurma;
    }

    public AlunoDTO(String nome, String emailPessoal, String emailFatec, Long idOrientador, Long idTurma){
        this.nome = nome;
        this.emailPessoal = emailPessoal;
        this.emailFatec = emailFatec;
        this.idOrientador = idOrientador;
        this.idTurma = idTurma;
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

    public Long getIdTurma(){
        return this.idTurma;
    }
    public void setIdTurma(Long idTurma){
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
