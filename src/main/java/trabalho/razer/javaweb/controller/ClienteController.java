package trabalho.razer.javaweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	
	@RequestMapping(method = RequestMethod.GET, value = "/teste")
	public String inicio() {
		return "teste";
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/salvar")
	public String salvar(Cliente cliente) {
		clienteRepository.save(cliente);
		return "/home";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/listaclientes")
	public ModelAndView listarClientes() {
		ModelAndView modelAndView = new ModelAndView("/teste");
		Iterable<Cliente> clientes = clienteRepository.findAll();
		modelAndView.addObject("clientes", clientes);
		return modelAndView;
	}
}
