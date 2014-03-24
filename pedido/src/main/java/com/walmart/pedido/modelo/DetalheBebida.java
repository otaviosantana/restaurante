package com.walmart.pedido.modelo;

public class DetalheBebida {

	private TipoBebida tipoBebida;
	private Integer quantidade;

	public TipoBebida getTipoBebida() {
		return tipoBebida;
	}

	public void setTipoBebida(TipoBebida tipoBebida) {
		this.tipoBebida = tipoBebida;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPrecoBebida() {
		return tipoBebida.getPreco() * quantidade;
	}
}
