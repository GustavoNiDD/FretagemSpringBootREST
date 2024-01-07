package com.br.frete.controllers;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

@Tag(name = "Usuario API", description = "Operações relacionadas a usuários")
@Controller
public class UsuarioController {

    @Autowired
    private ProdutoUsuarioService produtoUsuarioService;

    @Autowired
    private PedidoUsuarioService pedidosUsuarioService;

    @Autowired
    private ProdutosUsuarioPedidoService produtosUsuarioPedidoService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FreteService freteService;

    public void addAttribute(String name, Object object, Model model) {
        model.addAttribute("usuario", new Usuario());

    }

    @Operation(summary = "Visual cadastrar usuario", description = "Visual cadastrar usuario")
    @GetMapping("/usuario/cadastrar")
    public String usuarioCadastrar(Model model) {
        addAttribute("usuario", new Usuario(), model);
        return "usuarioCadastrar";
    }

    @Operation(summary = "Visual logar usuario", description = "Visual logar usuario")
    @GetMapping("/usuario/logar")
    public String usuarioLogar(Model model) {
        addAttribute("usuario", new Usuario(), model);
        return "usuarioLogar";
    }

    @Operation(summary = "Visual home usuario", description = "Visual home usuario")
    @GetMapping("/usuario/home")
    public String usuarioHome(Model model) {
        addAttribute("usuario", new Usuario(), model);
        return "usuarioHome";
    }

    @Operation(summary = "Visual solicitar pedido", description = "Visual solicitar pedido")
    @GetMapping("/usuario/solicitarPedido")
    public String usuarioSolicitarPedido(Model model) {
        addAttribute("usuario", new Usuario(), model);
        return "usuarioSolicitarPedido";
    }

    @Operation(summary = "Visual solicitar frete", description = "Visual solicitar frete")
    @GetMapping("/usuario/adicionarProduto")
    public String usuarioAdicionarProduto(Model model) {
        addAttribute("produto", new ProdutoUsuario(), model);
        return "usuarioAdicionarProduto";
    }

    @Operation(summary = "Visual solicitar frete", description = "Visual solicitar frete")
    @GetMapping("/usuario/vincularProdutoPedido")
    public String vincularProdutoPedido(Model model, Principal principal) {
        model.addAttribute("produtoUsuarioPedido", new ProdutosUsuarioPedido());

        List<ProdutoUsuario> produtos = produtoUsuarioService.getByUsuarioUsername(principal.getName());
        List<PedidoUsuario> pedidos = pedidosUsuarioService.getByUsuarioUsername(principal.getName());
        model.addAttribute("produtos", produtos);
        model.addAttribute("pedidos", pedidos);

        List<ProdutosUsuarioPedido> produtosUsuarioPedido = produtosUsuarioPedidoService
                .getByUsuarioId(usuarioService.getByUsername(principal.getName()).getId());
        model.addAttribute("produtosUsuarioPedido", produtosUsuarioPedido);
        return "usuarioVincularProduto";
    }

    @Operation(summary = "Visual vincular produto por pedido especifico", description = "vincular produto por pedido especifico")
    @GetMapping("/usuario/vincularProdutoPedido/{pedidoId}")
    public String getProdutosUsuarioPedido(Principal principal,
            @PathVariable Long pedidoId, Model model) {
        List<ProdutosUsuarioPedido> produtosUsuarioPedido = produtosUsuarioPedidoService.findByPedido_Id(pedidoId);
        List<ProdutoUsuario> produtos = produtoUsuarioService.getByUsuarioUsername(principal.getName());
        List<PedidoUsuario> pedidos = pedidosUsuarioService.getByUsuarioUsername(principal.getName());
        model.addAttribute("produtos", produtos);
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("produtosUsuarioPedido", produtosUsuarioPedido);
        return "usuarioVincularProduto";
    }

    @Operation(summary = "Visual vincular meus produtos", description = "vincular meus produtos")
    @GetMapping("/usuario/meusProdutos")
    public String meusProdutos(Model model, Principal principal) {

        List<ProdutoUsuario> produtos = produtoUsuarioService.getByUsuarioUsername(principal.getName());
        model.addAttribute("produtos", produtos);
        return "usuarioMeusProdutos";
    }

    @Operation(summary = "Visual vincular meus pedidos", description = "vincular meus pedidos")
    @GetMapping("/usuario/meusPedidos")
    public String meusPedidos(Model model, Principal principal) {

        List<Frete> fretesUsuario = freteService.getAll();
        List<Frete> fretesUsuarioFiltrados = fretesUsuario.stream()
                .filter(frete -> frete.getPedidoUsuario().getUsuario().getUsername().equals(principal.getName()))
                .collect(Collectors.toList());

        model.addAttribute("fretesUsuario", fretesUsuarioFiltrados);
        return "usuarioMeusPedidos";
    }

    @Operation(summary = "Visual solicitar frete", description = "Visual solicitar frete")
    @GetMapping("/usuario/solicitarFrete")
    public String empresaFrete(Model model, Principal principal) {
        List<Frete> fretes = freteService.getAll();
        model.addAttribute("fretes", fretes);

        List<PedidoUsuario> pedidos = pedidosUsuarioService.getByUsuarioUsername(principal.getName());
        List<PedidoUsuario> pedidosFiltrados = pedidos.stream()
                .filter(pedido -> !pedido.isStatusPedido())
                .collect(Collectors.toList());

        model.addAttribute("pedidos", pedidosFiltrados);

        List<Empresa> listaEmpresas = empresaService.getAll();
        model.addAttribute("empresas", listaEmpresas);
        return "usuarioSolicitarFrete";
    }

    @Operation(summary = "Visual solicitar frete por empresa especifica", description = "Visual solicitar frete especifica")
    @GetMapping("/usuario/solicitarFrete/{empresaId}")
    public String empresaFreteId(Model model, Principal principal) {
        model.addAttribute("frete", new Frete());

        List<PedidoUsuario> pedidos = pedidosUsuarioService.getByUsuarioUsername(principal.getName());
        List<PedidoUsuario> pedidosFiltrados = pedidos.stream()
                .filter(pedido -> !pedido.isStatusPedido())
                .collect(Collectors.toList());

        model.addAttribute("pedidos", pedidosFiltrados);

        List<Empresa> listaEmpresas = empresaService.getAll();
        model.addAttribute("empresas", listaEmpresas);
        return "usuarioSolicitarFrete";
    }

}
