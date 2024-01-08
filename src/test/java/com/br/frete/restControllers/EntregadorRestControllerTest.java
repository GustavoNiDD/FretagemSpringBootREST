package com.br.frete.restControllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.br.frete.entity.Entregador;
import com.br.frete.entity.Frete;
import com.br.frete.entity.Veiculo;
import com.br.frete.services.EntregadorFreteService;
import com.br.frete.services.EntregadorService;
import com.br.frete.services.FreteService;
import com.br.frete.services.VeiculoService;

import jakarta.servlet.http.HttpSession;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EntregadorRestControllerTest {

    @InjectMocks
    EntregadorRestController entregadorRestController;

    @Mock
    EntregadorService entregadorService;

    @Mock
    VeiculoService veiculoService;

    @Mock
    FreteService freteService;

    @Mock
    EntregadorFreteService entregadorFreteService;

    @Mock
    HttpSession session;

    @Mock
    Principal principal;

    @Test
    public void testCadastrar() {
        Entregador entregador = new Entregador();
        Veiculo tipoVeiculo = new Veiculo();
        tipoVeiculo.setId(1L);         entregador.setTipoVeiculo(tipoVeiculo); 
        when(veiculoService.getById(tipoVeiculo.getId())).thenReturn(Optional.of(tipoVeiculo).get());
        when(entregadorService.save(entregador)).thenReturn(entregador);

        Entregador result = entregadorRestController.cadastrar(entregador);

        assertEquals(entregador, result);
        verify(veiculoService, times(1)).getById(tipoVeiculo.getId());
        verify(entregadorService, times(1)).save(entregador);
    }

    @Test
    public void testLogar() {
        Entregador entregador = new Entregador();
        when(entregadorService.logar(entregador)).thenReturn(entregador);
        when(entregadorService.verificarRoleName(entregador.getId())).thenReturn(true);

        Entregador result = entregadorRestController.logar(entregador, session);

        assertEquals(entregador, result);
    }

    @Test
    public void testEscolherFretes() {
        List<Long> freteIds = Arrays.asList(1L, 2L);
        String username = "username";
        when(principal.getName()).thenReturn(username);
        Entregador entregador = new Entregador();
        when(entregadorService.getByUsername(username)).thenReturn(entregador);
        Frete frete = new Frete();
        when(freteService.getFreteById(anyLong())).thenReturn(Optional.of(frete));

        String view = entregadorRestController.escolherFretes(freteIds, principal);

        assertEquals("entregadorMeusFretes", view);
    }

    @Test
    public void testCancelarFrete() {
        Long entregadorFreteId = 1L;
        Long freteId = 1L;
        Frete frete = new Frete();
        when(freteService.getById(freteId)).thenReturn(Optional.of(frete));

        String view = entregadorRestController.cancelarFrete(entregadorFreteId, freteId);

        assertEquals("/entregador/meusFretes", view);
    }

    @Test
    public void testMudarStatusParaEmAndamento() {
        Long freteId = 1L;
        doNothing().when(freteService).mudarStatusParaEmAndamento(freteId);

        String view = entregadorRestController.mudarStatusParaEmAndamento(freteId);

        verify(freteService, times(1)).mudarStatusParaEmAndamento(freteId);
        assertEquals("entregadorMeusFretes", view);
    }

    @Test
    public void testMudarStatusParaConcluido() {
        Long freteId = 1L;
        doNothing().when(freteService).mudarStatusParaConcluido(freteId);

        String view = entregadorRestController.mudarStatusParaConcluido(freteId);

        verify(freteService, times(1)).mudarStatusParaConcluido(freteId);
        assertEquals("entregadorMeusFretes", view);
    }
}
