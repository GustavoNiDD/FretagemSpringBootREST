-- INSERT INTO fretes (id_empresa, id_usuario, status_entrega)
-- VALUES (1, 1, 'pendente'),(2, 2, 'pendente');

INSERT INTO fretes (id_empresa, id_pedido_usuario, status_entrega, status_entregador)
VALUES (1, 1, 'pendente', false), 
       (2, 2, 'pendente', false);