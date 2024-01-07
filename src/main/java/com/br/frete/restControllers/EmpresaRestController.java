package com.br.frete.restControllers;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.br.frete.entity.Empresa;
import com.br.frete.entity.Frete;
import com.br.frete.entity.Produto;
import com.br.frete.entity.ProdutoUsuario;
import com.br.frete.entity.ProdutosFrete;
import com.br.frete.entity.StatusEntrega;
import com.br.frete.services.EmpresaService;
import com.br.frete.services.FreteService;
import com.br.frete.services.ProdutoService;
import com.br.frete.services.ProdutoUsuarioService;
import com.br.frete.services.ProdutosFreteService;
import com.br.frete.services.RoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Tag(name = "EmpresaRest API ", description = "Operações relacionadas a empresa")
@RestController
@RequestMapping("/empresa")
public class EmpresaRestController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutosFreteService produtosFreteService;

    @Autowired
    private FreteService freteService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    ProdutoUsuarioService produtoUsuarioService;

    private static final Logger logger = LoggerFactory.getLogger(EmpresaRestController.class);

    @Operation(summary = "Cadastrar empresa", description = "Cadastra uma nova empresa")
    @PostMapping("/cadastrar")
    public Empresa cadastrar(@RequestBody Empresa empresa) {
        return empresaService.save(empresa);
    }

    @Operation(summary = "Cadastrar produto", description = "Cadastra um novo produto")
    @PostMapping("/cadastrarProduto")
    public Produto cadastrarProduto(@RequestBody Produto produto) {
        return produtoService.save(produto);
    }

    @Operation(summary = "Logar empresa", description = "Realiza o login da empresa")
    @PostMapping("/logar")
    public Empresa logar(@RequestBody Empresa empresa, HttpSession session) {

        Empresa loggedEmpresa = empresaService.logar(empresa);

        if (loggedEmpresa != null) {
            logger.info("Empresa logada com sucesso");

            if (empresaService.verificarRoleName(loggedEmpresa.getId())) {
                logger.info("Empresa possui o papel EMPRESA");
                List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("EMPRESA");
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        loggedEmpresa.getUsername(),
                        loggedEmpresa.getPassword(),
                        authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                        SecurityContextHolder.getContext());

                return loggedEmpresa;
            } else {
                logger.info("Empresa não possui o papel EMPRESA");
                return null;
            }

        } else {
            logger.info("Empresa não logada");
            return null;
        }

    }

    @Operation(summary = "Logout da empresa", description = "Realiza o logout da empresa")
    @PostMapping("/logout")
    public void logout(HttpSession session, HttpServletResponse response) throws IOException {
        SecurityContextHolder.clearContext();
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("/home");
    }

    @Operation(summary = "Salvar produtos frete", description = "Salva os produtos de um frete")
    @PostMapping("/salvarProdutosFrete")
    @ResponseBody
    public String salvarProdutosFrete(@RequestBody Map<String, Object> dados, Principal principal) {
        List<Map<String, Object>> produtos = (List<Map<String, Object>>) dados.get("produtos");

        Long freteId = ((Number) dados.get("freteId")).longValue();

        logger.info("Produtos: {}", produtos);
        logger.info("Frete ID: {}", freteId);

        for (int i = 0; i < produtos.size(); i++) {
            Map<String, Object> produtoDados = produtos.get(i);
            String produtoIdStr = (String) produtoDados.get("id");
            Long produtoId = Long.parseLong(produtoIdStr);
            ProdutoUsuario produtoUsuario = produtoUsuarioService.findById(produtoId);
            if (produtoUsuario != null) {
                Produto produto = new Produto();
                produto.setNome(produtoUsuario.getNome());
                produto.setValor(produtoUsuario.getValor());
                produto.setPeso(produtoUsuario.getPeso());
                produto.setDescricaoProduto(produtoUsuario.getDescricaoProduto());
                produto.setEmpresa(empresaService.getByUsername(principal.getName()));

                Frete frete = freteService.getById2(freteId);
                Produto produto2 = null;
                if (frete.getStatusEntrega().equals(StatusEntrega.pendente)) {
                    produtoService.save(produto);
                    produto2 = produtoService.getById(produto.getId());
                } else {
                    produto2 = produtoService.findByNomeAndEmpresa(produto.getNome(), produto.getEmpresa().getId())
                            .get();
                }

                ProdutosFrete produtosFreteExistente = produtosFreteService.findByFreteAndProduto(frete, produto2);
                if (produtosFreteExistente != null) {

                    produtosFreteExistente.setQuantidade(Integer.parseInt(produtoDados.get("quantidade").toString()));
                    produtosFreteService.save(produtosFreteExistente);
                } else {

                    ProdutosFrete produtosFrete = new ProdutosFrete();
                    produtosFrete.setFrete(frete);
                    produtosFrete.setProduto(produto2);
                    produtosFrete.setQuantidade(Integer.parseInt(produtoDados.get("quantidade").toString()));
                    produtosFreteService.save(produtosFrete);
                }

                if (i == produtos.size() - 1) {
                    frete.setStatusEntrega(StatusEntrega.em_andamento);
                    freteService.save(frete);
                }

                logger.info("Produto: {}", produto);
            } else {
                logger.error("Produto com ID {} não encontrado", produtoId);
            }
        }
        return "Sucesso";

    }

    @Operation(summary = "Cadastrar produto frete", description = "Cadastra um produto de um frete")
    @PostMapping("/cadastrarProdutoFrete")
    public ProdutosFrete cadastrarProdutoFrete(@RequestBody ProdutosFrete produtoFrete) {
        return produtosFreteService.save(produtoFrete);
    }
}
