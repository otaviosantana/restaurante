package com.walmart.pedido.servico;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.walmart.pedido.modelo.PedidoMesa;
import com.walmart.pedido.modelo.PedidoParceiro;

@Path("/krusty")
public class PedidoServico {

	@POST
	@Path("/pedidoParceiro")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response receberPedidoParceiro(PedidoParceiro pedidoParceiro) {
		System.out.println("Pedido Recebido: " + pedidoParceiro);
		return Response.ok().build();
	}

	@POST
	@Path("/pedidoMesa")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response receberPedidoMesa(PedidoMesa pedidoMesa) {
		System.out.println("Pedido Recebido: " + pedidoMesa);
		return Response.ok().build();
	}

	@GET
	@Path("/statusPedido/{numeroPedido}")
	public Response statusPedido(@PathParam("numeroPedido") Integer numeroPedido) {
		System.out.println(numeroPedido);
		return Response.ok().build();
	}

	@GET
	@Path("listaPedidos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarPedidos() {
		System.out.println("Buscar pedidos");
		return Response.ok("{\"teste\":|\"ok\"}", MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("atualizarPedido/{numeroPedido}")
	public Response atualizarPedido(@PathParam("numeroPedido") Integer numeroPedido) {
		System.out.println(numeroPedido);
		return Response.ok().build();
	}

	@GET
	@Path("consultaPedido/{numeroMesa}")
	public Response consultaPedido(@PathParam("numeroMesa") Integer numeroMesa) {
		System.out.println("consulta status do pedido - " + numeroMesa);
		return Response.ok().build();
	}

	@GET
	@Path("consultaSaldoPedido/{numeroMesa}")
	public Response consultaSaldoPedido(@PathParam("numeroMesa") Integer numeroMesa) {
		System.out.println("consulta saldo pedido " + numeroMesa);
		return Response.ok().build();
	}
}
