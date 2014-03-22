package com.walmart.pedido.modelo;

import org.codehaus.jackson.map.ObjectMapper;

public class Pedido {

	private String hamburguer;
	private String bebida;
	private String endereco;

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

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}


	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
