package com.br.frete.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.frete.entity.Frete;
import com.br.frete.services.FreteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "FreteRest API", description = "Operações relacionadas a fretes")
@RestController
@RequestMapping("/frete")
public class FreteRestController {

    @Autowired
    private FreteService freteService;

    @Operation(summary = "Cadastrar um frete", description = "Cadastra um novo frete")
    @PostMapping("/cadastrar")
    public Frete cadastrar(@RequestBody Frete frete) {
        return freteService.save(frete);
    }

}
