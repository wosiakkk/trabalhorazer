package trabalho.razer.javaweb.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

	/*Controle de acesso a página de  cadastro. Evitar erro na página ao acessa-la 
	 * diretamente na url*/
	@GetMapping(value = "/cadastropedido")
	private ModelAndView controlePaginaCadastroPedido() {
		List<String> msgErro = new ArrayList<String>();
		msgErro.add("Você não pode acessar a página de cadastro sem antes selecionar um cliente!");
		return MontagemModelAndView("/buscarcliente", new Cliente(), null, msgErro);
	}
	
	@PostMapping(value = "/buscapedidos")
	private ModelAndView buscarPedidos(String cpf) {
		
		Cliente cliente;
		try {
			//buscando o cleinte informado
			cliente = clienteRepository.buscaPorCpf(cpf);
			if(cliente != null) {
				//verificando se ele possui pedidos
				int numeroDePedidos = pedidoRepository.numeroDePedido(cliente.getId());
				if(numeroDePedidos > 0) {
					//possui pedidos
				}else {
					List<String> msgErro = new ArrayList<String>();
					msgErro.add("Este usuário não possui pedidos cadastrados.");
					return MontagemModelAndView("/buscarpedidos", null, null, msgErro);
				}
			}else {
				List<String> msgErro = new ArrayList<String>();
				msgErro.add("CPF Não cadastrado");
				return MontagemModelAndView("/buscarpedidos", null, null, msgErro);
			}
		} catch (Exception e) {
			List<String> msgErro = new ArrayList<String>();
			msgErro.add("Problema ao burcar o registro!");
			System.out.println("Erro: "+ e.getCause());
			return MontagemModelAndView("/buscarpedidos", new Cliente(), null, msgErro);
		}
		
		
		
		//int numeroDePedidos = pedidoRepository.numeroDePedido(idcliente);
	/*	if(numeroDePedidos == 0) {
			clienteRepository.deleteById(idcliente);
			List<String> msgSucesso = new ArrayList<String>();
			msgSucesso.add("Cliente deletado com sucesso!");
			return MontagemModelAndView("/cadastrocliente", new Cliente(), msgSucesso, null);
		}*/
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
	
	@RequestMapping(value = "/salvarpedido", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	private @ResponseBody String listaitens(@RequestBody ListaPedidoWrapper wrapper) {
		
		Cliente cliente = null;
		Produto produto =null;
		List<ItemDoPedido> itens = new ArrayList<>();
		Pedido pedido = new Pedido();
		for (ListaPedidoDTO listaDto : wrapper.getListaitens()) {
			if(cliente ==null) {
				cliente = clienteRepository.buscaPorCpf(listaDto.getClientecpf());
				Calendar c = Calendar.getInstance();
				Date data = c.getTime();
				
				pedido.setCliente(cliente);
				pedido.setData(data);
				pedido = pedidoRepository.save(pedido);
			}
			produto = produtoRepository.findById(Long.parseLong(listaDto.getIdproduto())).get();
			Long qtd = Long.parseLong(listaDto.getQuantidade());
			ItemDoPedido item = new ItemDoPedido(pedido, produto, qtd);
			itens.add(item);
		}
		
		pedido.setItens(itens);

		pedidoRepository.save(pedido);	
		
		System.out.println(pedido);
		
		return "sucesso";
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
