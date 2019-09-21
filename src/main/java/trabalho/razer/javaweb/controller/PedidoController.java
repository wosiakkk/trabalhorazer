package trabalho.razer.javaweb.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import trabalho.razer.javaweb.model.Cliente;
import trabalho.razer.javaweb.model.ItemDoPedido;
import trabalho.razer.javaweb.model.Pedido;
import trabalho.razer.javaweb.model.Produto;
import trabalho.razer.javaweb.repository.ClienteRepository;
import trabalho.razer.javaweb.repository.PedidoRepository;
import trabalho.razer.javaweb.repository.ProdutoRepository;

@Controller
public class PedidoController {
	
	/*Injeção do repository*/
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping(value = "/cadastrarpedido")
	private ModelAndView salvar() {
		
	
		Optional<Cliente> teste = clienteRepository.findById(2L);
		
		Optional<Produto> p1 = produtoRepository.findById(21L);
		Optional<Produto> p2 = produtoRepository.findById(23L);
		
		Calendar c = Calendar.getInstance();
		Date data = c.getTime();
		Pedido pedido = new Pedido();
		pedido.setCliente(teste.get());
		pedido.setData(data);
		
		pedido = pedidoRepository.save(pedido);
		
		ItemDoPedido i1 = new ItemDoPedido(pedido,p1.get(), 2L);
		ItemDoPedido i2 = new ItemDoPedido(pedido,p2.get(), 5L);
		List<ItemDoPedido> itens = new ArrayList<ItemDoPedido>();
		itens.add(i1);
		itens.add(i2);
		pedido.setItens(itens);
		
		pedidoRepository.save(pedido);	
		
		System.out.println(pedido);
		
		return null;
	}
	
}
