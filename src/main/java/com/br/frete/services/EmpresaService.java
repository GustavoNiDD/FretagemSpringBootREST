package com.br.frete.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.frete.entity.Empresa;
import com.br.frete.entity.Role;
import com.br.frete.repository.EmpresaRepository;
import com.br.frete.repository.RoleRepository;

@Service
public class EmpresaService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public List<Empresa> getAll() {
        return empresaRepository.findAll();
    }

    public Empresa getByUsername(String username) {
        return empresaRepository.findByUsername(username) != null ? empresaRepository.findByUsername(username) : null;
    }

    public Empresa getById(Long id) {
        return empresaRepository.findById(id).isPresent() ? empresaRepository.findById(id).get() : null;
    }

    public Empresa save(Empresa empresa) {
        if (empresaRepository.findByCnpj(empresa.getCnpj()) != null) {
            throw new RuntimeException("Empresa já cadastrada");
        } else {
            Role roleEmpresa = roleRepository.findByName("EMPRESA");
            if (roleEmpresa == null) {
                throw new RuntimeException("Role EMPRESA não encontrada");
            }
            empresa.setRoles(Collections.singleton(roleEmpresa));
            return empresaRepository.save(empresa);
        }
    }

    public Empresa logar(Empresa empresa) {
        List<Empresa> empresaExistente = empresaRepository.findByUsernameAndPassword(empresa.getUsername(),
                empresa.getPassword());
        if (!empresaExistente.isEmpty()) {
            return empresaExistente.get(0);
        } else {
            throw new RuntimeException("Usuário ou senha inválidos");
        }
    }

    public boolean verificarRoleName(Long id) {
        if (empresaRepository.existsByEmpresaIdAndRoleName(id) == 1) {
            return true;
        } else {
            return false;
        }
    }

}
