package com.walmart.pedido.modelo;

public enum TipoHamburguer {

	SIMPLES(10.0),
	CHEESE_BURGUER(12.0),
	CHEESE_SALADA(15.0);

	private final Double preco;

	private TipoHamburguer(Double preco) {
		this.preco = preco;
	}

	public Double getPreco() {
		return preco;
	}
}
