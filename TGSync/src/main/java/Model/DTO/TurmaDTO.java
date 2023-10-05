package Model.DTO;

public class TurmaDTO {
	private Long id;
	private int ano;
	private int semestre;
	private int disciplina;

	public TurmaDTO(Long id, int ano, int semestre, int disciplina){
		this.id = id;
		this.ano = ano;
		this.semestre = semestre;
		this.disciplina = disciplina;
	}
	public TurmaDTO(int ano, int semestre, int disciplina){
		this.ano = ano;
		this.semestre = semestre;
		this.disciplina = disciplina;
	}
	public TurmaDTO(){}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public int getSemestre() {
		return semestre;
	}
	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}
	public int getDisciplina() {
		return disciplina;
	}
	public void setDisciplina(int disciplina) {
		this.disciplina = disciplina;
	}
}
