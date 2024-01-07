package com.br.frete.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.frete.entity.ProdutosUsuarioPedido;
import com.br.frete.repository.ProdutosUsuarioPedidoRepository;

@Service
public class ProdutosUsuarioPedidoService {

    private final ProdutosUsuarioPedidoRepository produtosUsuarioPedidoRepository;

    @Autowired
    public ProdutosUsuarioPedidoService(ProdutosUsuarioPedidoRepository produtosUsuarioPedidoRepository) {
        this.produtosUsuarioPedidoRepository = produtosUsuarioPedidoRepository;
    }

    public List<ProdutosUsuarioPedido> getByUsuarioId(Long id) {
        return produtosUsuarioPedidoRepository.findByUsuarioId(id);
    }

    public ProdutosUsuarioPedido save(ProdutosUsuarioPedido produtosUsuarioPedido) {
        return produtosUsuarioPedidoRepository.save(produtosUsuarioPedido);
    }

    public ProdutosUsuarioPedido findById(Long id) {
        return produtosUsuarioPedidoRepository.findById(id).get();
    }

    public List<ProdutosUsuarioPedido> findAll() {
        return produtosUsuarioPedidoRepository.findAll();
    }

    public List<ProdutosUsuarioPedido> getByPedidoUsuarioId(Long id) {
        return produtosUsuarioPedidoRepository.findByPedidoUsuarioId(id);
    }

    public List<ProdutosUsuarioPedido> findByPedido_Id(Long id) {
        return produtosUsuarioPedidoRepository.findByPedidoUsuario_Id(id);
    }

}