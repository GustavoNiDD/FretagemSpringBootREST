package com.br.frete.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.frete.entity.EntregadorFrete;

@Repository
public interface EntregadorFreteRepository extends JpaRepository<EntregadorFrete, Long> {

    List<EntregadorFrete> findAll();

    List<EntregadorFrete> findByEntregadorId(Long id);

    List<EntregadorFrete> findByFreteId(Long id);

}