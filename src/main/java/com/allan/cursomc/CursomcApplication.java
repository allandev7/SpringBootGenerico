package com.allan.cursomc;

import java.text.SimpleDateFormat;
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

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
	}

}
