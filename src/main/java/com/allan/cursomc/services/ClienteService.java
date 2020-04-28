package com.allan.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.allan.cursomc.domain.Cidade;
import com.allan.cursomc.domain.Cliente;
import com.allan.cursomc.domain.Endereco;
import com.allan.cursomc.domain.enums.Perfil;
import com.allan.cursomc.domain.enums.TipoCliente;
import com.allan.cursomc.dto.ClienteDTO;
import com.allan.cursomc.dto.ClienteNewDto;
import com.allan.cursomc.repositories.CidadeRepository;
import com.allan.cursomc.repositories.ClienteRepository;
import com.allan.cursomc.repositories.EnderecoRepository;
import com.allan.cursomc.security.UserSS;
import com.allan.cursomc.services.exception.AuthorizationException;
import com.allan.cursomc.services.exception.DataIntegrityException;
import com.allan.cursomc.services.exception.ObjectNotFoundException;
;

@Service
public class ClienteService {
	
	@Autowired
	private CidadeRepository cidadeRepo;
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepo;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);// deixa nulo já que é um insert
		obj = repo.save(obj);
		enderecoRepo.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente find(Integer id){
		
		UserSS user = UserService.getCurrentUser();
		if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado id:" +
									id+", Tipo: "+ Cliente.class.getName()));
	} 
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());//verifica se o id existe
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete (Integer id) {
		find(id);//verifica se o id existe
		try {
			repo.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente que contém"
					+ " pedidos");
		}
		
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage,
			String orderBy, String direction){
		
		
		PageRequest	pageRequest	= PageRequest.of(page, linesPerPage,
				Direction.valueOf(direction), orderBy);		
		return repo.findAll(pageRequest);
	}	
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDto objDTO) {
		System.out.println("**************  "+ objDTO.getCidadeId());
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()), pe.encode(objDTO.getSenha()));
		Cidade cid = cidadeRepo.getOne(objDTO.getCidadeId());
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), 
					objDTO.getBairro(), objDTO.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if (objDTO.getTelefone2()!=null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if (objDTO.getTelefone3()!=null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		return cli;	
	}
	
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	
	
}
