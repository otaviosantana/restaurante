package com.walmart.pedido.servico;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Response;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.walmart.pedido.dao.PedidoDAO;
import com.walmart.pedido.modelo.DetalheBebida;
import com.walmart.pedido.modelo.DetalheHamburguer;
import com.walmart.pedido.modelo.Pedido;
import com.walmart.pedido.modelo.PedidoMesa;
import com.walmart.pedido.modelo.PedidoParceiro;
import com.walmart.pedido.modelo.StatusPedido;
import com.walmart.pedido.modelo.TipoBebida;
import com.walmart.pedido.modelo.TipoHamburguer;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
	PedidoServico.class,
	ClassPathXmlApplicationContext.class
})
public class PedidoServicoTest {

	private static final int NUMERO_MESA = 5;
	private static final String PEDIDO_EFETUADO_SUCESSO = "Pedido efetuado com sucesso. Número do pedido é 10";
	private static final String ERRO_RECEBER_PEDIDO = "Houve um erro ao efetuar o seu pedido, tente novamente.";
	private static final String BEANS_XML = "beans.xml";
	private static final Integer NUMERO_PEDIDO = 10;

	@Test
	public void testReceberPedidoParceiro() throws Exception {
		PedidoParceiro pedido = new PedidoParceiro();
		PedidoDAO pedidoDAOMock = createPedidoDAOMock(pedido);
		ClassPathXmlApplicationContext contextMock = createClassPathXmlApplicationContextMock(pedidoDAOMock);
		PedidoServico pedidoServico = new PedidoServico();
		Response result = pedidoServico.receberPedidoParceiro(pedido);
		Assert.assertEquals(PEDIDO_EFETUADO_SUCESSO, result.getEntity().toString());
		EasyMock.verify(pedidoDAOMock);
		EasyMock.verify(contextMock);
		PowerMock.verify(ClassPathXmlApplicationContext.class);
	}

	@Test
	public void testReceberPedidoParceiroComErro() throws Exception {
		PedidoParceiro pedido = new PedidoParceiro();
		ClassPathXmlApplicationContext contextMock = createClassPathXmlApplicationContextMockWithException();
		PedidoServico pedidoServico = new PedidoServico();
		Response result = pedidoServico.receberPedidoParceiro(pedido);
		Assert.assertEquals(ERRO_RECEBER_PEDIDO, result.getEntity().toString());
		EasyMock.verify(contextMock);
		PowerMock.verify(ClassPathXmlApplicationContext.class);
	}

	@Test
	public void testReceberPedidoMesa() throws Exception {
		PedidoMesa pedido = new PedidoMesa();
		PedidoDAO pedidoDAOMock = createPedidoDAOMock(pedido);
		ClassPathXmlApplicationContext contextMock = createClassPathXmlApplicationContextMock(pedidoDAOMock);
		PedidoServico pedidoServico = new PedidoServico();
		Response result = pedidoServico.receberPedidoMesa(pedido);
		Assert.assertEquals(PEDIDO_EFETUADO_SUCESSO, result.getEntity().toString());
		EasyMock.verify(pedidoDAOMock);
		EasyMock.verify(contextMock);
		PowerMock.verify(ClassPathXmlApplicationContext.class);
	}

	@Test
	public void testReceberPedidoMesaComErro() throws Exception {
		PedidoMesa pedido = new PedidoMesa();
		ClassPathXmlApplicationContext contextMock = createClassPathXmlApplicationContextMockWithException();
		PedidoServico pedidoServico = new PedidoServico();
		Response result = pedidoServico.receberPedidoMesa(pedido);
		Assert.assertEquals(ERRO_RECEBER_PEDIDO, result.getEntity().toString());
		EasyMock.verify(contextMock);
		PowerMock.verify(ClassPathXmlApplicationContext.class);
	}

	@Test
	public void testStatusPedidoInexistente() throws Exception {
		PedidoDAO pedidoDAOMock = createPedidoDAOMockParaRecuperar(null);
		ClassPathXmlApplicationContext contextMock = createClassPathXmlApplicationContextMock(pedidoDAOMock);
		PedidoServico pedidoServico = new PedidoServico();
		Response result = pedidoServico.statusPedido(NUMERO_PEDIDO);
		Assert.assertEquals("Não encontramos o seu pedido " + NUMERO_PEDIDO, result.getEntity().toString());
		EasyMock.verify(pedidoDAOMock);
		EasyMock.verify(contextMock);
		PowerMock.verify(ClassPathXmlApplicationContext.class);
	}

