package com.br.frete.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.br.frete.entity.Usuario;
import com.br.frete.services.EmpresaService;
import com.br.frete.services.EntregadorService;
import com.br.frete.services.ProdutoService;
import com.br.frete.services.VeiculoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "General API", description = "Operações gerais")
@Controller
public class GeneralController {

    @Operation(summary = "Home", description = "Página inicial")
    @GetMapping(value = "/home")
    public String home(Model model) {
        return "home";
    }

    @GetMapping(value = "/maps")
    public String maps(Model model) {
        return "maps";
    }
}