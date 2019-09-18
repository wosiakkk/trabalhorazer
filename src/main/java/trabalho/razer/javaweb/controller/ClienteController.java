package trabalho.razer.javaweb.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import trabalho.razer.javaweb.model.Cliente;
import trabalho.razer.javaweb.repository.ClienteRepository;

@Controller
public class ClienteController {

	/*Injetando o repository*/
	@Autowired
	private ClienteRepository clienteRepository;
	
	/*Método para executar na primeira requisição na página de cadastro de cliente.
	 * Com isso a lista de clientes atuais já é carregada*/
	@RequestMapping(method = RequestMethod.GET, value = "/cadastrocliente")
	public ModelAndView inicio() {
		/*Carregando a lista de usuários ao acessar pela primera vez a página do cliente*/
		ModelAndView modelAndView = new ModelAndView("/cadastrocliente");
		Iterable<Cliente> clientes = clienteRepository.findAll();
		modelAndView.addObject("clientes", clientes);
		/*Evitasndo erro de objeto nulo(necessário após realizar o método de edição)*/
		modelAndView.addObject("clienteobj", new Cliente());
		return modelAndView;
	}
	
	/*Método para salvar um cliente. Ao final recarrega a lista de clientes atualizada*/
	@RequestMapping(method = RequestMethod.POST, value = "/salvar")
	public ModelAndView salvar(Cliente cliente) {
		clienteRepository.save(cliente);
		/*Carregando a lista de usuários após salvar*/
		ModelAndView modelAndView = new ModelAndView("/cadastrocliente");
		Iterable<Cliente> clientes = clienteRepository.findAll();
		modelAndView.addObject("clientes", clientes);
		/*Evitando erro de objeto nulo(necessário após realizar o método de edição)*/
		modelAndView.addObject("clienteobj", new Cliente());
		return modelAndView;
	}
	
	/*Método para edição. Basicamente vai pegar a id do datatable referente ao clique do link,
	 * pesquisar o usuário e setar no form de save para ser editado e ir ao método de salvar novamente*/
	@GetMapping("/editarcliente/{idcliente}")
	public ModelAndView editar(@PathVariable("idcliente") Long idcliente) {
		/*Buscando o cliente referente ao id passado por parâmetro*/
		Optional<Cliente> cliente = clienteRepository.findById(idcliente);
		/*Definindo a página de retorno, no qual será a mesma página*/
		ModelAndView modelAndView = new ModelAndView("/cadastrocliente");
		/*Add o objeto carregado no obj modelandview no formulário de cadastro.*/
		modelAndView.addObject("clienteobj", cliente.get());
		/*Carregando a lista de usuários após editar*/
		Iterable<Cliente> clientes = clienteRepository.findAll();
		modelAndView.addObject("clientes", clientes);
		/*retornando*/
		return modelAndView;
	}
	
	/*Método para exclusão de cliente, que vai interceptar a requisição get com o id
	 * passado como parâmetro através do link de exclusão da datatable de clientes*/
	@GetMapping("/excluircliente/{idcliente}")
	public ModelAndView excluir(@PathVariable("idcliente") Long idcliente) {
		/*Efetuando a exlusão*/
		clienteRepository.deleteById(idcliente);
		/*Definindo a página de retorno, no qual será a mesma página*/
		ModelAndView modelAndView = new ModelAndView("/cadastrocliente");
		/*Evitando erro de objeto nulo(necessário após realizar o método de edição)*/
		modelAndView.addObject("clienteobj", new Cliente());
		/*Carregando a lista de usuários após editar*/
		Iterable<Cliente> clientes = clienteRepository.findAll();
		modelAndView.addObject("clientes", clientes);
		/*retornando*/
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/listaclientes")
	public ModelAndView listarClientes() {
		ModelAndView modelAndView = new ModelAndView("/cadastrocliente");
		Iterable<Cliente> clientes = clienteRepository.findAll();
		modelAndView.addObject("clientes", clientes);
		return modelAndView;
	}
}
