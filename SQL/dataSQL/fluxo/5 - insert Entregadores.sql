-- -- Insere os entregadores na tabela entregadores
-- INSERT INTO entregadores (nome, username, password, cpf, endereco, telefone, email, data_nascimento, id_tipo_veiculo) VALUES 
-- ('Entregador 1', 'user1', 'pass1', '11111111111', 'Endereço 1', '1111-1111', 'entregador1@email.com', '2000-01-01', 4), 
-- ('Entregador 2', 'user2', 'pass2', '22222222222', 'Endereço 2', '2222-2222', 'entregador2@email.com', '2000-01-01', 5), 
-- ('Entregador 3', 'user3', 'pass3', '33333333333', 'Endereço 3', '3333-3333', 'entregador3@email.com', '2000-01-01', 10);

-- -- Obtém os IDs dos entregadores que acabamos de inserir
-- SET @entregador_id1 = (SELECT id FROM entregadores WHERE username = 'user1');
-- SET @entregador_id2 = (SELECT id FROM entregadores WHERE username = 'user2');
-- SET @entregador_id3 = (SELECT id FROM entregadores WHERE username = 'user3');

-- -- Insere registros na tabela de junção entregador_role para associar os entregadores à role
-- INSERT INTO entregador_role (entregador_id, role_id) VALUES 
-- (@entregador_id1, 2), 
-- (@entregador_id2, 2), 
-- (@entregador_id3, 2);

INSERT INTO entregadores (nome, username, password, cpf, endereco, telefone, email, data_nascimento, id_tipo_veiculo)
VALUES ('Carlos Souza', 'carlossouza', 'password', '12345678901', 'Endereço Carlos', '11111111111', 'carlos@email.com', '1980-01-01', 4),
VALUES ('Ana Pereira', 'anapereira', 'password', '23456789012', 'Endereço Ana', '22222222222', 'ana@email.com', '1985-02-02', 5),
VALUES ('Roberto Alves', 'robertoalves', 'password', '34567890123', 'Endereço Roberto', '33333333333', 'roberto@email.com', '1990-03-03', 10);

INSERT INTO entregador_role (entregador_id, role_id)
SELECT id, (SELECT id FROM roles WHERE name = 'ENTREGADOR') FROM entregadores WHERE username IN ('carlossouza', 'anapereira', 'robertoalves');