package br.com.casadocodigo.loja.models;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component //deixamos ele disponivel para ser injeto pelo spring em outras classes, usando o @Autowired
@Scope(value=WebApplicationContext.SCOPE_SESSION) //todo bean do spring é um singleton, estamos mudando o scopo default do spring que é o scope_application para scope_session ou seja, cada navegar vai ter uma sessão
public class CarrinhoCompras {

	private Map<CarrinhoItem, Integer> itens = new LinkedHashMap<>();
	
	public void add(CarrinhoItem item){
		itens.put(item, getQuantidade(item) + 1);
	}

	private int getQuantidade(CarrinhoItem item) {
		if(!itens.containsKey(item)){
			itens.put(item, 0);
		}
		return itens.get(item);
	}
	
	public int getQuantidade(){
		return itens.values().stream().reduce(0,(proximo, acumulador) -> proximo + acumulador);
	}
}
