package Model.DTO;

public class AlunoDTO {

    private int id;
    private String nome;
    private String emailPessoal;
    private String emailFatec;
    private int idOrientador;
    private int idTurma;

    public AlunoDTO(int id, String nome, String emailPessoal, String emailFatec, int idOrientador){
        this.id = id;
        this.nome = nome;
        this.emailPessoal = emailPessoal;
        this.emailFatec = emailFatec;
        this.idOrientador = idOrientador;
    }
    public AlunoDTO(String nome, String emailPessoal, String emailFatec, int idOrientador){
        this.nome = nome;
        this.emailPessoal = emailPessoal;
        this.emailFatec = emailFatec;
        this.idOrientador = idOrientador;
    }

    // private List<Nota> listaNotas;

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