	@Test
	public void testStatusPedido() throws Exception {
		Pedido pedido = new PedidoParceiro();
		PedidoDAO pedidoDAOMock = createPedidoDAOMockParaRecuperar(pedido);
		ClassPathXmlApplicationContext contextMock = createClassPathXmlApplicationContextMock(pedidoDAOMock);
		PedidoServico pedidoServico = new PedidoServico();
		Response result = pedidoServico.statusPedido(NUMERO_PEDIDO);
		Assert.assertEquals("Seu pedido está aguardando para ser preparado", result.getEntity().toString());
		EasyMock.verify(pedidoDAOMock);
		EasyMock.verify(contextMock);
		PowerMock.verify(ClassPathXmlApplicationContext.class);
	}

	@Test
	public void testListarPedidosVazio() throws Exception {
		PedidoDAO pedidoDAOMock = createPedidoDAOMockListarTodos(new ArrayList<Pedido>());
		ClassPathXmlApplicationContext contextMock = createClassPathXmlApplicationContextMock(pedidoDAOMock);
		PedidoServico pedidoServico = new PedidoServico();
		Response result = pedidoServico.listarPedidos();
		Assert.assertEquals("{\"resposta\":\"Não há pedidos a serem preparados\"", result.getEntity().toString());
		EasyMock.verify(contextMock);
		PowerMock.verify(ClassPathXmlApplicationContext.class);
		EasyMock.verify(pedidoDAOMock);
	}

	@Test
	public void testListarPedidos() throws Exception {
		Pedido pedidoMesa = getPedidoMesa();
		Pedido pedidoParceiro = getPedidoParceiro();
		Collection<Pedido> pedidos = getPedidos(pedidoMesa, pedidoParceiro);
		PedidoDAO pedidoDAOMock = createPedidoDAOMockListarTodos(pedidos);
		ClassPathXmlApplicationContext contextMock = createClassPathXmlApplicationContextMock(pedidoDAOMock);
		PedidoServico pedidoServico = new PedidoServico();
		Response result = pedidoServico.listarPedidos();
		String esperado = "[{\"numeroPedido\":" + pedidoMesa.getNumeroPedido() + ",\"detalhesHamburguer\":[{\"tipoHamburguer\":\"CHEESE_BURGUER\",\"quantidade\":1,\"observacao\":\"observacao\",\"totalPedidoHamburguer\":12.0},{\"tipoHamburguer\":\"CHEESE_SALADA\",\"quantidade\":2,\"observacao\":\"observacao\",\"totalPedidoHamburguer\":30.0},{\"tipoHamburguer\":\"SIMPLES\",\"quantidade\":3,\"observacao\":\"observacao\",\"totalPedidoHamburguer\":30.0}],\"detalhesBebida\":[{\"tipoBebida\":\"AGUA\",\"quantidade\":1,\"precoBebida\":3.0},{\"tipoBebida\":\"CERVEJA\",\"quantidade\":2,\"precoBebida\":10.0},{\"tipoBebida\":\"REFRIGERANTE_2L\",\"quantidade\":3,\"precoBebida\":21.0},{\"tipoBebida\":\"REFRIGERANTE_LATA\",\"quantidade\":4,\"precoBebida\":14.0}],\"statusPedido\":\"PENDENTE\",\"numeroMesa\":5}, {\"numeroPedido\":" + pedidoParceiro.getNumeroPedido() + ",\"detalhesHamburguer\":[{\"tipoHamburguer\":\"CHEESE_BURGUER\",\"quantidade\":1,\"observacao\":\"observacao\",\"totalPedidoHamburguer\":12.0},{\"tipoHamburguer\":\"CHEESE_SALADA\",\"quantidade\":2,\"observacao\":\"observacao\",\"totalPedidoHamburguer\":30.0},{\"tipoHamburguer\":\"SIMPLES\",\"quantidade\":3,\"observacao\":\"observacao\",\"totalPedidoHamburguer\":30.0}],\"detalhesBebida\":[{\"tipoBebida\":\"AGUA\",\"quantidade\":1,\"precoBebida\":3.0},{\"tipoBebida\":\"CERVEJA\",\"quantidade\":2,\"precoBebida\":10.0},{\"tipoBebida\":\"REFRIGERANTE_2L\",\"quantidade\":3,\"precoBebida\":21.0},{\"tipoBebida\":\"REFRIGERANTE_LATA\",\"quantidade\":4,\"precoBebida\":14.0}],\"statusPedido\":\"PENDENTE\",\"endereco\":\"Rua do Endereco\",\"solicitante\":\"Solicitante\"}]";
		Assert.assertEquals(esperado, result.getEntity().toString());
		EasyMock.verify(contextMock);
		PowerMock.verify(ClassPathXmlApplicationContext.class);
		EasyMock.verify(pedidoDAOMock);
	}

