package com.br.frete.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.frete.entity.Empresa;
import com.br.frete.entity.Produto;
import com.br.frete.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> getAll() {
        return produtoRepository.findAll();
    }

    public Produto getById(Long id) {
        return produtoRepository.findById(id).isPresent() ? produtoRepository.findById(id).get() : null;
    }

    public Optional<Produto> findByNomeAndEmpresa(String nome, Long empresa) {
        return produtoRepository.findByNomeAndEmpresa_Id(nome, empresa);
    }

    public List<Produto> getAllById(Long id) {
        return produtoRepository.findAllByEmpresa_Id(id);
    }

    public Optional<Produto> getByEmpresa(Empresa empresa) {
        return produtoRepository.findById(empresa.getId()).isPresent() ? produtoRepository.findById(empresa.getId())
                : Optional.empty();
    }

    public List<Produto> getByEmpresaId(Long id) {
        return produtoRepository.findAllByEmpresa_Id(id);
    }

    public Produto save(Produto produto) {
        return produtoRepository.findByNomeAndEmpresa_Id(produto.getNome(), produto.getEmpresa().getId()).isPresent()
                ? produtoRepository.findByNomeAndEmpresa_Id(produto.getNome(), produto.getEmpresa().getId()).get()
                : produtoRepository.save(produto);
    }

    public void deleteById(Long id) {
        produtoRepository.deleteById(id);
    }

    public void deleteAll() {
        produtoRepository.deleteAll();
    }

}
