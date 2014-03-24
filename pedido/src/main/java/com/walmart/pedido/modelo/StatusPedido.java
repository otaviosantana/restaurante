package com.walmart.pedido.modelo;

public enum StatusPedido {

	PENDENTE("Seu pedido está aguardando para ser preparado"),
	EM_PREPARO("Seu pedido está em preparo"),
	PRONTO("Seu pedido está pronto para ser entregue"),
	ENTREGUE("Seu pedido já foi entregue");

	private final String descricao;

	private StatusPedido(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