	private Collection<Pedido> getPedidos(Pedido pedidoMesa,
			Pedido pedidoParceiro) {
		Collection<Pedido> pedidos = new ArrayList<Pedido>();
		pedidos.add(pedidoMesa);
		pedidos.add(pedidoParceiro);
		return pedidos;
	}

	@Test
	public void testAtualizarPedidoNaoEncontrado() throws Exception {
		PedidoDAO pedidoDAOMock = createPedidoDAOMockAtualizarPedido(null);
		ClassPathXmlApplicationContext contextMock = createClassPathXmlApplicationContextMock(pedidoDAOMock);
		PedidoServico pedidoServico = new PedidoServico();
		Response result = pedidoServico.atualizarPedido(NUMERO_PEDIDO);
		Assert.assertEquals("Não encontramos o seu pedido " + NUMERO_PEDIDO, result.getEntity().toString());
		EasyMock.verify(pedidoDAOMock);
		PowerMock.verify(ClassPathXmlApplicationContext.class);
		EasyMock.verify(contextMock);
	}

	@Test
	public void testAtualizarPedido() throws Exception {
		Pedido pedido = new PedidoParceiro();
		pedido.setStatusPedido(StatusPedido.ENTREGUE);
		PedidoDAO pedidoDAOMock = createPedidoDAOMockAtualizarPedido(pedido);
		ClassPathXmlApplicationContext contextMock = createClassPathXmlApplicationContextMock(pedidoDAOMock);
		PedidoServico pedidoServico = new PedidoServico();
		Response result = pedidoServico.atualizarPedido(NUMERO_PEDIDO);
		Assert.assertEquals("O status do pedido " + NUMERO_PEDIDO + " foi alterado: Seu pedido já foi entregue", result.getEntity().toString());
		EasyMock.verify(pedidoDAOMock);
		PowerMock.verify(ClassPathXmlApplicationContext.class);
		EasyMock.verify(contextMock);
	}

	@Test
	public void testConsultaSaldoPedidoNaoEncontrado() throws Exception {
		PedidoDAO pedidoDAOMock = createPedidoDAOMockParaRecuperar(null);
		ClassPathXmlApplicationContext contextMock = createClassPathXmlApplicationContextMock(pedidoDAOMock);
		PedidoServico pedidoServico = new PedidoServico();
		Response result = pedidoServico.consultaSaldoPedido(NUMERO_PEDIDO);
		Assert.assertEquals("Não encontramos o seu pedido " + NUMERO_PEDIDO, result.getEntity().toString());
		EasyMock.verify(pedidoDAOMock);
		PowerMock.verify(ClassPathXmlApplicationContext.class);
		EasyMock.verify(contextMock);
	}

	@Test
	public void testConsultaSaldoPedido() throws Exception {
		Pedido pedido = getPedidoMesa();
		PedidoDAO pedidoDAOMock = createPedidoDAOMockParaRecuperar(pedido);
		ClassPathXmlApplicationContext contextMock = createClassPathXmlApplicationContextMock(pedidoDAOMock);
		PedidoServico pedidoServico = new PedidoServico();
		Response result = pedidoServico.consultaSaldoPedido(NUMERO_PEDIDO);
		Assert.assertEquals("O saldo parcial do seu pedido " + NUMERO_PEDIDO + " é de: R$ 120.00", result.getEntity().toString());
		EasyMock.verify(pedidoDAOMock);
		PowerMock.verify(ClassPathXmlApplicationContext.class);
		EasyMock.verify(contextMock);
	}

	private Pedido getPedidoMesa() {
		PedidoMesa pedido = new PedidoMesa();
		pedido.setDetalhesHamburguer(getDetalhesHamburguer());
		pedido.setDetalhesBebida(getDetalhesBebida());
		pedido.setNumeroMesa(NUMERO_MESA);
		return pedido;
	}

