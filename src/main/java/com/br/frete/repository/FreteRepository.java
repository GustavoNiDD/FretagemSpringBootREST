package com.br.frete.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.frete.entity.Frete;
import com.br.frete.entity.StatusEntrega;

@Repository
public interface FreteRepository extends JpaRepository<Frete, Long> {

    List<Frete> findAll();

    List<Frete> findByEmpresaId(Long empresaId);

    List<Frete> findByStatusEntrega(StatusEntrega statusEntrega);

    @Query("SELECT f FROM fretes f WHERE f.statusEntregador = false")
    List<Frete> findFretesWithStatusEntregadorFalse();

}