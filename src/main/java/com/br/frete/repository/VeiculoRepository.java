package com.br.frete.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.frete.entity.Veiculo;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    @Override
    List<Veiculo> findAll();

    Veiculo findById(long id);

    Veiculo findByPeso(int peso);

    Veiculo deleteById(int id);

}