	private Pedido getPedidoParceiro() {
		PedidoParceiro pedido = new PedidoParceiro();
		pedido.setDetalhesHamburguer(getDetalhesHamburguer());
		pedido.setDetalhesBebida(getDetalhesBebida());
		pedido.setEndereco("Rua do Endereco");
		pedido.setSolicitante("Solicitante");
		return pedido;
	}

	private List<DetalheBebida> getDetalhesBebida() {
		List<DetalheBebida> detalhes = new ArrayList<>();
		detalhes.add(getDetalheBebida(1, TipoBebida.AGUA));
		detalhes.add(getDetalheBebida(2, TipoBebida.CERVEJA));
		detalhes.add(getDetalheBebida(3, TipoBebida.REFRIGERANTE_2L));
		detalhes.add(getDetalheBebida(4, TipoBebida.REFRIGERANTE_LATA));
		return detalhes;
	}

	private DetalheBebida getDetalheBebida(Integer quantidade, TipoBebida tipo) {
		DetalheBebida detalhe = new DetalheBebida();
		detalhe.setQuantidade(quantidade);
		detalhe.setTipoBebida(tipo);
		return detalhe;
	}

	private List<DetalheHamburguer> getDetalhesHamburguer() {
		List<DetalheHamburguer> detalhes = new ArrayList<>();
		detalhes.add(getDetalheHamburguer(1, TipoHamburguer.CHEESE_BURGUER));
		detalhes.add(getDetalheHamburguer(2, TipoHamburguer.CHEESE_SALADA));
		detalhes.add(getDetalheHamburguer(3, TipoHamburguer.SIMPLES));
		return detalhes;
	}

	private DetalheHamburguer getDetalheHamburguer(int quantidade, TipoHamburguer tipo) {
		DetalheHamburguer detalhe = new DetalheHamburguer();
		detalhe.setQuantidade(quantidade);
		detalhe.setTipoHamburguer(tipo);
		detalhe.setObservacao("observacao");
		return detalhe;
	}

	private PedidoDAO createPedidoDAOMockAtualizarPedido(Pedido pedido) {
		PedidoDAO mock = EasyMock.createMock(PedidoDAO.class);
		EasyMock.expect(mock.atualizarStatusPedido(NUMERO_PEDIDO)).andReturn(pedido);
		EasyMock.replay(mock);
		return mock;
	}

	private PedidoDAO createPedidoDAOMockListarTodos(Collection<Pedido> pedidos) {
		PedidoDAO mock = EasyMock.createMock(PedidoDAO.class);
		EasyMock.expect(mock.listaTodosPedidos()).andReturn(pedidos);
		EasyMock.replay(mock);
		return mock;
	}

	private PedidoDAO createPedidoDAOMockParaRecuperar(Pedido pedido) {
		PedidoDAO mock = EasyMock.createMock(PedidoDAO.class);
		EasyMock.expect(mock.recuperaPedido(NUMERO_PEDIDO)).andReturn(pedido);
		EasyMock.replay(mock);
		return mock;
	}

	private ClassPathXmlApplicationContext createClassPathXmlApplicationContextMock(PedidoDAO pedidoDAO) throws Exception {
		ClassPathXmlApplicationContext mock = PowerMock.createMockAndExpectNew(ClassPathXmlApplicationContext.class, new Class[]{String.class}, BEANS_XML);
		EasyMock.expect(mock.getBean(PedidoDAO.class)).andReturn(pedidoDAO);
		mock.close();
		EasyMock.replay(mock);
		PowerMock.replay(ClassPathXmlApplicationContext.class);
		return mock;
	}

	private ClassPathXmlApplicationContext createClassPathXmlApplicationContextMockWithException() throws Exception {
		ClassPathXmlApplicationContext mock = PowerMock.createMockAndExpectNew(ClassPathXmlApplicationContext.class, new Class[]{String.class}, BEANS_XML);
		EasyMock.expect(mock.getBean(PedidoDAO.class)).andThrow(new ApplicationContextException(""));
		EasyMock.replay(mock);
		PowerMock.replay(ClassPathXmlApplicationContext.class);
		return mock;
	}

	private PedidoDAO createPedidoDAOMock(Pedido pedido) {
		PedidoDAO mock = EasyMock.createMock(PedidoDAO.class);
		EasyMock.expect(mock.receberPedido(pedido)).andReturn(NUMERO_PEDIDO);
		EasyMock.replay(mock);
		return mock;
	}
}
