package com.br.frete.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.frete.entity.ProdutoUsuario;

@Repository
public interface ProdutoUsuarioRepository extends JpaRepository<ProdutoUsuario, Long> {

    public List<ProdutoUsuario> findByUsuarioUsername(String username);

    public List<ProdutoUsuario> findAllById(Long id);


}