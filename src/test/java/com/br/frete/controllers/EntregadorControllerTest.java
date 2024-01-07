package com.br.frete.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import com.br.frete.entity.Entregador;
import com.br.frete.entity.EntregadorFrete;
import com.br.frete.entity.Frete;
import com.br.frete.entity.ProdutosFrete;
import com.br.frete.entity.StatusEntrega;
import com.br.frete.entity.Veiculo;
import com.br.frete.repository.EntregadorRepository;
import com.br.frete.services.EntregadorFreteService;
import com.br.frete.services.EntregadorService;
import com.br.frete.services.FreteService;
import com.br.frete.services.ProdutosFreteService;
import com.br.frete.services.VeiculoService;

@SpringBootTest
public class EntregadorControllerTest {

    @InjectMocks
    EntregadorController entregadorController;

    @Mock
    VeiculoService veiculoService;

    @Mock
    Model model;

    @Mock
    FreteService freteService;

    @Mock
    ProdutosFreteService produtosFreteService;

    @Mock
    EntregadorFreteService entregadorFreteService;

    @Mock
    EntregadorRepository entregadorRepository;

    @Mock
    EntregadorService entregadorService;
    @Mock
    Principal principal;

    @Test
    public void testEntregadorLogar() {
        String view = entregadorController.entregadorLogar(model);
        verify(model, times(1)).addAttribute("entregador", new Entregador());
        assertEquals("entregadorLogar", view);
    }

    @Test
    public void testEntregadorCadastrar() {
        List<Veiculo> veiculos = new ArrayList<>();
        veiculos.add(new Veiculo());
        when(veiculoService.getAll()).thenReturn(veiculos);

        String view = entregadorController.entregadorCadastrar(model);

        verify(model, times(1)).addAttribute("entregador", new Entregador());
        verify(model, times(1)).addAttribute("tiposVeiculo", veiculos);
        assertEquals("entregadorCadastrar", view);
    }

    @Test
    public void testEntregadorHome() {
        String view = entregadorController.entregadorHome(model);
        verify(model, times(1)).addAttribute("entregador", new Entregador());
        assertEquals("entregadorHome", view);
    }

    @Test
    public void testProdutosFretes() {
        List<Frete> fretes = new ArrayList<>();
        fretes.add(new Frete());
        when(freteService.getFretesWithStatusEntregadorFalse()).thenReturn(fretes);

        List<Long> freteIds = fretes.stream().map(Frete::getId).collect(Collectors.toList());
        List<ProdutosFrete> produtosFretes = new ArrayList<>();
        produtosFretes.add(new ProdutosFrete());
        when(produtosFreteService.getProdutosFreteByFreteIds(freteIds)).thenReturn(produtosFretes);

        String view = entregadorController.produtosFretes(model);

        verify(model, times(1)).addAttribute("fretes", fretes);
        verify(model, times(1)).addAttribute("produtosFretes", produtosFretes);
        assertEquals("entregadorFretesDisponiveis", view);
    }

    @Test
    public void testEntregadorConsultar() {
        Entregador entregador = new Entregador();
        when(entregadorService.getByUsername(principal.getName())).thenReturn(entregador);

        List<EntregadorFrete> entregadorFretes = new ArrayList<>();
        entregadorFretes.add(new EntregadorFrete());
        when(entregadorFreteService.getAllEntregador(entregador)).thenReturn(entregadorFretes);

        List<Map<String, Object>> resultados = new ArrayList<>();
        when(entregadorRepository.findAllEntregadorFrete(entregador.getId())).thenReturn(resultados);

        String view = entregadorController.entregadorConsultar(model, principal);

        verify(model, times(1)).addAttribute("resultados", resultados);
        verify(model, times(1)).addAttribute("entregadorFretes", entregadorFretes);
        assertEquals("entregadorMeusFretes", view);
    }

    @Test
    public void testGetFretesConcluidos() {
        Frete frete1 = new Frete();
        Frete frete2 = new Frete();
        List<Frete> fretesConcluidos = Arrays.asList(frete1, frete2);

        when(freteService.getByStatusEntrega(StatusEntrega.concluido)).thenReturn(fretesConcluidos);

        String view = entregadorController.getFretesConcluidos(model);

        verify(model, times(1)).addAttribute("fretesConcluidos", fretesConcluidos);
        assertEquals("entregadorFretesConcluidos", view);
    }

}