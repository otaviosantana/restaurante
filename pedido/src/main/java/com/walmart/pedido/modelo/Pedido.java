package com.walmart.pedido.modelo;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Pedido implements Comparable<Pedido> {

	private static AtomicInteger numeroUltimoPedido = new AtomicInteger(0);

	private Integer numeroPedido;
	private List<DetalheHamburguer> detalhesHamburguer;
	private List<DetalheBebida> detalhesBebida;
	private StatusPedido statusPedido = StatusPedido.PENDENTE;

	public Pedido() {
		this.numeroPedido = getProximoNumeroPedido();
	}

	public Integer getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(Integer numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public List<DetalheHamburguer> getDetalhesHamburguer() {
		return detalhesHamburguer;
	}

	public void setDetalhesHamburguer(List<DetalheHamburguer> hamburguer) {
		this.detalhesHamburguer = hamburguer;
	}

	public List<DetalheBebida> getDetalhesBebida() {
		return detalhesBebida;
	}

	public void setDetalhesBebida(List<DetalheBebida> bebida) {
		this.detalhesBebida = bebida;
	}

	public StatusPedido getStatusPedido() {
		return statusPedido;
	}

	public void setStatusPedido(StatusPedido statusPedido) {
		this.statusPedido = statusPedido;
	}

	public void setProximoStatusPedido() {
		if (this.statusPedido == StatusPedido.PENDENTE) {
			this.statusPedido = StatusPedido.EM_PREPARO;
		} else if (this.statusPedido == StatusPedido.EM_PREPARO) {
			this.statusPedido = StatusPedido.PRONTO;
		} else if (this.statusPedido == StatusPedido.PRONTO) {
			this.statusPedido = StatusPedido.ENTREGUE;
		}
	}

	protected Integer getProximoNumeroPedido() {
		return numeroUltimoPedido.incrementAndGet();
	}

	@Override
	public int compareTo(Pedido p) {
		return this.getNumeroPedido().compareTo(p.getNumeroPedido());
	}

	public Double calculaValorTotal() {
		Double precoTotal = 0.0;
		for (DetalheHamburguer detalheHamburguer : detalhesHamburguer) {
			precoTotal += detalheHamburguer.getTotalPedidoHamburguer();
		}
		for (DetalheBebida detalheBebida : detalhesBebida) {
			precoTotal += detalheBebida.getPrecoBebida();
		}
		return precoTotal;
	}
}
