package br.com.javaaps.models;

import br.com.javaaps.models.enums.TipoNota;

public class Nota {
	
	private double valor;
	private TipoNota tipo;
	
	public Nota() {
		
	}
	
	public Nota(double valor, TipoNota tipo) {
		super();
		this.valor = valor;
		this.tipo = tipo;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public TipoNota getTipo() {
		return tipo;
	}

	public void setTipo(TipoNota tipo) {
		this.tipo = tipo;
	}
}
