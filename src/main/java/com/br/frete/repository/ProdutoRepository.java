package com.br.frete.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.frete.entity.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findAll();

    Optional<Produto> findByNomeAndEmpresa_Id(String nome, Long empresaId);

    public List<Produto> findAllByEmpresa_Id(Long id);

}
