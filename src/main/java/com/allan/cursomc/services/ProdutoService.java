package com.allan.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.allan.cursomc.domain.Categoria;
import com.allan.cursomc.domain.Produto;
import com.allan.cursomc.repositories.CategoriaRepository;
import com.allan.cursomc.repositories.ProdutoRepository;
import com.allan.cursomc.services.exception.ObjectNotFoundException;;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository cateRepo;

	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado id:" + id + ", Tipo: " + Produto.class.getName()));
	}

	public Page<Produto> search (String nome, List<Integer> ids, Integer page, Integer linesPerPage,
			String orderBy, String direction){
	
		PageRequest	pageRequest	= PageRequest.of(page, linesPerPage,
				Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = cateRepo.findAllById(ids); 
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);		

	}
}
