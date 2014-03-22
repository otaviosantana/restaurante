package com.walmart.pedido.servico;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.walmart.pedido.modelo.Pedido;

@Path("/krusty")
public class PedidoServico {

	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response recebePedido(Pedido pedido) {
		System.out.println("Pedido Recebido: " + pedido);
		return Response.ok().build();
	}
}
