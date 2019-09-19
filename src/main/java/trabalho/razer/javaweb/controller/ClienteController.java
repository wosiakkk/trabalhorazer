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
		return MontagemModelAndView("/cadastrocliente", new Cliente(), null, null);
	}
	
	/*Método para salvar um cliente. Ao final recarrega a lista de clientes atualizada.
	 *A anotação @Valid ativa  a validação implementada pelas anotações no model de cliente, e
	 *o objeto BindingResult irá retornar os erros das validações */
	@RequestMapping(method = RequestMethod.POST, value = "/salvar")
	public ModelAndView salvar(@Valid Cliente cliente, BindingResult bindingResult) {
		//erros de validações
		if(bindingResult.hasErrors()) {
			List<String> msg = new ArrayList<>();
			bindingResult.getAllErrors().forEach(objectError-> msg.add(objectError.getDefaultMessage()));
			return MontagemModelAndView("/cadastrocliente", cliente, null, msg);
		}

		try {
			clienteRepository.save(cliente);
			List<String> msgSucesso = new ArrayList<String>();
			msgSucesso.add("Cliente salvo com sucesso!");
			return MontagemModelAndView("/cadastrocliente", new Cliente(), msgSucesso, null);
		} catch (DataIntegrityViolationException e) {
			System.out.println(e.getCause());
			List<String> msg = new ArrayList<>();
			msg.add("O CPF informado já foi cadastrado.");
			System.out.println("Erro: "+ e.getCause());
			return MontagemModelAndView("/cadastrocliente", cliente, null, msg);
		}
		
	}
	
	/*Método para edição. Basicamente vai pegar a id do datatable referente ao clique do link,
	 * pesquisar o usuário e setar no form de save para ser editado e ir ao método de salvar novamente*/
	@GetMapping("/editarcliente/{idcliente}")
	public ModelAndView editar(@PathVariable("idcliente") Long idcliente) {
		Optional<Cliente> cliente;
		try {
			cliente = clienteRepository.findById(idcliente);
			return MontagemModelAndView("/cadastrocliente", cliente.get(), null, null);
		} catch (Exception e) {
			List<String> msgErros = new ArrayList<String>();
			msgErros.add("Erro ao editar esse registro");
			System.out.println("Erro: "+ e.getCause());
			return MontagemModelAndView("/cadastrocliente", new Cliente(), null, msgErros);
		}
	}
	
	/*Método para exclusão de cliente, que vai interceptar a requisição get com o id
	 * passado como parâmetro através do link de exclusão da datatable de clientes*/
	@GetMapping("/excluircliente/{idcliente}")
	public ModelAndView excluir(@PathVariable("idcliente") Long idcliente) {
		
		try {
			clienteRepository.deleteById(idcliente);
			List<String> msgSucesso = new ArrayList<String>();
			msgSucesso.add("Cliente deletado com sucesso!");
			return MontagemModelAndView("/cadastrocliente", new Cliente(), msgSucesso, null);
		} catch (Exception e) {
			List<String> msgErro = new ArrayList<String>();
			msgErro.add("Problema ao deletar o registro!");
			System.out.println("Erro: "+ e.getCause());
			return MontagemModelAndView("/cadastrocliente", new Cliente(), null, msgErro);
		}
		
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
		Iterable<Cliente> clientes = clienteRepository.findAll();
		modelAndView.addObject("clientes", clientes);
		//carregando mensagens
		if(msgSucesso != null)
			modelAndView.addObject("msgSucesso", msgSucesso);
		if(msgErros != null)
			modelAndView.addObject("msg", msgErros);
		return modelAndView;
	}
}
