package Model.Dto;

public class TGDto {

    private Str tema;
    private Str tipoTg;
    private Str emailAluno;


    public TGDto(TGDto tgDto) {
        this.tgDto = tgDto;
    }

    public Str getTema() {
        return tema;
    }

    public void setTema(Str tema) {
        this.tema = tema;
    }

    public Str getTipoTg() {
        return tipoTg;
    }

    public void setTipoTg(Str tipoTg) {
        this.tipoTg = tipoTg;
    }

    public Str getEmailAluno() {
        return emailAluno;
    }

    public void setEmailAluno(Str emailAluno) {
        this.emailAluno = emailAluno;
    }
}

