package br.com.casadocodigo.loja.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;

@Controller
@RequestMapping("produtos") //para não ter que coloca sempre nos metodos o contexto produtos. exemplo: "produtos/form"
public class ProdutosController {
	
	@Autowired
	private ProdutoDAO produtoDAO;
	
	@RequestMapping("/form")
	public ModelAndView form(){
		ModelAndView modelAndView = new ModelAndView("produtos/form");//informe atraves do construtor, para qual pagina deve ir 
		modelAndView.addObject("tipos", TipoPreco.values()); // manda os valores da controller para a view "form.jsp"
		return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String grava(Produto produto){ //faz o bind com o mesmo nome que colocamos no "name" da jsp "form.jsp"
		System.out.println(produto);
		produtoDAO.gravar(produto);
		return "produtos/ok";
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView listar() {
		List<Produto> produtos = produtoDAO.listar();
		ModelAndView modelAndView = new ModelAndView("produtos/lista");
		modelAndView.addObject("produtos", produtos);
		
		return modelAndView;
	}
}
