package br.com.javaaps.models;

import java.util.List;

public class Rendimento {
	
	private Aluno aluno;
	private Curso curso;
	private List<Nota> notas;
	
	public Rendimento(Aluno aluno, Curso curso, List<Nota> notas) {
		super();
		this.aluno = aluno;
		this.curso = curso;
		this.notas = notas;
	}
	
	/**
	 * Retorna se o aluno est� aprovado ou n�o
	 * @return
	 */
	public boolean isAprovado() {
		double mediaInicial = 0;
		
		double notaNP1 = 0;
		double notaNP2 = 0;
		double notaSub = 0;
		double notaExame = 0;
		
		for(Nota nota : getNotas()) {
			switch(nota.getTipo()) {
				case NP1:
					notaNP1 = nota.getValor();
					break;
				case NP2:
					notaNP2 = nota.getValor();
					break;
				case SUB:
					notaSub = nota.getValor();
					break;
				case EXAME:
					notaExame = nota.getValor();
					break;
			}
		}
		
		// Substitui a menor nota da NP1 ou NP2 pela nota da sub
		if (notaSub > notaNP1 || notaSub > notaNP2) {
			if (notaNP1 < notaNP2) { notaNP1 = notaSub; }
			else { notaNP2 = notaSub; }
		}
		
		mediaInicial = (notaNP1 + notaNP2) / 2;
	
		// Calcula aprova��o
		if (mediaInicial >= curso.getMedia()) {
			return true;
		} else {
			return ((mediaInicial + notaExame) / 2) >= 5;
		}
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public List<Nota> getNotas() {
		return notas;
	}

	public void setNotas(List<Nota> notas) {
		this.notas = notas;
	}
	
	public String toCSV() {
		String csv = "";
		
		csv += getAluno().getId();
		
		for(Nota nota : getNotas()) {
			csv += ";" + nota.getValor();
		}
		
		return csv;
	}
}
