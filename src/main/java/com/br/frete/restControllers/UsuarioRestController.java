package com.br.frete.restControllers;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Tag(name = "UsuarioRest API", description = "Operações relacionadas a usuários")
@RestController
@RequestMapping("/usuario")
public class UsuarioRestController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PedidoUsuarioService pedidoUsuarioService;

    @Autowired
    private ProdutoUsuarioService produtoUsuarioService;

    @Autowired
    private ProdutosUsuarioPedidoService produtosUsuarioPedidoService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private FreteService freteService;

    private static final Logger logger = LoggerFactory.getLogger(UsuarioRestController.class);

    @Operation(summary = "Cadastrar usuário", description = "Cadastra um novo usuário")
    @PostMapping("/cadastrar")
    public Usuario cadastrar(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @Operation(summary = "Logar usuário", description = "Realiza o login do usuário")
    @PostMapping("/logar")
    public Usuario logar(@RequestBody Usuario usuario, HttpSession session) {

        Usuario usuarioLogado = usuarioService.logar(usuario);

        if (usuarioService.verificarRoleName(usuarioLogado.getId())) {
            logger.info("Usuario possui o papel USUARIO");
            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("USUARIO");
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    usuarioLogado.getUsername(),
                    usuarioLogado.getPassword(),
                    authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());

            return usuarioLogado;
        } else {
            logger.info("Usuario não possui o papel USUARIO");
            return null;
        }
    }

    @Operation(summary = "Logout do usuário", description = "Realiza o logout do usuário")
    @PostMapping("/logout")
    public void logout(HttpSession session, HttpServletResponse response) throws IOException {
        SecurityContextHolder.clearContext();
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("/home");
    }

    @Operation(summary = "Solicitar pedido", description = "Solicita um novo pedido")
    @PostMapping("/solicitarPedido")
    public void solicitarPedido(@RequestParam("statusPedido") boolean statusPedido,
            @RequestParam("remetente") String remetente,
            @RequestParam("destinatarioKm") double destinatarioKm, Principal principal, Model model,
            HttpServletResponse response) throws IOException {
        PedidoUsuario novoPedido = new PedidoUsuario();
        novoPedido.setStatusPedido(statusPedido);
        novoPedido.setRemetente(remetente);
        novoPedido.setDestinatarioKm(destinatarioKm);
        novoPedido.setUsuario(usuarioService.getByUsername(principal.getName()));

        pedidoUsuarioService.save(novoPedido);

        model.addAttribute("mensagem", "Pedido feito com sucesso!");

        response.sendRedirect("/usuario/home");
    }

    @Operation(summary = "Adicionar produto", description = "Adiciona um novo produto")
    @PostMapping("/adicionarProduto")
    public void adicionarProduto(@RequestParam("nome") String nome,
            @RequestParam("valor") double valor, @RequestParam("peso") double peso,
            @RequestParam("descricaoProduto") String descricaoProduto, Principal principal,
            HttpServletResponse response) throws IOException {
        ProdutoUsuario novoProduto = new ProdutoUsuario();
        novoProduto.setNome(nome);
        novoProduto.setValor(valor);
        novoProduto.setPeso(peso);
        novoProduto.setDescricaoProduto(descricaoProduto);
        novoProduto.setUsuario(usuarioService.getByUsername(principal.getName()));

        produtoUsuarioService.save(novoProduto);

        response.sendRedirect("/usuario/adicionarProduto");

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
