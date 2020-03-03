package com.allan.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allan.cursomc.domain.Categoria;
import com.allan.cursomc.domain.Cidade;
import com.allan.cursomc.domain.Cliente;
import com.allan.cursomc.domain.Endereco;
import com.allan.cursomc.domain.Estado;
import com.allan.cursomc.domain.ItemPedido;
import com.allan.cursomc.domain.Pagamento;
import com.allan.cursomc.domain.PagamentoComBoleto;
import com.allan.cursomc.domain.PagamentoComCartao;
import com.allan.cursomc.domain.Pedido;
import com.allan.cursomc.domain.Produto;
import com.allan.cursomc.domain.enums.EstadoPagamento;
import com.allan.cursomc.domain.enums.TipoCliente;
import com.allan.cursomc.repositories.CategoriaRepository;
import com.allan.cursomc.repositories.CidadeRepository;
import com.allan.cursomc.repositories.ClienteRepository;
import com.allan.cursomc.repositories.EnderecoRepository;
import com.allan.cursomc.repositories.EstadoRepository;
import com.allan.cursomc.repositories.ItemPedidoRepository;
import com.allan.cursomc.repositories.PagamentoRepository;
import com.allan.cursomc.repositories.PedidoRepository;
import com.allan.cursomc.repositories.ProdutoRepository;

@Service
public class DBService {
	
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
	@Autowired
	private PedidoRepository pedidoRepo;
	@Autowired
	private PagamentoRepository pagamentoRepo;
	@Autowired
	private ItemPedidoRepository itemPedRepo;
	
	
	public void instantiateTestDatabase() throws ParseException {
		// TODO Auto-generated method stub
				Categoria cat1 = new Categoria(null, "Informática");
				Categoria cat2 = new Categoria(null, "Escritório");
				Categoria cat3 = new Categoria(null, "Cama mesa e banho");
				Categoria cat4 = new Categoria(null, "Eletrônicos");
				Categoria cat5 = new Categoria(null, "Jardinagem");
				Categoria cat6 = new Categoria(null, "Decoração");
				Categoria cat7 = new Categoria(null, "Perfumaria");
				
				Produto p1 = new Produto(null, "Computador" , 2000);
				Produto p2 = new Produto(null, "Impressora" , 800);
				Produto p3 = new Produto(null, "Mouse" , 80);
				Produto p4 = new Produto(null, "Mesa de Escritório" , 300);
				Produto p5 = new Produto(null, "Toalha" , 50);
				Produto p6 = new Produto(null, "Colcha" , 2000);
				Produto p7 = new Produto(null, "TV True Color" , 1200);
				Produto p8 = new Produto(null, "Roçadeira" , 800);
				Produto p9 = new Produto(null, "Abajour" , 100);
				Produto p10 = new Produto(null, "Mouse" , 180);
				Produto p11 = new Produto(null, "Mouse" , 90);		
				
				cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
				cat2.getProdutos().addAll(Arrays.asList(p2,p4));
				cat3.getProdutos().addAll(Arrays.asList(p5,p6));
				cat4.getProdutos().addAll(Arrays.asList(p1,p2, p3, p7));
				cat5.getProdutos().addAll(Arrays.asList(p8));
				cat6.getProdutos().addAll(Arrays.asList(p9,p10));
				cat7.getProdutos().addAll(Arrays.asList(p11));
				
				p1.getCategorias().addAll(Arrays.asList(cat1,cat4));
				p2.getCategorias().addAll(Arrays.asList(cat1,cat2, cat4));
				p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
				p4.getCategorias().addAll(Arrays.asList(cat2));
				p5.getCategorias().addAll(Arrays.asList(cat3));
				p6.getCategorias().addAll(Arrays.asList(cat3));
				p7.getCategorias().addAll(Arrays.asList(cat4));
				p8.getCategorias().addAll(Arrays.asList(cat5));
				p9.getCategorias().addAll(Arrays.asList(cat6));
				p10.getCategorias().addAll(Arrays.asList(cat6));
				p11.getCategorias().addAll(Arrays.asList(cat7));

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
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				
				Pedido ped1 = new Pedido(null, sdf.parse("03/07/2002 20:09"), e1, cli1);
				Pedido ped2 = new Pedido(null, sdf.parse("10/10/2009 20:32"), e2, cli1);
				
				Pagamento pagt1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, 
															ped1, 6);
				ped1.setPagamento(pagt1);
				
				Pagamento pagt2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2,
															sdf.parse("20/02/2017 09:00"), null);	
				ped2.setPagamento(pagt2);
				
				cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
				
				ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
				ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
				ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
				
				ped1.getItens().addAll(Arrays.asList(ip1,ip2));
				ped2.getItens().addAll(Arrays.asList(ip3));
				
				p1.getItens().add(ip1);
				p2.getItens().add(ip3);
				p3.getItens().add(ip2);
				
				
				estadoRepo.saveAll(Arrays.asList(est1,est2));
				cidadeRepo.saveAll(Arrays.asList(c1,c2,c3));
				categoriaRepo.saveAll(Arrays.asList(cat1,cat2, cat3, cat4, cat5, cat6,cat7));
				produtoRepo.saveAll(Arrays.asList(p1,p2,p3));
				clienteRepo.saveAll(Arrays.asList(cli1));
				enderecoRepo.saveAll(Arrays.asList(e1,e2));
				pedidoRepo.saveAll(Arrays.asList(ped1,ped2));
				pagamentoRepo.saveAll(Arrays.asList(pagt1, pagt2));
				itemPedRepo.saveAll(Arrays.asList(ip1,ip2,ip3));
	}
}
