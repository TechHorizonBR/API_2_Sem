package Model.DTO;

public class OrientadorDTO {
    private Long id;
    private String nome;
    private String email;

    public OrientadorDTO(){
    }
    public OrientadorDTO(Long id, String nome, String emailFatec) {
        this.id = id;
        this.nome = nome;
        this.email = emailFatec;
    }

    public OrientadorDTO(String nome, String emailFatec) {
        this.nome = nome;
        this.email = emailFatec;
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
        return email;
    }

    public void setEmail(String emailFatec) {
        this.email = emailFatec;
    }

    @Override
    public String toString(){
        return String.format("Nome: %s, Id: %s", this.nome, this.id);
    }
}

