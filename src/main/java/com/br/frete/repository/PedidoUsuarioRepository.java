package com.br.frete.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.frete.entity.PedidoUsuario;

@Repository
public interface PedidoUsuarioRepository extends JpaRepository<PedidoUsuario, Long> {

    public List<PedidoUsuario> findByUsuarioUsername(String username);

}