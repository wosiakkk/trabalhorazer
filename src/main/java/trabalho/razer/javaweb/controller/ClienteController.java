package trabalho.razer.javaweb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
	
	/*Método para salvar um cliente. Ao final recarrega a lista de clientes atualizada.
	 *A anotação @Valid ativa  a validação implementada pelas anotações no model de cliente, e
	 *o objeto BindingResult irá retornar os erros das validações */
	@RequestMapping(method = RequestMethod.POST, value = "/salvar")
	public ModelAndView salvar(@Valid Cliente cliente, BindingResult bindingResult) {
		
		/*Verificando inicialmente se houve erros de validação*/
		if(bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView("/cadastrocliente");
			Iterable<Cliente> clientes = clienteRepository.findAll();
			modelAndView.addObject("clientes", clientes);
			/*Como houe erro, carrega para a mesma tela com o mesmo obj preenchido inicialmente pelo usuário*/
			modelAndView.addObject("clienteobj", cliente);
			/*Criando uma lista para armazenar as mensagens de erros*/
			List<String> msg = new ArrayList<>();
			/*varrendo a lista de erros do binding e setando em um objeto de erro com lambda (ObjectErro é o retorno do métodos
			 * getAllErrors)*/
			bindingResult.getAllErrors().forEach(objectError-> msg.add(objectError.getDefaultMessage()));
			//add a lista de erros
			modelAndView.addObject("msg", msg);	
			//retornando
			return modelAndView;
		}
		/*caso n dê erros de validações o método executa como anteriormente,
		 * entretanto ainda pode haver um erro de violação de cpf, no qual deve ser único no bd,
		 * com isso o método save do jpa é tratado com try catch*/
		try {
			clienteRepository.save(cliente);
		} catch (DataIntegrityViolationException e) {
			//imprimendo a causa do erro no console para verificação
			System.out.println(e.getCause());
			//realizando um novo reotnro com a mensagem de erro
			ModelAndView modelAndView = new ModelAndView("/cadastrocliente");
			Iterable<Cliente> clientes = clienteRepository.findAll();
			modelAndView.addObject("clientes", clientes);
			modelAndView.addObject("clienteobj", cliente);
			List<String> msg = new ArrayList<>();
			msg.add("O CPF informado já foi cadastrado.");
			modelAndView.addObject("msg", msg);	
			return modelAndView;
		}
			
		/*Carregando a lista de usuários após salvar*/
		ModelAndView modelAndView = new ModelAndView("/cadastrocliente");
		Iterable<Cliente> clientes = clienteRepository.findAll();
		modelAndView.addObject("clientes", clientes);
		/*Evitando erro de objeto nulo(necessário após realizar o método de edição)*/
		modelAndView.addObject("clienteobj", new Cliente());
		/*Mensagemd e sucesso*/
		List<String> msgSucesso = new ArrayList<String>();
		msgSucesso.add("Cliente salvo com sucesso!");
		modelAndView.addObject("msgSucesso", msgSucesso);
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
		List<String> msgSucesso = new ArrayList<String>();
		msgSucesso.add("Cliente deletado com sucesso!");
		modelAndView.addObject("msgSucesso", msgSucesso);
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
