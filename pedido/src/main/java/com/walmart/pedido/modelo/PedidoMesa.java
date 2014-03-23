package com.walmart.pedido.modelo;

public class PedidoMesa extends Pedido {

	private Integer numeroMesa;
	private Integer totalMesa;

	public Integer getNumeroMesa() {
		return numeroMesa;
	}

	public void setNumeroMesa(Integer numeroMesa) {
		this.numeroMesa = numeroMesa;
	}

	public Integer getTotalMesa() {
		return totalMesa;
	}

	public void setTotalMesa(Integer totalMesa) {
		this.totalMesa = totalMesa;
	}
}
