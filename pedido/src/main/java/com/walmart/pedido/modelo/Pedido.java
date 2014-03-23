package com.walmart.pedido.modelo;

public abstract class Pedido {

	private String hamburguer;
	private String bebida;

	public String getHamburguer() {
		return hamburguer;
	}

	public void setHamburguer(String hamburguer) {
		this.hamburguer = hamburguer;
	}

	public String getBebida() {
		return bebida;
	}

	public void setBebida(String bebida) {
		this.bebida = bebida;
	}
}
