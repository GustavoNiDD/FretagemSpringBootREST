package com.br.frete.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.br.frete.entity.Empresa;
import com.br.frete.entity.ProdutosUsuarioPedido;
import com.br.frete.services.EmpresaService;
import com.br.frete.services.FreteService;
import com.br.frete.services.ProdutoService;
import com.br.frete.services.ProdutosUsuarioPedidoService;
import com.br.frete.services.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EmpresaControllerTest {

    @InjectMocks
    EmpresaController empresaController;

    @Mock
    EmpresaService empresaService;

    @Mock
    UsuarioService usuarioService;

    @Mock
    FreteService freteService;

    @Mock
    ProdutoService produtoService;

    @Mock
    ProdutosUsuarioPedidoService produtosUsuarioPedidoService;

    @Mock
    Model model;

    @Mock
    BindingResult bindingResult;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEmpresaCadastrar() {
        String view = empresaController.empresaCadastrar(model);
        verify(model, times(1)).addAttribute(eq("empresa"), any());
        assert (view.equals("empresaCadastrar"));
    }

    @Test
    public void testEmpresaLogar() {
        String view = empresaController.empresaLogar(model);
        verify(model, times(1)).addAttribute(eq("empresa"), any());
        assertEquals("empresaLogar", view);
    }

    @Test
    public void testEmpresaLogout() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication auth = mock(Authentication.class);

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);

        String view = empresaController.empresaLogout(request, response);
        assertEquals("empresaLogar", view);

                SecurityContextHolder.clearContext();
    }

    @Test
    public void testEmpresaHome() {
        String view = empresaController.empresaHome(model);
        verify(model, times(1)).addAttribute(eq("empresa"), any());
        assertEquals("empresaHome", view);
    }

    @Test
    public void testEmpresaConsultar() {
        when(freteService.getAll()).thenReturn(new ArrayList<>());

        String view = empresaController.empresaConsultar(model);
        verify(model, times(1)).addAttribute(eq("fretes"), any());
        assertEquals("empresaFreteLista", view);
    }

    @Test
    public void testEmpresaFrete() {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("test");
        when(usuarioService.getAll()).thenReturn(new ArrayList<>());
        when(empresaService.getByUsername(anyString())).thenReturn(new Empresa());

        String view = empresaController.empresaFrete(model, principal);
        verify(model, times(3)).addAttribute(anyString(), any());
        assertEquals("empresaFrete", view);
    }
    
    @Test
    public void testEmpresaProdutos() {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("test");
        when(empresaService.getByUsername(anyString())).thenReturn(new Empresa());

        String view = empresaController.empresaProdutos(model, principal);
        verify(model, times(3)).addAttribute(anyString(), any());
        assertEquals("empresaAdicionarProdutos", view);
    }

    @Test
    public void testEmpresaProdutosFrete() {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("test");
        when(empresaService.getByUsername(anyString())).thenReturn(new Empresa());
        when(freteService.getFreteEmpresa(anyLong())).thenReturn(new ArrayList<>());
        when(produtoService.getByEmpresaId(anyLong())).thenReturn(new ArrayList<>());

        String view = empresaController.empresaProdutosFrete(model, principal);
        verify(model, times(4)).addAttribute(anyString(), any());
        assertEquals("empresaFreteProdutos", view);
    }

    @Test
    public void testEmpresaCadastrarProduto() {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("test");
        when(empresaService.getByUsername(anyString())).thenReturn(new Empresa());
        when(produtoService.getAllById(anyLong())).thenReturn(new ArrayList<>());

        String view = empresaController.empresaCadastrarProduto(model, principal);
        verify(model, times(1)).addAttribute(anyString(), any());
        assertEquals("empresaProdutoLista", view);
    }

    @Test
    public void testEmpresaConsultarProdutos() {
        when(produtoService.getAll()).thenReturn(new ArrayList<>());

        String view = empresaController.empresaConsultarProdutos(model);
        verify(model, times(1)).addAttribute(anyString(), any());
        assertEquals("empresaProdutoLista", view);
    }

    @Test
    public void testVisualizarProdutosPorPedido() {
        Long id = 1L;
        List<ProdutosUsuarioPedido> produtosUsuarioFrete = new ArrayList<>();
        when(produtosUsuarioPedidoService.getByPedidoUsuarioId(id)).thenReturn(produtosUsuarioFrete);

        List<Object[]> result = empresaController.visualizarProdutosPorPedido(id);
        assertEquals(produtosUsuarioFrete.size(), result.size());
    }

    @Test
    public void testVisualizarPedidosEmpresa() {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("test");
        when(empresaService.getByUsername(anyString())).thenReturn(new Empresa());
        when(freteService.getAllByEmpresaId(anyLong())).thenReturn(new ArrayList<>());

        String view = empresaController.visualizarPedidosEmpresa(model, principal);
        verify(model, times(1)).addAttribute(anyString(), any());
        assertEquals("empresaMeusPedidos", view);
    }

    @Test
    public void testVisualizarPedidosEmpresaWithoutPrincipal() {
        String view = empresaController.visualizarPedidosEmpresa(model, null);
        assertEquals("empresaLogar", view);
    }
}