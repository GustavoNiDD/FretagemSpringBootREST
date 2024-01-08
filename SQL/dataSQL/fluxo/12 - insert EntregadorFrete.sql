-- Active: 1693676830144@@127.0.0.1@3306@fretes_infinity
INSERT INTO entregador_frete (id_frete, id_entregador)
VALUES (1, 1), 
       (2, 2);


UPDATE fretes
SET status_entregador = 'true'
WHERE id_empresa IN (1, 2) AND id_pedido_usuario IN (1, 2);