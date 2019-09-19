package trabalho.razer.javaweb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import trabalho.razer.javaweb.model.Produto;
import trabalho.razer.javaweb.repository.ProdutoRepository;

@Controller
public class ProdutoController {
	
	/*Mesmos conceitos do controller de cliente. Anotações presentes em clientecontroller*/
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	/*MÉTODO INICIAL DA PÁGINA*/
	@GetMapping(value = "/cadastroproduto")
	public ModelAndView inicio() {
		return MontagemModelAndView("/cadastroproduto", new Produto(), null, null);
	}
	
	
	/*MÉTODO DE SAVE*/
	@PostMapping(value = "/salvarproduto")
	public ModelAndView salvar(@Valid Produto produto, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			/*Há erros de validação, setando as mensagens em list*/		
			List<String> msg = new ArrayList<String>();
			bindingResult.getAllErrors().forEach(objError -> msg.add(objError.getDefaultMessage()));
			
			return MontagemModelAndView("/cadastroproduto", produto, null, msg);
		}
		try {
			produtoRepository.save(produto);
			List<String> msgSucesso = new ArrayList<String>();
			msgSucesso.add("Produto salvo com sucesso!");
			return MontagemModelAndView("/cadastroproduto", new Produto(), msgSucesso, null);
		} catch (Exception e) {
			List<String> msg = new ArrayList<String>();
			msg.add("Problema ao salvar este registro");
			return MontagemModelAndView("/cadastroproduto", produto, null, msg);
		}
	}
	
	/*MÉTODO DE EDITAR*/
	@GetMapping(value = "/editarproduto/{idproduto}")
	public ModelAndView editar(@PathVariable("idproduto") Long idproduto) {
		
		Optional<Produto> produtoBuscado;
		try {
			produtoBuscado = produtoRepository.findById(idproduto);
			return MontagemModelAndView("/cadastroproduto", produtoBuscado.get(), null, null);
		} catch (Exception e) {
			List<String> erro = new ArrayList<String>();
			erro.add("Erro ao editar esse registro");
			return MontagemModelAndView("/cadastroproduto", new Produto(), null,erro);
		}
	}
	
	/*MÉTODO DE EXCLUSÃO*/
	@GetMapping(value = "/excluirproduto/{idproduto}")
	public ModelAndView excluir(@PathVariable("idproduto") Long idproduto) {
		try {
			produtoRepository.deleteById(idproduto);
		} catch (Exception e) {
			List<String> msgErros = new ArrayList<String>();
			msgErros.add("Problema ao excluir esse registro!");
			return MontagemModelAndView("/cadastroproduto", new Produto(), null , msgErros);
		}
		List<String> msgSucesso = new ArrayList<String>();
		msgSucesso.add("Produto excluido com sucesso!");
		return MontagemModelAndView("/cadastroproduto", new Produto(), msgSucesso, null);
	}

	/*###############  REFATORAÇÃO ###################
	 * O trecho de código abaixo se repetia em todos métodos*/
	private ModelAndView MontagemModelAndView(String view, Produto produto,
			List<String> msgSucesso, List<String> msgErros) {
		//Defindo a url de retorno
		ModelAndView modelAndView = new ModelAndView(view);
		//Defindo o objeto manipulado pelo form
		modelAndView.addObject("produtoobj", produto);
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
