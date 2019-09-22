package trabalho.razer.javaweb.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import trabalho.razer.javaweb.dto.ListaPedidoDTO;
import trabalho.razer.javaweb.dto.ListaPedidoWrapper;
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
		
		/*Código para teste de save da rotina de pedido*/
	
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
	
	/*Buscar cliente por CPF para cadastro de pedidos*/
	@PostMapping(value = "/buscacpf")
	private ModelAndView bucarCpf(String cpf) {
		Cliente busca;
		try {
			busca = clienteRepository.buscaPorCpf(cpf);
			if(busca!=null) {
				return MontagemModelAndView("/cadastropedido", busca, null, null);
			}else {
				List<String> msgErro = new ArrayList<String>();
				msgErro.add("CPF Não cadastrado");
				return MontagemModelAndView("/buscarcliente", null, null, msgErro);
			}
		} catch (Exception e) {
			List<String> msgErro = new ArrayList<String>();
			msgErro.add("Problema ao burcar o registro!");
			System.out.println("Erro: "+ e.getCause());
			return MontagemModelAndView("/buscarcliente", new Cliente(), null, msgErro);
		}
	}
	
	@RequestMapping(value = "/testelista", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	private ModelAndView listaitens(@RequestBody ListaPedidoWrapper wrapper) {
		
		
		
		System.out.println(wrapper);
		
		return null;
	}
	
	
	/*###############  REFATORAÇÃO ###################
	 * O trecho de código abaixo se repetia em todos métodos*/
	private ModelAndView MontagemModelAndView(String view, Cliente cliente,
			List<String> msgSucesso, List<String> msgErros) {
		
		//Defindo a url de retorno
		ModelAndView modelAndView = new ModelAndView(view);
		//Defindo o objeto manipulado pelo form
		modelAndView.addObject("clienteobj", cliente);
		//Carregando a lista para o datatable
		Iterable<Produto> produtos = produtoRepository.findAll();
		modelAndView.addObject("produtos", produtos);
		//carregando mensagens
		if(msgSucesso != null)
			modelAndView.addObject("msgSucesso", msgSucesso);
		if(msgErros != null)
			modelAndView.addObject("msg", msgErros);
		return modelAndView;
	}
	
}
