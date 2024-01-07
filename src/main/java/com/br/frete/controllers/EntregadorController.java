package com.br.frete.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name = "Entregador API", description = "Operações relacionadas a entregadores")
@Controller
public class EntregadorController {

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private ProdutosFreteService produtosFreteService;

    @Autowired
    private FreteService freteService;

    @Autowired
    private EntregadorService entregadorService;

    @Autowired
    private EntregadorFreteService entregadorFreteService;

    @Autowired
    private EntregadorRepository entregadorRepository;

    public void addAttribute(String name, Object object, Model model) {
        model.addAttribute("entregador", new Entregador());
    }

    @Operation(summary = "Visual login da entregador", description = "Visual um novo login da entregador")
    @GetMapping("/entregador/logar")
    public String entregadorLogar(Model model) {
        model.addAttribute("entregador", new Entregador());
        return "entregadorLogar";
    }

    @Operation(summary = "Logout da entregador", description = "Visual o logout da entregador")
    @GetMapping("/entregador/logout")
    public String entregadorLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "entregadorLogar";
    }

    @Operation(summary = "Visual cadastrar entregador", description = "Visual um novo entregador")
    @GetMapping("/entregador/cadastrar")
    public String entregadorCadastrar(Model model) {
        model.addAttribute("entregador", new Entregador());
        List<Veiculo> tiposVeiculo = veiculoService.getAll();
        model.addAttribute("tiposVeiculo", tiposVeiculo);
        return "entregadorCadastrar";
    }

    @Operation(summary = "Visual home entregador", description = "Visual um novo home entregador")
    @GetMapping("/entregador/home")
    public String entregadorHome(Model model) {
        addAttribute("entregador", new Entregador(), model);
        return "entregadorHome";
    }

    @Operation(summary = "Visual fretesDisponiveis", description = "Visual fretesDisponiveis")
    @GetMapping("/entregador/fretesDisponiveis")
    public String produtosFretes(Model model) {
        List<Frete> fretes = freteService.getFretesWithStatusEntregadorFalse();
        System.out.println(fretes);
        model.addAttribute("fretes", fretes);

        List<Long> freteIds = fretes.stream().map(Frete::getId).collect(Collectors.toList());
        List<ProdutosFrete> produtosFretes = produtosFreteService.getProdutosFreteByFreteIds(freteIds);
        model.addAttribute("produtosFretes", produtosFretes);

        return "entregadorFretesDisponiveis";
    }

    @Operation(summary = "Visual meus fretes", description = "Visual meus fretes")
    @GetMapping("/entregador/meusFretes")
    public String entregadorConsultar(Model model, Principal principal) {
        Entregador entregador = entregadorService.getByUsername(principal.getName());
        List<EntregadorFrete> entregadorFretes = entregadorFreteService.getAllEntregador(entregador);

        List<Map<String, Object>> resultados = entregadorRepository.findAllEntregadorFrete(entregador.getId());
        model.addAttribute("resultados", resultados);

        model.addAttribute("entregadorFretes", entregadorFretes);
        return "entregadorMeusFretes";
    }

    @Operation(summary = "Visual fretesConcluidos", description = "Visual fretesConcluidos")
    @GetMapping("/entregador/fretesConcluidos")
    public String getFretesConcluidos(Model model) {
        List<Frete> fretesConcluidos = freteService.getByStatusEntrega(StatusEntrega.concluido);
        model.addAttribute("fretesConcluidos", fretesConcluidos);
        return "entregadorFretesConcluidos";
    }

}
