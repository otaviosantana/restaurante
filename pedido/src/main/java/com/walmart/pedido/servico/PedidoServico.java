package com.walmart.pedido.servico;

import java.text.DecimalFormat;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.walmart.pedido.dao.PedidoDAO;
import com.walmart.pedido.modelo.Pedido;
import com.walmart.pedido.modelo.PedidoMesa;
import com.walmart.pedido.modelo.PedidoParceiro;

@Component
@Path("/krusty")
public class PedidoServico {

	@POST
	@Path("/pedidoParceiro")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response receberPedidoParceiro(PedidoParceiro pedidoParceiro) {
		return receberPedido(pedidoParceiro);
	}

	@POST
	@Path("/pedidoMesa")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response receberPedidoMesa(PedidoMesa pedidoMesa) {
		return receberPedido(pedidoMesa);
	}

	@GET
	@Path("/statusPedido/{numeroPedido}")
	public Response statusPedido(@PathParam("numeroPedido") Integer numeroPedido) {
		PedidoDAO pedidoDAO = getPedidoDAO();
		Pedido pedido = pedidoDAO.recuperaPedido(numeroPedido);
		if (pedido == null) {
			return Response.ok("Não encontramos o seu pedido").build();
		}
		return Response.ok(pedido.getStatusPedido().getDescricao()).build();
	}

	@GET
	@Path("/listaPedidos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarPedidos() {
		PedidoDAO pedidoDAO = getPedidoDAO();
		Collection<Pedido> pedidos = pedidoDAO.listaTodosPedidos();
		return Response.ok(pedidos, MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/atualizarPedido/{numeroPedido}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response atualizarPedido(@PathParam("numeroPedido") Integer numeroPedido) {
		PedidoDAO pedidoDAO = getPedidoDAO();
		Pedido pedido = pedidoDAO.atualizarStatusPedido(numeroPedido);
		if (pedido == null) {
			return Response.ok("Não encontramos o seu pedido").build();
		}
		return Response.ok(pedido.getStatusPedido().getDescricao()).build();
	}

	@GET
	@Path("/consultaSaldoPedido/{numeroPedido}")
	public Response consultaSaldoPedido(@PathParam("numeroPedido") Integer numeroPedido) {
		PedidoDAO pedidoDAO = getPedidoDAO();
		Pedido pedido = pedidoDAO.recuperaPedido(numeroPedido);
		if (pedido == null) {
			return Response.ok("Não encontramos o seu pedido").build();
		}
		DecimalFormat format = new DecimalFormat(".00");
		String resposta = "O saldo parcial do seu pedido é de: R$ " + format.format(pedido.calculaValorTotal());
		return Response.ok(resposta).build();
	}

	private PedidoDAO getPedidoDAO() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		PedidoDAO pedidoDAO = context.getBean(PedidoDAO.class);
		context.close();
		return pedidoDAO;
	}

	private Response receberPedido(Pedido pedido) {
		try {
			PedidoDAO pedidoDAO = getPedidoDAO();
			Integer numeroPedido = pedidoDAO.receberPedido(pedido);
			String resposta = "Pedido efetuado com sucesso. Número do pedido é " + numeroPedido;
			return Response.ok(resposta).build();
		} catch (Exception e) {
			return Response.ok("Houve um erro ao efetuar o seu pedido, tente novamente.").build();
		}
	}
}
