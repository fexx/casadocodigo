package br.com.casadocodigo.loja.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component //deixamos ele disponivel para ser injeto pelo spring em outras classes, usando o @Autowired
@Scope(value=WebApplicationContext.SCOPE_SESSION) //todo bean do spring é um singleton, estamos mudando o scopo default do spring que é o scope_application para scope_session ou seja, cada navegar vai ter uma sessão
public class CarrinhoCompras implements Serializable { //sempre que usar um escopo de sessão(scope_session) temos que implementar o serializable, pq o servidor guarda o estado do objeto em arquivo, e depois retorno a usar o mesmo objeto

	private static final long serialVersionUID = -2106272778536854933L;
	
	private Map<CarrinhoItem, Integer> itens = new LinkedHashMap<>();
	
	public void add(CarrinhoItem item){
		itens.put(item, getQuantidade(item) + 1);
	}

	public Integer getQuantidade(CarrinhoItem item) {
		if(!itens.containsKey(item)){
			itens.put(item, 0);
		}
		return itens.get(item);
	}
	
	public int getQuantidade(){
		return itens.values().stream().reduce(0,(proximo, acumulador) -> proximo + acumulador);
	}
	
	public Collection<CarrinhoItem> getItens() {
		return itens.keySet();
	}
	
	public BigDecimal getTotal(CarrinhoItem item){
		return item.getTotal(getQuantidade(item));
	}

	public BigDecimal getTotal(){
		BigDecimal total = BigDecimal.ZERO;
		for(CarrinhoItem item: itens.keySet()){
			total = total.add(getTotal(item));
		}
		return total;
	}
}
