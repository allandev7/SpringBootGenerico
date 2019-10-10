package com.allan.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.allan.cursomc.domain.Categoria;
import com.allan.cursomc.domain.Produto;


@Repository
public interface  ProdutoRepository extends JpaRepository<Produto, Integer>{
	//OS CODIGOS COMENTADOS SAO COMO SERIA FEITO O PROCESSO USANDO O JPQL
	//NAO TEMOS ESSA NECESSIDADE POR CONTA DO PODER DOS PADROES DE NOMES DO SPRING DATA
	// REF. https://docs.spring.io/spring-data/jpa/docs/2.2.0.RELEASE/reference/html/#reference
	//@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(/*@Param("nome")*/String nome, /*@Param("categorias")*/List<Categoria>categorias, Pageable pageRequest);
}
