package com.walmart.pedido.dao;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.walmart.pedido.modelo.Pedido;

@Component
public class PedidoDAO {

	private static Map<Integer, Pedido> pedidos = new HashMap<>();
	private static Collection<Pedido> listaPedidos = Collections.synchronizedCollection(new TreeSet<Pedido>()); 

	public Pedido atualizarStatusPedido(Integer numeroPedido) {
		Pedido pedido = pedidos.get(numeroPedido);
		if (pedido != null) {
			pedido.setProximoStatusPedido();
			return pedido;
		}
		return null;
	}

	public Integer receberPedido(Pedido pedido) {
		listaPedidos.add(pedido);
		pedidos.put(pedido.getNumeroPedido(), pedido);
		return pedido.getNumeroPedido();
	}

	public Pedido recuperaPedido(Integer numeroPedido) {
		return pedidos.get(numeroPedido);
	}

	public Collection<Pedido> listaTodosPedidos() {
		return listaPedidos;
	}
}
