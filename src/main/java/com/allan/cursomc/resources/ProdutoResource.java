package com.allan.cursomc.resources;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.allan.cursomc.domain.Produto;
import com.allan.cursomc.dto.ProdutoDTO;
import com.allan.cursomc.resources.utils.URL;
import com.allan.cursomc.services.ProdutoService;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> listar(@PathVariable Integer id) throws ObjectNotFoundException{
		Produto cat1 = service.find(id);
		System.out.println(id);
		return ResponseEntity.ok().body(cat1);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value= "nome", defaultValue = "")String nome,
			@RequestParam(value= "categorias", defaultValue = "")String categorias,
			@RequestParam(value= "page", defaultValue = "0")Integer page,
			@RequestParam(value= "linesPerPage", defaultValue = "24")Integer linesPerPage,
			@RequestParam(value= "orderBy", defaultValue = "nome")String orderBy,
			@RequestParam(value= "direction", defaultValue = "ASC")String direction) {
		
		List<Integer> ids = URL.decodeIntList(categorias);
		String nomeDecoded = URL.decodeParam(nome);
		Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		
		Page<ProdutoDTO> listDTO = list.map(//Page não precisa do stream pois já é java 8
				obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
}
