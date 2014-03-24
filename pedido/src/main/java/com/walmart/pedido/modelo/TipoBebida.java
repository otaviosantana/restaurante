package com.walmart.pedido.modelo;

public enum TipoBebida {

	AGUA(3.0),
	REFRIGERANTE_LATA(3.5),
	REFRIGERANTE_2L(7.0),
	CERVEJA(5.0);

	private final Double preco;

	private TipoBebida(Double preco) {
		this.preco = preco;
	}

	public Double getPreco() {
		return preco;
	}
}
