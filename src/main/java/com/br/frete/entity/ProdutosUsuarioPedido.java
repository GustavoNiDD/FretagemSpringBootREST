package com.br.frete.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produtos_usuario_frete")
@Entity(name = "produtos_usuario_frete")
public class ProdutosUsuarioPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_produto_usuario", nullable = false)
    private ProdutoUsuario produtoUsuario;

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private PedidoUsuario pedidoUsuario;

    @Column(nullable = false)
    private Integer quantidade;

}