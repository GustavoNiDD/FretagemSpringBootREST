package com.br.frete.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.frete.entity.ProdutosUsuarioPedido;

@Repository
public interface ProdutosUsuarioPedidoRepository extends JpaRepository<ProdutosUsuarioPedido, Long> {

    List<ProdutosUsuarioPedido> findByPedidoUsuarioId(Long id);

    List<ProdutosUsuarioPedido> findByPedidoUsuario_Id(Long pedidoUsuarioId);

    @Query(value = "SELECT pup.* FROM Produtos_Usuario_frete AS pup JOIN Pedido_Usuario AS pu ON pup.id_pedido = pu.id JOIN Usuario AS u ON pu.id_usuario = u.id WHERE u.id = :usuarioId", nativeQuery = true)
    List<ProdutosUsuarioPedido> findByUsuarioId(@Param("usuarioId") Long usuarioId);

}