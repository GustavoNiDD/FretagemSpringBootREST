package com.br.frete.controllers;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.br.frete.entity.Empresa;
import com.br.frete.entity.Frete;
import com.br.frete.entity.Produto;
import com.br.frete.entity.ProdutosFrete;
import com.br.frete.entity.ProdutosUsuarioPedido;
import com.br.frete.entity.Usuario;
import com.br.frete.services.EmpresaService;
import com.br.frete.services.FreteService;
import com.br.frete.services.ProdutoService;
import com.br.frete.services.ProdutosUsuarioPedidoService;
import com.br.frete.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@Tag(name = "Empresa API", description = "Operações relacionadas a empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FreteService freteService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutosUsuarioPedidoService produtosUsuarioPedidoService;

    public void addAttribute(String name, Object object, Model model) {
        model.addAttribute("empresa", new Empresa());
    }

    @GetMapping("/empresa/cadastrar")
    @Operation(summary = "Cadastrar uma nova empresa")
    public String empresaCadastrar(Model model) {
        addAttribute("empresa", new Empresa(), model);
        return "empresaCadastrar";
    }

    @GetMapping("/empresa/logar")
    @Operation(summary = "Logar uma empresa")
    public String empresaLogar(Model model) {
        addAttribute("empresa", new Empresa(), model);
        return "empresaLogar";
    }

    @GetMapping("/empresa/logout")
    @Operation(summary = "Logout da empresa")
    public String empresaLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "empresaLogar";
    }

    @GetMapping("/empresa/home")
    @Operation(summary = "Home da empresa")
    public String empresaHome(Model model) {
        addAttribute("empresa", new Empresa(), model);
        return "empresaHome";
    }

    @GetMapping("/empresa/todosFretes")
    @Operation(summary = "Obter todos os fretes cadastrados")
    public String empresaConsultar(Model model) {
        List<Frete> fretes = freteService.getAll();
        model.addAttribute("fretes", fretes);
        return "empresaFreteLista";
    }

    @GetMapping("/empresa/adicionarFrete")
    @Operation(summary = "Adicionar um novo frete")
    public String empresaFrete(Model model, Principal principal) {
        model.addAttribute("frete", new Frete());

        List<Usuario> usuarios = usuarioService.getAll();
        model.addAttribute("usuarios", usuarios);

        String nomeEmpresaLogada = principal.getName();
        Empresa empresaLogada = empresaService.getByUsername(nomeEmpresaLogada);
        model.addAttribute("empresas", empresaLogada);
        return "empresaFrete";
    }

    @GetMapping("/empresa/adicionarProdutos")
    @Operation(summary = "Adicionar produtos a um frete")
    public String empresaProdutos(Model model, Principal principal) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("frete", new Frete());
        Empresa empresa = empresaService.getById(empresaService.getByUsername(principal.getName()).getId());
        model.addAttribute("empresas", empresa);
        return "empresaAdicionarProdutos";
    }

    @GetMapping("/empresa/adicionarProdutosFrete")
    @Operation(summary = "Adicionar produtos a um frete")
    public String empresaProdutosFrete(Model model, Principal principal) {
        model.addAttribute("produto", new ProdutosFrete());
        model.addAttribute("frete", new Frete());
        List<Frete> fretes = freteService.getFreteEmpresa(empresaService.getByUsername(principal.getName()).getId());
        model.addAttribute("fretes", fretes);
        List<Produto> produtos = produtoService
                .getByEmpresaId(empresaService.getByUsername(principal.getName()).getId());
        model.addAttribute("produtos", produtos);
        return "empresaFreteProdutos";
    }

    @GetMapping("/empresa/produto")
    @Operation(summary = "Visualizar produtos da empresa")
    public String empresaCadastrarProduto(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            Empresa empresa = empresaService.getByUsername(username);
            List<Produto> produtos = produtoService.getAllById(empresa.getId());
            model.addAttribute("produtos", produtos);
            return "empresaProdutoLista";
        } else {
            return "empresaLogar";
        }
    }

    @GetMapping("/empresa/listarProdutos")
    @Operation(summary = "Listar todos os produtos cadastrados")
    public String empresaConsultarProdutos(Model model) {
        List<Produto> produtos = produtoService.getAll();
        model.addAttribute("produtos", produtos);
        return "empresaProdutoLista";
    }

    @GetMapping("/empresa/visualizarProdutosPorPedido/{id}")
    @Operation(summary = "Visualizar produtos por pedido")
    @ResponseBody
    public List<Object[]> visualizarProdutosPorPedido(@PathVariable("id") Long id) {
        List<ProdutosUsuarioPedido> produtosUsuarioFrete = produtosUsuarioPedidoService.getByPedidoUsuarioId(id);

        List<Object[]> produtosQuantidade = produtosUsuarioFrete.stream()
                .map(produtosUsuarioPedido -> new Object[] { produtosUsuarioPedido.getProdutoUsuario(),
                        produtosUsuarioPedido.getQuantidade() })
                .collect(Collectors.toList());

        return produtosQuantidade;
    }

    @GetMapping("/empresa/meusPedidos")
    @Operation(summary = "Visualizar pedidos da empresa")
    public String visualizarPedidosEmpresa(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            Empresa empresa = empresaService.getByUsername(username);
            List<Frete> fretes = freteService.getAllByEmpresaId(empresa.getId());

            model.addAttribute("pedidos", fretes);
            return "empresaMeusPedidos";
        } else {
            return "empresaLogar";
        }
    }
}
