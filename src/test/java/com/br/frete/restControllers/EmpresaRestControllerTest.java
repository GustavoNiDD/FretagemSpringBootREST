package com.br.frete.restControllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import com.br.frete.entity.Empresa;
import com.br.frete.entity.Frete;
import com.br.frete.entity.Produto;
import com.br.frete.entity.ProdutoUsuario;
import com.br.frete.entity.ProdutosFrete;
import com.br.frete.entity.Role;
import com.br.frete.entity.StatusEnum;
import com.br.frete.services.EmpresaService;
import com.br.frete.services.FreteService;
import com.br.frete.services.ProdutoService;
import com.br.frete.services.ProdutoUsuarioService;
import com.br.frete.services.ProdutosFreteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class EmpresaRestControllerTest {

    // @InjectMocks
    // EmpresaRestController empresaRestController;

    @MockBean
    EmpresaService empresaService;

    @MockBean
    ProdutoService produtoService;

    @MockBean
    FreteService freteService;

    @MockBean
    ProdutoUsuarioService produtoUsuarioService;

    @MockBean
    ProdutosFreteService produtosFreteService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    EmpresaRestController empresaRestController;

    // @Mock
    // private EmpresaRestController empresaRestController;

    @Test
    public void testCadastrar() throws Exception {
        // Mock de dados
        Empresa empresa = new Empresa();
        empresa.setId(1L);
        when(empresaService.save(any(Empresa.class))).thenReturn(empresa);

        // Executar a requisição
        mockMvc.perform(post("/empresa/cadastrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empresa)))
                .andExpect(status().isOk());

        // Verificar se o serviço foi chamado corretamente
        verify(empresaService, times(1)).save(any(Empresa.class));
    }

    @Test
    public void testCadastrarProduto() throws Exception {
        // Crie uma empresa completa
        Empresa empresa = new Empresa();
        empresa.setNome("Empresa Teste");
        empresa.setPassword("senhaTeste");
        empresa.setUsername("teste@teste.com");
        empresa.setCnpj("12345678901234");
        empresa.setEndereco("Endereço Teste");
        empresa.setTelefone("123456789");
        empresa.setDataCriacao(new Date(System.currentTimeMillis())); // Set to current date and time
        empresa.setStatus(StatusEnum.ativo);
        Role role = new Role();
        role.setName("EMPRESA");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        empresa.setRoles(roles);

        // Mock the authentication and security context
        Set<GrantedAuthority> grantedAuthorities = roles.stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toSet());

        Authentication auth = new UsernamePasswordAuthenticationToken(empresa, null, grantedAuthorities);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);

        // Mock de dados do produto
        Produto produto = new Produto();
        produto.setId(1L);
        when(produtoService.save(any(Produto.class))).thenReturn(produto);

        // Executar a requisição para cadastrar o produto
        mockMvc.perform(post("/empresa/cadastrarProduto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isOk());

        // Verificar se o serviço foi chamado corretamente
        verify(produtoService, times(1)).save(any(Produto.class));
    }

    @Test
    public void testSalvarProdutosFrete() throws Exception {
        Map<String, Object> dados = new HashMap<>();
        dados.put("produtos", new ArrayList<>());
        dados.put("freteId", 1L);

        // Crie uma empresa completa
        Empresa empresa = new Empresa();
        empresa.setNome("Empresa Teste");
        empresa.setPassword("senhaTeste");
        empresa.setUsername("teste@teste.com");
        empresa.setCnpj("12345678901234");
        empresa.setEndereco("Endereço Teste");
        empresa.setTelefone("123456789");
        empresa.setDataCriacao(new Date(System.currentTimeMillis())); // Set to current date and time
        empresa.setStatus(StatusEnum.ativo);
        Role role = new Role();
        role.setName("EMPRESA");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        empresa.setRoles(roles);

        // Mock the authentication and security context
        Set<GrantedAuthority> grantedAuthorities = roles.stream()
            .map(r -> new SimpleGrantedAuthority(r.getName()))
            .collect(Collectors.toSet());
        Authentication auth = new UsernamePasswordAuthenticationToken(empresa, null, grantedAuthorities);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);

        // Mock the services
        when(produtoUsuarioService.findById(anyLong())).thenReturn(new ProdutoUsuario());
        when(empresaService.getByUsername(anyString())).thenReturn(empresa);
        when(freteService.getById2(anyLong())).thenReturn(new Frete());
        when(produtoService.save(any(Produto.class))).thenReturn(new Produto());
        when(produtoService.getById(anyLong())).thenReturn(new Produto());
        when(produtosFreteService.findByFreteAndProduto(any(Frete.class), any(Produto.class))).thenReturn(null);

        mockMvc.perform(post("/empresa/salvarProdutosFrete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dados)))
                .andExpect(status().isOk())
                .andExpect(content().string("Sucesso"));
    }

    @Test
    public void testCadastrarProdutoFrete() throws Exception {
        // Crie uma empresa completa
        Empresa empresa = new Empresa();
        empresa.setNome("Empresa Teste");
        empresa.setPassword("senhaTeste");
        empresa.setUsername("teste@teste.com");
        empresa.setCnpj("12345678901234");
        empresa.setEndereco("Endereço Teste");
        empresa.setTelefone("123456789");
        empresa.setDataCriacao(new Date(System.currentTimeMillis())); // Set to current date and time
        empresa.setStatus(StatusEnum.ativo);
        Role role = new Role();
        role.setName("EMPRESA");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        empresa.setRoles(roles);

        // Mock the authentication and security context
        Set<GrantedAuthority> grantedAuthorities = roles.stream()
            .map(r -> new SimpleGrantedAuthority(r.getName()))
            .collect(Collectors.toSet());
        Authentication auth = new UsernamePasswordAuthenticationToken(empresa, null, grantedAuthorities);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);

        // Test the /empresa/cadastrarProdutoFrete endpoint
        ProdutosFrete produtoFrete = new ProdutosFrete();
        when(produtosFreteService.save(produtoFrete)).thenReturn(produtoFrete);

        mockMvc.perform(post("/empresa/cadastrarProdutoFrete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(produtoFrete)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(produtoFrete)));

        verify(produtosFreteService, times(1)).save(produtoFrete);
    }

}