package br.com.javaaps.models;

import java.util.List;
import java.util.stream.Collectors;

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
		return getMedia() >= 5;
	}
	
	public double getMedia() {
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
	
		// Cálculo da média final
		if (mediaInicial >= curso.getMedia()) {
			return mediaInicial;
		} else {
			return (mediaInicial + notaExame) / 2;
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
	
	public String toString(boolean imprimeCurso, boolean imprimeAluno) {
		StringBuilder obj = new StringBuilder();
		Curso curso = getCurso();
		Aluno aluno = getAluno();
		
		if (imprimeCurso) {
			obj.append(String.format("Curso: %s - Nível: %s - Ano: %d \n", curso.getNome(), curso.getNivel(), curso.getAno()));
		}
		
		if (imprimeAluno) {
			obj.append(String.format("Aluno: %s \n", aluno.getNome()));
		}
		
		obj.append(String.format("Notas (NP1, NP2, SUB e Exame): %s \n", String.join(" - ", 
				getNotas().stream().map(nota -> String.valueOf(nota.getValor())).collect(Collectors.toList()))));
		
		obj.append(String.format("Média: %.2f \n", getMedia()));
		obj.append(String.format("Status: %s", isAprovado() ? "APROVADO" : "REPROVADO"));
		
		return obj.toString();
	}
}
