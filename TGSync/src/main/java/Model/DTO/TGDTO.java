package Model.DTO;

public class TGDTO {

    private Long id;
    private String tipo;
    private String disciplina;
    private String problema;
    private String empresa;
    private Long idAluno;

    public TGDTO(Long id, String tipo, String disciplina, String problema, String empresa, Long idAluno) {
        this.id = id;
        this.tipo = tipo;
        this.disciplina = disciplina;
        this.problema = problema;
        this.empresa = empresa;
        this.idAluno = idAluno;}

    public TGDTO(String tipo, String disciplina, String problema, String empresa, Long idAluno) {
        this.tipo = tipo;
        this.disciplina = disciplina;
        this.problema = problema;
        this.empresa = empresa;
        this.idAluno = idAluno;}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Long getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }
}

