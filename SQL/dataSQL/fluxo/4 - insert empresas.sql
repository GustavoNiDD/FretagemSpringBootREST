-- Active: 1693676830144@@127.0.0.1@3306@fretes_infinity
-- -- Inserir empresas
-- INSERT INTO empresas (nome, cnpj, username, password, endereco, telefone, data_criacao, status)
-- VALUES ('Empresa 1', '45678901234567', 'empresa1', 'senha1', 'Endereço 1', '4567890123', '2022-04-04', 'ATIVO'),
-- ('Empresa 2', '56789012345678', 'empresa2', 'senha2', 'Endereço 2', '5678901234', '2022-05-05', 'ATIVO'),
-- ('Empresa 3', '67890123456789', 'empresa3', 'senha3', 'Endereço 3', '6789012345', '2022-06-06', 'ATIVO');

-- -- Inserir roles
-- INSERT INTO empresa_role (empresa_id, role_id)
-- VALUES ((SELECT id FROM empresas WHERE nome = 'Empresa 1'), (SELECT id FROM roles WHERE name = 'EMPRESA')),
-- ((SELECT id FROM empresas WHERE nome = 'Empresa 2'), (SELECT id FROM roles WHERE name = 'EMPRESA')),
-- ((SELECT id FROM empresas WHERE nome = 'Empresa 3'), (SELECT id FROM roles WHERE name = 'EMPRESA'));


INSERT INTO empresas (nome, cnpj, username, password, endereco, telefone, data_criacao, status)
VALUES ('Sedex', '12345678901234', 'sedex', 'sedex', 'Endereço Sedex', '11111111111', CURRENT_DATE, 'ATIVO'),
VALUES ('Pac', '23456789012345', 'pac', 'pac', 'Endereço Pac', '22222222222', CURRENT_DATE, 'ATIVO');

INSERT INTO empresa_role (empresa_id, role_id)
SELECT id, (SELECT id FROM roles WHERE name = 'EMPRESA') FROM empresas WHERE nome IN ('Sedex', 'Pac');