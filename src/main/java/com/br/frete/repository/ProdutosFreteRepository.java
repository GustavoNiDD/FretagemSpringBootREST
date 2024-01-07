package com.br.frete.repository;

import java.util.List;

// ProdutosFreteRepository.java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.frete.entity.Frete;
import com.br.frete.entity.Produto;
import com.br.frete.entity.ProdutosFrete;

@Repository
public interface ProdutosFreteRepository extends JpaRepository<ProdutosFrete, Long> {

    List<ProdutosFrete> findAll();

    ProdutosFrete findById(long id);

    @Query(value= "SELECT * FROM produtos_frete pf WHERE pf.id_frete IN :freteIds", nativeQuery = true)
    List<ProdutosFrete> findByFreteIds(@Param("freteIds") List<Long> freteIds);

    void deleteById(Long id);

    ProdutosFrete findByFreteAndProduto(Frete frete, Produto produto);

}
