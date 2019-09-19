package trabalho.razer.javaweb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javassist.expr.NewArray;
import trabalho.razer.javaweb.model.Produto;
import trabalho.razer.javaweb.repository.ProdutoRepository;

@Controller
public class ProdutoController {
	
	/*Mesmos conceitos do controller de cliente. Anotações presentes em clientecontroller*/
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	
	@GetMapping(value = "/cadastroproduto")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("/cadastroproduto");
		modelAndView.addObject("produtoobj", new Produto());
		Iterable<Produto> produtos = produtoRepository.findAll();
		modelAndView.addObject("produtos", produtos);
		return modelAndView;
	}
	
	
	
	@PostMapping(value = "/salvarproduto")
	public ModelAndView salvar(@Valid Produto produto, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			/*Há erros de valçidação*/
			ModelAndView modelAndView = new ModelAndView("/cadastroproduto");
			modelAndView.addObject("produtoobj",produto);
			List<String> msg = new ArrayList<String>();
			bindingResult.getAllErrors().forEach(objError -> msg.add(objError.getDefaultMessage()));
			modelAndView.addObject("msg", msg);
			return modelAndView;
		}
		ModelAndView modelAndView = new ModelAndView("/cadastroproduto");
		produtoRepository.save(produto);
		modelAndView.addObject("produtoobj", new Produto());
		List<String> msgSucesso = new ArrayList<String>();
		msgSucesso.add("Produto salvo com sucesso!");
		modelAndView.addObject("msgSucesso", msgSucesso);
		return modelAndView;
	}
	
	
	
	
	
}
