package br.com.casadocodigo.loja.conf;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.casadocodigo.loja.controllers.HomeController;
import br.com.casadocodigo.loja.daos.ProdutoDAO;

//Class de configuração especifico para a parte web.

//Fala para o spring que preciso usar a parte web mvc dele
@EnableWebMvc
//"basePackages" não é uma boa opção, pq se mudarmos o pacote, temos que lembrar de mudar aqui também.
//@ComponentScan(basePackages={"br.com.casadocodigo.loja.controllers"})
@ComponentScan(basePackageClasses={HomeController.class, ProdutoDAO.class}) //passando a controller com .class, o spring já sabe que tem que pegar o pacote que essa controle(HomeController) e esse dao(HomeController) está
public class AppWebConfiguration {
	
	/**
	 * Metodo resolvedor interno de views
	 * 
	 * @return
	 */
	@Bean //Fala que esse metodo é gerenciado pelo spring, toda classe gerenciada pelo spring é um bean
	public InternalResourceViewResolver internalResourceViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/"); //avisa ao spring que as views estão nessa pasta
		resolver.setSuffix(".jsp"); //avisa ao spring que nossas paginas são paginas .jsp, sendo assim não precisa informar sempre nos metodos da controller.
		
		return resolver;
	}
	
	//Arquivo de menssages
	@Bean //Fala que esse metodo é gerenciado pelo spring, toda classe gerenciada pelo spring é um bean
	public MessageSource messageSource(){
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("WEB-INF/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1);
		return messageSource;
	}
	
	@Bean //para formatar as datas com esse padrão
	public FormattingConversionService mvcConversionService(){
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		registrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
		registrar.registerFormatters(conversionService);
		return conversionService;
	}
}
