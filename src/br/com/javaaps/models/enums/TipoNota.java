package br.com.javaaps.models.enums;

public enum TipoNota {
	
	NP1("NP1"),
	NP2("NP2"),
	SUB("SUB"),
	EXAME("EXAME");
	
	private final String descricao; 
	
	TipoNota(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
