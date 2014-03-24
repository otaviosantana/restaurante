package com.walmart.pedido.modelo;

public class DetalheHamburguer {

	private TipoHamburguer tipoHamburguer;
	private Integer quantidade;
	private String observacao;

	public TipoHamburguer getTipoHamburguer() {
		return tipoHamburguer;
	}

	public void setTipoHamburguer(TipoHamburguer tipoHamburguer) {
		this.tipoHamburguer = tipoHamburguer;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Double getTotalPedidoHamburguer() {
		return tipoHamburguer.getPreco() * quantidade;
	}
}
