package Model.DTO;

public class OrientadorDTO {
    private Long id;
    private String nome;
    private String emailFatec;

    public OrientadorDTO(){
    }
    public OrientadorDTO(Long id, String nome, String emailFatec) {
        this.id = id;
        this.nome = nome;
        this.emailFatec = emailFatec;
    }

    public OrientadorDTO(String nome, String emailFatec) {
        this.nome = nome;
        this.emailFatec = emailFatec;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return emailFatec;
    }

    public void setEmail(String emailFatec) {
        this.emailFatec = emailFatec;
    }
}
