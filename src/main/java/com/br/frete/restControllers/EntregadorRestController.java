package com.br.frete.restControllers;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.frete.entity.Entregador;
import com.br.frete.entity.EntregadorFrete;
import com.br.frete.entity.Frete;
import com.br.frete.entity.Veiculo;
import com.br.frete.services.EntregadorFreteService;
import com.br.frete.services.EntregadorService;
import com.br.frete.services.FreteService;
import com.br.frete.services.VeiculoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Tag(name = "EntregadorRest API", description = "Operações relacionadas a entregadores")
@RestController
@RequestMapping("/entregador")
public class EntregadorRestController {

    @Autowired
    private EntregadorService entregadorService;

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private FreteService freteService;

    @Autowired
    private EntregadorFreteService entregadorFreteService;

    private static final Logger logger = LoggerFactory.getLogger(EntregadorRestController.class);

    @Operation(summary = "Cadastrar entregador", description = "Cadastra um novo entregador")
    @PostMapping("/cadastrar")
    public Entregador cadastrar(@RequestBody Entregador entregador) {
        Veiculo tipoVeiculo = veiculoService.getById(entregador.getTipoVeiculo().getId());
        entregador.setTipoVeiculo(tipoVeiculo);
        return entregadorService.save(entregador);
    }

    @Operation(summary = "Logar entregador", description = "Realiza o login do entregador")
    @PostMapping("/logar")
    public Entregador logar(@RequestBody Entregador entregador, HttpSession session) {

        Entregador loggedEntregador = entregadorService.logar(entregador);

        if (loggedEntregador != null) {
            logger.info("Entregador logada com sucesso");

            if (entregadorService.verificarRoleName(loggedEntregador.getId())) {
                logger.info("Entregador possui o papel Entregador");
                List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ENTREGADOR");
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        loggedEntregador.getUsername(),
                        loggedEntregador.getPassword(),
                        authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                        SecurityContextHolder.getContext());

                return loggedEntregador;
            } else {
                logger.info("Entregador não possui o papel Entregador");
                return null;
            }

        } else {
            logger.info("Entregador não logada");
            return null;
        }

    }

    @Operation(summary = "Logout do entregador", description = "Realiza o logout do entregador")
    @PostMapping("/logout")
    public void logout(HttpSession session, HttpServletResponse response) throws IOException {
        SecurityContextHolder.clearContext();
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("/home");
    }

    @Operation(summary = "Aceitar frete", description = "O entregador aceita um ou mais fretes")
    @PostMapping("/aceitarFrete")
    public String escolherFretes(@RequestParam List<Long> freteIds, Principal principal) {
        String username = principal.getName();
        Entregador entregador = entregadorService.getByUsername(username);
        for (Long freteId : freteIds) {
            Optional<Frete> optionalFrete = freteService.getFreteById(freteId);
            if (optionalFrete.isPresent()) {
                Frete frete = optionalFrete.get();
                EntregadorFrete entregadorFrete = new EntregadorFrete();
                entregadorFrete.setEntregador(entregador);
                entregadorFrete.setFrete(frete);
                entregadorFreteService.saveEntregadorFrete(entregadorFrete);

                frete.setStatusEntregador(true);
                freteService.save(frete);
            }
        }
        return "entregadorMeusFretes";
    }

    @Operation(summary = "Cancelar frete", description = "O entregador cancela um frete")
    @PostMapping("/cancelarFrete")
    public String cancelarFrete(@RequestParam Long entregadorFreteId, @RequestParam Long freteId) {

        entregadorFreteService.cancelarFrete(entregadorFreteId);

        Frete frete = freteService.getById(freteId)
                .orElseThrow(() -> new IllegalArgumentException("Frete não encontrado"));

        frete.setStatusEntregador(false);

        freteService.save(frete);

        return "/entregador/meusFretes";
    }

    @Operation(summary = "Mudar status para em andamento", description = "Muda o status de um frete para 'Em Andamento'")
    @PostMapping("mudarStatusParaEmAndamento")
    public String mudarStatusParaEmAndamento(@RequestParam Long freteId) {
        freteService.mudarStatusParaEmAndamento(freteId);
        return "entregadorMeusFretes";
    }

    @Operation(summary = "Mudar status para concluído", description = "Muda o status de um frete para 'Concluído'")
    @PostMapping("mudarStatusParaConcluido")
    public String mudarStatusParaConcluido(@RequestParam Long freteId) {
        freteService.mudarStatusParaConcluido(freteId);
        return "entregadorMeusFretes";
    }

}
