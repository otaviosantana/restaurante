package com.walmart.pedido.servico;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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
		try {
			PedidoDAO pedidoDAO = getPedidoDAO();
			Collection<Pedido> pedidos = pedidoDAO.listaTodosPedidos();
			if (pedidos.isEmpty()) {
				System.out.println("Teste");
				return Response.ok("{\"resposta\":\"Não há pedidos a serem preparados\"", MediaType.APPLICATION_JSON).build();
			}
			List<String> resultado = getResultado(pedidos);
			return Response.ok(resultado, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok("Erro ao chamar o servidor").build();
		}
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
		System.out.println("Recebendo pedido");
		try {
			PedidoDAO pedidoDAO = getPedidoDAO();
			Integer numeroPedido = pedidoDAO.receberPedido(pedido);
			System.out.println("Numero pedido " + numeroPedido);
			String resposta = "Pedido efetuado com sucesso. Número do pedido é " + numeroPedido;
			return Response.ok(resposta).build();
		} catch (Exception e) {
			return Response.ok("Houve um erro ao efetuar o seu pedido, tente novamente.").build();
		}
	}

	private List<String> getResultado(Collection<Pedido> pedidos) {
		List<String> resultado = new ArrayList<>();
		for (Pedido pedido : pedidos) {
			try {
				resultado.add(new ObjectMapper().writeValueAsString(pedido));
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultado;
	}
}
