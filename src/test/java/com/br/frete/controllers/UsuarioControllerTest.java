package com.br.frete.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

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

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private PedidoUsuarioService pedidosUsuarioService;

    @Mock
    private ProdutoUsuarioService produtoUsuarioService;

    @Mock
    private ProdutosUsuarioPedidoService produtosUsuarioPedidoService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private ProdutoUsuario produtoUsuario;

    @Mock
    private ProdutosUsuarioPedido produtosUsuarioPedido;

    @Mock
    private Empresa empresa;

    @Mock
    private FreteService freteService;
    @Mock
    private EmpresaService empresaService;

    @Test
    public void testUsuarioCadastrar() {
        String view = usuarioController.usuarioCadastrar(model);
        verify(model, times(1)).addAttribute(eq("usuario"), any(Usuario.class));
        assertEquals("usuarioCadastrar", view);
    }

    @Test
    public void testUsuarioLogar() {
        String view = usuarioController.usuarioLogar(model);
        verify(model, times(1)).addAttribute(eq("usuario"), any(Usuario.class));
        assertEquals("usuarioLogar", view);
    }

    @Test
    public void testUsuarioHome() {
        String view = usuarioController.usuarioHome(model);
        verify(model, times(1)).addAttribute(eq("usuario"), any(Usuario.class));
        assertEquals("usuarioHome", view);
    }

    @Test
    public void testUsuarioSolicitarPedido() {
        String view = usuarioController.usuarioSolicitarPedido(model);
        verify(model, times(1)).addAttribute(eq("usuario"), any(Usuario.class));
        assertEquals("usuarioSolicitarPedido", view);
    }

    @Test
    public void testUsuarioAdicionarProduto() {
        model.addAttribute("produto", new ProdutoUsuario());
        String view = usuarioController.usuarioAdicionarProduto(model);
        verify(model, times(1)).addAttribute(eq("produto"), any(ProdutoUsuario.class));
        assertEquals("usuarioAdicionarProduto", view);
    }

    @Test
    public void testVincularProdutoPedido() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);     
        when(principal.getName()).thenReturn("username");
        when(produtoUsuarioService.getByUsuarioUsername(anyString())).thenReturn(Arrays.asList(new ProdutoUsuario()));
        when(pedidosUsuarioService.getByUsuarioUsername(anyString())).thenReturn(Arrays.asList(new PedidoUsuario()));
        when(usuarioService.getByUsername(anyString())).thenReturn(usuario);         when(produtosUsuarioPedidoService.getByUsuarioId(anyLong())).thenReturn(Arrays.asList(new ProdutosUsuarioPedido()));
    
        String view = usuarioController.vincularProdutoPedido(model, principal);
    
        verify(model, times(1)).addAttribute(eq("produtoUsuarioPedido"), any(ProdutosUsuarioPedido.class));
        verify(model, times(1)).addAttribute(eq("produtos"), anyList());
        verify(model, times(1)).addAttribute(eq("pedidos"), anyList());
        verify(model, times(1)).addAttribute(eq("produtosUsuarioPedido"), anyList());
        assertEquals("usuarioVincularProduto", view);
    }

    @Test
    public void testEmpresaFrete() {
        when(principal.getName()).thenReturn("username");
        when(freteService.getAll()).thenReturn(Arrays.asList(new Frete()));
        when(pedidosUsuarioService.getByUsuarioUsername(anyString())).thenReturn(Arrays.asList(new PedidoUsuario()));
        when(empresaService.getAll()).thenReturn(Arrays.asList(new Empresa()));

        String view = usuarioController.empresaFrete(model, principal);

        verify(model, times(1)).addAttribute(eq("fretes"), anyList());
        verify(model, times(1)).addAttribute(eq("pedidos"), anyList());
        verify(model, times(1)).addAttribute(eq("empresas"), anyList());
        assertEquals("usuarioSolicitarFrete", view);
    }

    @Test
    public void testEmpresaFreteId() {
        when(principal.getName()).thenReturn("username");
        when(pedidosUsuarioService.getByUsuarioUsername(anyString())).thenReturn(Arrays.asList(new PedidoUsuario()));
        when(empresaService.getAll()).thenReturn(Arrays.asList(new Empresa()));

        String view = usuarioController.empresaFreteId(model, principal);

        verify(model, times(1)).addAttribute(eq("frete"), any(Frete.class));
        verify(model, times(1)).addAttribute(eq("pedidos"), anyList());
        verify(model, times(1)).addAttribute(eq("empresas"), anyList());
        assertEquals("usuarioSolicitarFrete", view);
    }
}