package com.br.frete.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.frete.entity.PedidoUsuario;
import com.br.frete.repository.PedidoUsuarioRepository;

@Service
public class PedidoUsuarioService {

    private final PedidoUsuarioRepository pedidoUsuarioRepository;

    @Autowired
    public PedidoUsuarioService(PedidoUsuarioRepository pedidoUsuarioRepository) {
        this.pedidoUsuarioRepository = pedidoUsuarioRepository;
    }

    public PedidoUsuario save(PedidoUsuario pedidoUsuario) {
        return pedidoUsuarioRepository.save(pedidoUsuario);
    }

    public List<PedidoUsuario> getByUsuarioUsername(String usuario) {
        return pedidoUsuarioRepository.findByUsuarioUsername(usuario);
    }

    public PedidoUsuario getById(Long id) {
        Optional<PedidoUsuario> pedidoUsuario = pedidoUsuarioRepository.findById(id);
        if (pedidoUsuario.isPresent()) {
            return pedidoUsuario.get();
        } else {
            throw new NoSuchElementException("PedidoUsuario with id " + id + " does not exist");
        }
    }

}