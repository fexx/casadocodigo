package br.com.casadocodigo.loja.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;
import br.com.casadocodigo.loja.validation.ProdutoValidation;

@Controller
@RequestMapping("/produtos") //para não ter que coloca sempre nos metodos o contexto produtos. exemplo: "produtos/form"
public class ProdutosController {
	
	@Autowired
	private ProdutoDAO produtoDAO;
	
	@Autowired
	private FileSaver fileSaver;
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.addValidators(new ProdutoValidation());
	}
	
	@RequestMapping("/form")
	public ModelAndView form(Produto produto){ //Na hora de cadastrar um prouto, e tiver algum erro, ele devolver para o formulario, e para pegar os ultimos dados preenchidos no formulario, preciso passar o porduto no form(Produto produto) 
		ModelAndView modelAndView = new ModelAndView("produtos/form");//informe atraves do construtor, para qual pagina deve ir 
		modelAndView.addObject("tipos", TipoPreco.values()); // manda os valores da controller para a view "form.jsp"
		return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.POST) // BindingResult tem que ser depois da classe que vai ser validada, nesse caso ele ficou depois da classe produto.
	public ModelAndView gravar(MultipartFile sumario, @Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes){ //faz o bind com o mesmo nome que colocamos no "name" da jsp "form.jsp"
		System.out.println(sumario.getOriginalFilename()); //exibe o nome do arquivo
		
		if(result.hasErrors()){
			return form(produto);
		}
		
		String path = fileSaver.write("arquivos-sumario", sumario);
		produto.setSumarioPath(path);
		produtoDAO.gravar(produto);
		//adiciona essa informação no segundo request, ou seja no request da lista. Ele mantém o request, sem isso ele não manda a mensagem e passa ele como parametro na url do navegador
		//temos que receber como parametro no metodo para usalo.
		redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");
		//faz um redirect para a tela de produtos. Ele não deixa guardar os ultimos dados cadastrado no navegador, 
		//ou seja ao tecla F5 ele não faz um novo post(um re-submit) com os dados antigos salvo, o que fazia respeti os dados do produto. isso é solucionado com o redirect
		return new ModelAndView("redirect:produtos");
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView listar() {
		List<Produto> produtos = produtoDAO.listar();
		ModelAndView modelAndView = new ModelAndView("produtos/lista");
		modelAndView.addObject("produtos", produtos);
		
		return modelAndView;
	}
	
	@RequestMapping("/detalhe/{id}")
	public ModelAndView detalhe(@PathVariable("id") Integer id){
		ModelAndView modelAndView = new ModelAndView("produtos/detalhe");//mandar o usuario para essa tela
		Produto produto = produtoDAO.find(id);
		modelAndView.addObject("produto", produto);
		return modelAndView;
	}
}