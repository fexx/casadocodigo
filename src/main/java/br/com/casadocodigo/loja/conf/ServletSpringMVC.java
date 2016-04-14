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
		//Faz com que os pring conheça o controlle, passa a class de configuração(AppWebConfiguration) para avisar quais os controller que o spring vai usar
		return new Class[]{AppWebConfiguration.class};
	}

	@Override
	protected String[] getServletMappings() {
		//Avisa ao servidor que a partir de "/" é o spring que vai atender as requisições, é o spring que vai mapear
		return new String[]{"/"};
	}

}
