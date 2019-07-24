package com.allan.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.allan.cursomc.domain.Categoria;
import com.allan.cursomc.domain.Cidade;
import com.allan.cursomc.domain.Cliente;
import com.allan.cursomc.domain.Endereco;
import com.allan.cursomc.domain.Estado;
import com.allan.cursomc.domain.Produto;
import com.allan.cursomc.domain.enums.TipoCliente;
import com.allan.cursomc.repositories.CategoriaRepository;
import com.allan.cursomc.repositories.CidadeRepository;
import com.allan.cursomc.repositories.ClienteRepository;
import com.allan.cursomc.repositories.EnderecoRepository;
import com.allan.cursomc.repositories.EstadoRepository;
import com.allan.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepo;
	@Autowired
	private ProdutoRepository produtoRepo;
	@Autowired
	private CidadeRepository cidadeRepo;
	@Autowired
	private EstadoRepository estadoRepo;
	@Autowired
	private ClienteRepository clienteRepo;
	@Autowired
	private EnderecoRepository enderecoRepo;
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador" , 2000);
		Produto p2 = new Produto(null, "Impressora" , 800);
		Produto p3 = new Produto(null, "Mouse" , 80);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().add(cat1);
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().add(cat1);
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().add(c1);
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maira@gmail.com", "16892469876",
				TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("25847691", "87407086"));
		
		Endereco e1 = new Endereco(null, "Rua flores", "300", "ap. 41b", 
				"Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida matos", "105", "Sala 800", 
				"Centro", "38220834",cli1,c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		
		estadoRepo.saveAll(Arrays.asList(est1,est2));
		cidadeRepo.saveAll(Arrays.asList(c1,c2,c3));
		categoriaRepo.saveAll(Arrays.asList(cat1,cat2));
		produtoRepo.saveAll(Arrays.asList(p1,p2,p3));
		clienteRepo.saveAll(Arrays.asList(cli1));
		enderecoRepo.saveAll(Arrays.asList(e1,e2));
	}

}
