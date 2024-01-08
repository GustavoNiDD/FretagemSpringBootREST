create database fretes_infinity;

# CRIE A DATABASE, INICIE A API, DESSA FORMA AS TABLES SERÃO CRIADAS
# MAS CASO QUEIRA TAMBÉM É POSSIVEL CRIAR A DATABASE COMPLETA PELO ARQUIVO databaseTables.sql

use fretes_infinity;
#Roles
INSERT INTO roles (name) VALUES ("EMPRESA"), ('ENTREGADOR'), ('USUARIO');

#veiculos
INSERT INTO
    veiculos (tipo_veiculo, peso)
VALUES 
    ('Bicicleta', 1),
    ('Moto', 2),
    ('Carro', 3),
    ('Furgão', 4),
    ('Caminhonete', 5),
    ('Caminhão Pequeno', 7),
    ('Caminhão Médio', 8),
    ('Caminhão Grande', 9),
    ('Caminhão Extra Grande', 10);
    
 #Usuario
INSERT INTO usuario (nome, username, password, distancia)
VALUES ('João Silva', 'joaosilva', 'joao', 10);

INSERT INTO usuario (nome, username, password, distancia)
VALUES ('Maria Santos', 'mariasantos', 'maria', 20);

INSERT INTO usuario_role (usuario_id, role_id)
SELECT id, (SELECT id FROM roles WHERE name = 'USUARIO') FROM usuario WHERE username IN ('joaosilva', 'mariasantos');

#Empresas   
INSERT INTO empresas (nome, cnpj, username, password, endereco, telefone, data_criacao, status)
VALUES 
('Sedex', '12345678901234', 'sedex', 'sedex', 'Endereço Sedex', '11111111111', CURRENT_DATE, 'ATIVO'),
('Pac', '23456789012345', 'pac', 'pac', 'Endereço Pac', '22222222222', CURRENT_DATE, 'ATIVO');

INSERT INTO empresa_role (empresa_id, role_id)
SELECT id, (SELECT id FROM roles WHERE name = 'EMPRESA') FROM empresas WHERE nome IN ('Sedex', 'Pac');

#Entregador
INSERT INTO entregadores (nome, username, password, cpf, endereco, telefone, email, data_nascimento, id_tipo_veiculo)
VALUES 
('Carlos Souza', 'carlossouza', 'password', '12345678901', 'Endereço Carlos', '11111111111', 'carlos@email.com', '1980-01-01', 4),
('Ana Pereira', 'anapereira', 'password', '23456789012', 'Endereço Ana', '22222222222', 'ana@email.com', '1985-02-02', 5),
('Roberto Alves', 'robertoalves', 'password', '34567890123', 'Endereço Roberto', '33333333333', 'roberto@email.com', '1990-03-03', 9);

INSERT INTO entregador_role (entregador_id, role_id)
SELECT id, (SELECT id FROM roles WHERE name = 'ENTREGADOR') FROM entregadores WHERE username IN ('carlossouza', 'anapereira', 'robertoalves');

#Pedido usuario
INSERT INTO pedido_usuario (status_pedido, remetente, destinatario_km, id_usuario)
VALUES
(false, 'Lojas Americanas', 10.5, 1),
(false, 'Amazon', 805.0, 2),
(false, 'Magazine Luiza', 200.3, 2);

#Pedido usuario 
INSERT INTO produto_usuario (nome, valor, peso, descricao_produto, id_usuario)
VALUES
	   ('Chocolate', 10.0, 1.5, 'Descrição do Produto 1', 1), 
       ('Bala', 2.0, 0.5, 'Descrição do Produto 2', 1),
       ('Celular', 1000.0, 0.5, 'Descrição do Produto 3', 2),
       ('Notebook', 2000.0, 1.5, 'Descrição do Produto 4', 2),
       ('Geladeira', 1500.30, 10, 'Descrição do Produto 5', 2);
       
       
#Produtos usuario frete
INSERT INTO produtos_usuario_frete (id_produto_usuario, id_pedido, quantidade)
VALUES (1, 1, 5), 
       (2, 1, 7),
       (3, 2, 2),
       (4, 2, 1),
       (5, 3, 1);
       
       
#Frete
INSERT INTO fretes (id_empresa, id_pedido_usuario, status_entrega, status_entregador)
VALUES (1, 1, 'pendente', false), 
       (2, 2, 'pendente', false),
       (2, 3, 'pendente', false);

#Produto empresa (ideal fazer na api, replica os itens solicitados pelo usuario, para controle de itens)
INSERT INTO produto (nome, valor, peso, descricao_produto, id_empresa)
VALUES ('Chocolate', 10.0, 1.5, 'Descrição do Produto 1', 1), 
       ('Bala', 2.0, 0.5, 'Descrição do Produto 2', 1),
       ('Celular', 1000.0, 0.5, 'Descrição do Produto 3', 2),
       ('Notebook', 2000.0, 1.5, 'Descrição do Produto 4', 2),
       ('Geladeira', 1500.30, 10, 'Descrição do Produto 5', 2);
       
#Frete empresa
INSERT INTO
    produtos_frete (
        id_frete,
        id_produto,
        quantidade
    )
VALUES (1, 1, 5), (1, 2, 7), (2, 3, 2), (2, 4, 1), (3, 5, 1);

UPDATE fretes
SET status_entrega = 'em_andamento'
WHERE id_empresa IN (1, 2, 3) AND id_pedido_usuario IN (1, 2, 3);
select * from entregador_frete;
#Entegador frete
INSERT INTO entregador_frete (id_frete, id_entregador)
VALUES (1, 1), 
       (2, 2);


UPDATE fretes
SET status_entregador = 1
WHERE id_empresa IN (1, 2) AND id_pedido_usuario IN (1, 2); 