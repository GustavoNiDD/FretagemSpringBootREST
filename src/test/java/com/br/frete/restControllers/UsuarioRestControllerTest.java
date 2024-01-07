package com.br.frete.restControllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.security.Principal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.br.frete.entity.Empresa;
import com.br.frete.entity.Frete;
import com.br.frete.entity.PedidoUsuario;
import com.br.frete.entity.ProdutoUsuario;
import com.br.frete.entity.ProdutosUsuarioPedido;
import com.br.frete.entity.Usuario;
import com.br.frete.services.EmpresaService;
import com.br.frete.services.FreteService;
import com.br.frete.services.PedidoUsuarioService;
import com.br.frete.services.ProdutoUsuarioService;
import com.br.frete.services.ProdutosUsuarioPedidoService;
import com.br.frete.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;

public class UsuarioRestControllerTest {

    @InjectMocks
    private UsuarioRestController usuarioRestController;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private PedidoUsuario pedidoUsuario;

    @Mock
    private ProdutoUsuarioService produtoUsuarioService;

    @Mock
    private PedidoUsuarioService pedidoUsuarioService;

    @Mock
    private ProdutosUsuarioPedidoService produtosUsuarioPedidoService;

    @Mock
    private EmpresaService empresaService;

    @Mock
    private FreteService freteService;


    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioRestController).build();
    }

    @Test
    public void testCadastrar() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Test User");
        usuario.setUsername("testuser");
        usuario.setPassword("password");

        when(usuarioService.save(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/usuario/cadastrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(usuario)))
                .andExpect(status().isOk());
    }

    @Test
    public void testLogar() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Test User");
        usuario.setUsername("testuser");
        usuario.setPassword("password");

        when(usuarioService.logar(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/usuario/logar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(usuario)))
                .andExpect(status().isOk());
    }

    @Test
    public void testLogout() throws Exception {
        mockMvc.perform(post("/usuario/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    public void testSolicitarPedido() throws Exception {
        PedidoUsuario pedidoUsuario = new PedidoUsuario();
        pedidoUsuario.setStatusPedido(true);
        pedidoUsuario.setRemetente("Test Remetente");
        pedidoUsuario.setDestinatarioKm(10.0);
        pedidoUsuario.setUsuario(new Usuario());

        Principal mockPrincipal = Mockito.mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("testuser");
        when(usuarioService.getByUsername(anyString())).thenReturn(new Usuario());
        when(pedidoUsuarioService.save(any(PedidoUsuario.class))).thenReturn(pedidoUsuario);

        mockMvc.perform(post("/usuario/solicitarPedido")
                .principal(mockPrincipal)
                .param("statusPedido", "true")
                .param("remetente", "Test Remetente")
                .param("destinatarioKm", "10.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/usuario/home"));
    }

    @Test
    public void testAdicionarProduto() throws Exception {
        ProdutoUsuario produtoUsuario = new ProdutoUsuario();
        produtoUsuario.setNome("Test Produto");
        produtoUsuario.setValor(100.0);
        produtoUsuario.setPeso(1.0);
        produtoUsuario.setDescricaoProduto("Teste de descrição do produto");
        produtoUsuario.setUsuario(new Usuario());

        Principal mockPrincipal = Mockito.mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("testuser");
        when(usuarioService.getByUsername(anyString())).thenReturn(new Usuario());
        when(produtoUsuarioService.save(any(ProdutoUsuario.class))).thenReturn(produtoUsuario);
 
        mockMvc.perform(post("/usuario/adicionarProduto")
                .principal(mockPrincipal)
                .param("nome", "Test Produto")
                .param("valor", "100.0")
                .param("peso", "1.0")
                .param("descricaoProduto", "Teste de descrição do produto"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/usuario/adicionarProduto"));
    }
     @Operation(summary = "Vincular produto ao pedido", description = "Vincula um produto a um pedido")
    @PostMapping("/vincularProdutoPedido")
    public void vincularProdutoPedido(
            @RequestParam Long produtoId,
            @RequestParam Long pedidoId,
            @RequestParam Integer quantidade, HttpServletResponse response) throws IOException {
        ProdutoUsuario produto = produtoUsuarioService.findById(produtoId);
        PedidoUsuario pedido = pedidoUsuarioService.getById(pedidoId);

        ProdutosUsuarioPedido produtosUsuarioPedido = new ProdutosUsuarioPedido();
        produtosUsuarioPedido.setProdutoUsuario(produto);
        produtosUsuarioPedido.setPedidoUsuario(pedido);
        produtosUsuarioPedido.setQuantidade(quantidade);

        produtosUsuarioPedidoService.save(produtosUsuarioPedido);
        response.sendRedirect("/usuario/vincularProdutoPedido/" + pedidoId);
    }

    @Operation(summary = "Solicitar frete", description = "Solicita um frete para um pedido")
    @PostMapping("/solicitarFrete")
    public void solicitarFrete(@RequestParam("pedido") Long pedidoId, @RequestParam("empresa") Long empresaId,
            HttpServletResponse response) throws IOException {
        PedidoUsuario pedido = pedidoUsuarioService.getById(pedidoId);
        Empresa empresa = empresaService.getById(empresaId);

        Frete frete = new Frete();
        frete.setPedidoUsuario(pedido);
        frete.setEmpresa(empresa);

        freteService.save(frete);

        response.sendRedirect("/usuario/home");
    }
}