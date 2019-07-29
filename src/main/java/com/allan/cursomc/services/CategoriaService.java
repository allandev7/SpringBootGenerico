package com.allan.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.allan.cursomc.domain.Categoria;
import com.allan.cursomc.dto.CategoriaDTO;
import com.allan.cursomc.repositories.CategoriaRepository;
import com.allan.cursomc.services.exception.DataIntegrityException;
import com.allan.cursomc.services.exception.ObjectNotFoundException;
;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id){
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado id:" +
									id+", Tipo: "+ Categoria.class.getName()));
	} 
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);// deixa nulo já que é um insert
		return repo.save(obj);
	}

	public Categoria update(Categoria obj) {
		find(obj.getId());//verifica se o id existe
		return repo.save(obj);
	}
	
	public void delete (Integer id) {
		find(id);//verifica se o id existe
		try {
			repo.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que contém"
					+ " produtos");
		}
		
	}

	public List<Categoria> findAll() {
		return repo.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage,
			String orderBy, String direction){
		
		
		PageRequest	pageRequest	= PageRequest.of(page, linesPerPage,
				Direction.valueOf(direction), orderBy);		
		return repo.findAll(pageRequest);
	}	
	
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}
	
}
