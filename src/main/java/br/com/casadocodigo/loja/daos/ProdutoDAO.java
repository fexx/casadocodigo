package br.com.casadocodigo.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.models.Produto;

@Repository //informa que o spring que vai gerencia essa classe
@Transactional //informamos que essa classe vai ser transacional, e que o spring vai gerenciar -> para isso usamos a anotação do spring: import org.springframework.transaction.annotation.Transactional;
public class ProdutoDAO {
	
	@PersistenceContext
	private EntityManager manager;
	
	public void gravar(Produto produto){
		manager.persist(produto);
	}

	public List<Produto> listar() {
		return manager.createQuery("select p from Produto p", Produto.class).getResultList();
	}

	public Produto find(Integer id) {
		return manager.createQuery("select distinct(p) from Produto p join fetch p.precos preco where p.id = :id",Produto.class).setParameter("id", id).getSingleResult(); //fazendo o join fetch, estamos falando ao hibernate na hora de buscar os produtos, tbm buscar os preços
	}

}
