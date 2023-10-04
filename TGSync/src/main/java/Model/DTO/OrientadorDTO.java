package Model.DTO;

public class OrientadorDTO {
    private Integer id;
    private String nome;
    private String emailFatec;

    public OrientadorDTO(){
    }
    public OrientadorDTO(Integer id, String nome, String emailFatec) {
        this.id = id;
        this.nome = nome;
        this.emailFatec = emailFatec;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
