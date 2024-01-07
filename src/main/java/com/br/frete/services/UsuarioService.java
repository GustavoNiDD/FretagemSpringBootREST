package com.br.frete.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.frete.entity.Role;
import com.br.frete.entity.Usuario;
import com.br.frete.repository.RoleRepository;
import com.br.frete.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    public Usuario save(Usuario usuario) {
        if (usuarioRepository.findByUsername(usuario.getUsername()) != null) {
            throw new RuntimeException("Usuário já cadastrado");
        } else {
            Role roleUsuario = roleRepository.findByName("USUARIO");
            if (roleUsuario == null) {
                throw new RuntimeException("Role USUARIO não encontrada");
            }
            usuario.setRoles(Collections.singleton(roleUsuario));
            return usuarioRepository.save(usuario);
        }
    }

    public Usuario getById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario getByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario update(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario logar(Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findByUsernameAndPassword(usuario.getUsername(),
                usuario.getPassword());
        if (usuarioExistente != null) {
            return usuarioExistente;
        } else {
            throw new RuntimeException("Usuário ou senha inválidos");
        }
    }

    public boolean verificarRoleName(Long id) {
        if (usuarioRepository.existsByUsuarioIdAndRoleName(id) == 1) {
            return true;
        } else {
            return false;
        }
    }
}
