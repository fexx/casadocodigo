package br.com.casadocodigo.loja.conf;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Classe que avisa ao servidor que o spring que vai tomar de conta das requisições
 * Pode ser feito via .xml também.
 * extend a classe que é responsavel ao inicializar o spring "AbstractAnnotationConfigDispatcherServletInitializer"
 * 
 * @author Fernando
 *
 */
public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		//Faz com que os spring as conheça as classes controlles atraves da classe de configuração(AppWebConfiguration). Essa classe avisar quais os controller que o spring vai usar
		//Faz com que os spring as conheça as classes daos atraves da classe de configuração(JPAConfiguration). Essa classe avisar quais os daos que o spring vai usar
		return new Class[]{AppWebConfiguration.class, JPAConfiguration.class};
	}

	@Override
	protected String[] getServletMappings() {
		//Avisa ao servidor que a partir de "/" é o spring que vai atender as requisições, é o spring que vai mapear
		return new String[]{"/"};
	}

}
