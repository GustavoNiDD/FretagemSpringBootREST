-- Active: 1693676830144@@127.0.0.1@3306@fretes_infinity

INSERT INTO
    produtos_frete (
        id_frete,
        id_produto,
        quantidade
    )
VALUES (1, 1, 5), (1, 2, 7), (2, 3, 2), (2, 4, 1);

UPDATE fretes
SET status_entrega = 'em_andamento'
WHERE id_empresa IN (1, 2) AND id_pedido_usuario IN (1, 2);