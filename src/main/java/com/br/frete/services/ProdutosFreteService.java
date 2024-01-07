package com.br.frete.services;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.frete.entity.Frete;
import com.br.frete.entity.Produto;
import com.br.frete.entity.ProdutosFrete;
import com.br.frete.repository.ProdutosFreteRepository;

@Service
public class ProdutosFreteService {

    private final ProdutosFreteRepository produtosFreteRepository;

    @Autowired
    public ProdutosFreteService(ProdutosFreteRepository produtosFreteRepository) {
        this.produtosFreteRepository = produtosFreteRepository;
    }

    public List<ProdutosFrete> getAllProdutosFrete() {
        return produtosFreteRepository.findAll();
    }

    public ProdutosFrete findByFreteAndProduto(Frete frete, Produto produto) {
        return produtosFreteRepository.findByFreteAndProduto(frete, produto);
    }

    public List<ProdutosFrete> getProdutosFreteByFreteIds(List<Long> freteIds) {
        return produtosFreteRepository.findByFreteIds(freteIds);
    }

    public Optional<ProdutosFrete> getProdutosFreteById(Long id) {
        return produtosFreteRepository.findById(id);
    }

    public ProdutosFrete save(ProdutosFrete produtosFrete) {
        return produtosFreteRepository.save(produtosFrete);
    }

    public void deleteProdutosFreteById(Long id) {
        produtosFreteRepository.deleteById(id);
    }

}
