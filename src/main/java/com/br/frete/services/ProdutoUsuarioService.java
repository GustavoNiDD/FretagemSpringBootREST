package com.br.frete.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.frete.entity.ProdutoUsuario;
import com.br.frete.repository.ProdutoUsuarioRepository;

@Service
public class ProdutoUsuarioService {

    private final ProdutoUsuarioRepository produtoUsuarioRepository;

    @Autowired
    public ProdutoUsuarioService(ProdutoUsuarioRepository produtoUsuarioRepository) {
        this.produtoUsuarioRepository = produtoUsuarioRepository;
    }

    public ProdutoUsuario save(ProdutoUsuario produtoUsuario) {
        return produtoUsuarioRepository.save(produtoUsuario);
    }

    public List<ProdutoUsuario> getByUsuarioUsername(String usuario) {
        return produtoUsuarioRepository.findByUsuarioUsername(usuario);
    }

    public ProdutoUsuario findById(Long id) {
        return produtoUsuarioRepository.findById(id).get();
    }

    public List<ProdutoUsuario> getAllById(Long id) {
        return produtoUsuarioRepository.findAllById(id);
    }

}