package com.br.frete.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.frete.entity.Entregador;
import com.br.frete.entity.Role;
import com.br.frete.repository.EntregadorRepository;
import com.br.frete.repository.RoleRepository;

@Service
public class EntregadorService {

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private RoleRepository roleRepository;

    public Iterable<Entregador> getAll() {
        return entregadorRepository.findAll();
    }

    public Entregador getByUsername(String username) {
        Optional<Entregador> entregadorOptional = entregadorRepository.findByUsername(username);
        if (entregadorOptional.isPresent()) {
            return entregadorOptional.get();
        }
        return null;
    }

    public Entregador save(Entregador entregador) {
        Optional<Entregador> entregadorOptional = entregadorRepository.findByCpf(entregador.getCpf());
        if (entregadorOptional.isPresent()) {
            throw new RuntimeException("Entregador já cadastrado");
        } else {
            Role roleEntregador = roleRepository.findByName("ENTREGADOR");
            if (roleEntregador == null) {
                throw new RuntimeException("Role ENTREGADOR não encontrada");
            }
            entregador.setRoles(Collections.singleton(roleEntregador));
            return entregadorRepository.save(entregador);
        }
    }

    public Entregador logar(Entregador entregador) {
        List<Entregador> entregadorExistente = entregadorRepository.findByUsernameAndPassword(entregador.getUsername(),
                entregador.getPassword());
        if (entregadorExistente != null) {
            return entregadorExistente.get(0);
        } else {
            throw new RuntimeException("Usuário ou senha inválidos");
        }
    }

    public boolean verificarRoleName(Long id) {
        if (entregadorRepository.existsByEntregadorIdAndRoleName(id) == 1) {
            return true;
        } else {
            return false;
        }
    }

}