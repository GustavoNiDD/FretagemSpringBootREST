-- -- Active: 1693676830144@@127.0.0.1@3306@fretes_infinity
-- INSERT INTO usuario (nome, username, password, distancia)
-- VALUES ('Usuario 1', 'username1', 'password1', 100),
--        ('Usuario 2', 'username2', 'password2', 200);

-- INSERT INTO usuario_role (usuario_id, role_id)
-- VALUES (1, 3),
--        (2, 3);


INSERT INTO usuario (nome, username, password, distancia)
VALUES ('João Silva', 'joaosilva', 'joao', 10);

INSERT INTO usuario (nome, username, password, distancia)
VALUES ('Maria Santos', 'mariasantos', 'maria', 20);

INSERT INTO usuario_role (usuario_id, role_id)
SELECT id, (SELECT id FROM roles WHERE name = 'USUARIO') FROM usuario WHERE username IN ('joaosilva', 'mariasantos');