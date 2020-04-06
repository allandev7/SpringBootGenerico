package com.allan.cursomc.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allan.cursomc.domain.ItemPedido;
import com.allan.cursomc.domain.PagamentoComBoleto;
import com.allan.cursomc.domain.Pedido;
import com.allan.cursomc.domain.enums.EstadoPagamento;
import com.allan.cursomc.repositories.ClienteRepository;
import com.allan.cursomc.repositories.ItemPedidoRepository;
import com.allan.cursomc.repositories.PagamentoRepository;
import com.allan.cursomc.repositories.PedidoRepository;
import com.allan.cursomc.services.exception.ObjectNotFoundException;


@Service
public class PedidoService {
	
	@Autowired
	private ClienteService clienteServ;
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepo;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepo;
	
	@Autowired
	private ProdutoService produtoServ;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id){
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado id:" +
									id+", Tipo: "+ Pedido.class.getName()));
	} 
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		System.out.println(obj.getCliente().getTipo());
		obj.setInstante(new Date());
		obj.setCliente(clienteServ.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepo.save(obj.getPagamento());
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoServ.find(ip.getProduto().getId()));
			ip.setPreco(produtoServ.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepo.saveAll(obj.getItens());
		emailService.sendOrderConfirmationEmailHtml(obj);
		return obj;
	}
	
}
