package trabalho.razer.javaweb.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import trabalho.razer.javaweb.model.Produto;
import trabalho.razer.javaweb.repository.ProdutoRepository;

@Controller
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@PostMapping(value = "/salvarproduto")
	public ModelAndView salvar(@Valid Produto produto, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			
			
		}
		ModelAndView modelAndView = new ModelAndView("/cadastroproduto");
		produtoRepository.save(produto);
		
		return modelAndView;
	}
	
	
	
	
	
}
