-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema fretes_infinity
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema fretes_infinity
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `fretes_infinity` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `fretes_infinity` ;

-- -----------------------------------------------------
-- Table `fretes_infinity`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fretes_infinity`.`roles` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_ofx66keruapi6vyqpv6f2or37` (`name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fretes_infinity`.`empresas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fretes_infinity`.`empresas` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `cnpj` VARCHAR(255) NULL DEFAULT NULL,
  `data_criacao` DATE NULL DEFAULT NULL,
  `endereco` VARCHAR(255) NULL DEFAULT NULL,
  `nome` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `status` ENUM('ativo', 'inativo') NULL DEFAULT NULL,
  `telefone` VARCHAR(255) NULL DEFAULT NULL,
  `username` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_dlxi0rufl6e9lwbo6fkcd5kp5` (`cnpj` ASC) VISIBLE,
  UNIQUE INDEX `UK_4i591figd8nsoii5e9ersm0cl` (`nome` ASC) VISIBLE,
  UNIQUE INDEX `UK_qp3kjix0cd0qrwhf5k62iikyp` (`username` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fretes_infinity`.`empresa_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fretes_infinity`.`empresa_role` (
  `empresa_id` BIGINT NOT NULL,
  `role_id` BIGINT NOT NULL,
  PRIMARY KEY (`empresa_id`, `role_id`),
  INDEX `FK3c32cg5vot0serggkqjnnu48g` (`role_id` ASC) VISIBLE,
  CONSTRAINT `FK3c32cg5vot0serggkqjnnu48g`
    FOREIGN KEY (`role_id`)
    REFERENCES `fretes_infinity`.`roles` (`id`),
  CONSTRAINT `FK7bwx4s8aav8sey8t4u5u658xt`
    FOREIGN KEY (`empresa_id`)
    REFERENCES `fretes_infinity`.`empresas` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fretes_infinity`.`veiculos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fretes_infinity`.`veiculos` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `peso` INT NULL DEFAULT NULL,
  `tipo_veiculo` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_or816cb5luyqrpmdeego083xo` (`tipo_veiculo` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fretes_infinity`.`entregadores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fretes_infinity`.`entregadores` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `cpf` VARCHAR(255) NULL DEFAULT NULL,
  `data_nascimento` DATE NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `endereco` VARCHAR(255) NULL DEFAULT NULL,
  `nome` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `telefone` VARCHAR(255) NULL DEFAULT NULL,
  `username` VARCHAR(255) NULL DEFAULT NULL,
  `id_tipo_veiculo` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_ghao026el12cg8yen7i7lr5st` (`cpf` ASC) VISIBLE,
  UNIQUE INDEX `UK_8uwmb1ated7g1f47kyxqa88x4` (`email` ASC) VISIBLE,
  UNIQUE INDEX `UK_tbfxdll31flw6tlipfcuthjxc` (`username` ASC) VISIBLE,
  INDEX `FKpo7h01j8f1b3t9kgi5n158gs4` (`id_tipo_veiculo` ASC) VISIBLE,
  CONSTRAINT `FKpo7h01j8f1b3t9kgi5n158gs4`
    FOREIGN KEY (`id_tipo_veiculo`)
    REFERENCES `fretes_infinity`.`veiculos` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fretes_infinity`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fretes_infinity`.`usuario` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `distancia` INT NULL DEFAULT NULL,
  `nome` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `username` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_863n1y3x0jalatoir4325ehal` (`username` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fretes_infinity`.`pedido_usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fretes_infinity`.`pedido_usuario` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `destinatario_km` DOUBLE NOT NULL,
  `remetente` VARCHAR(255) NOT NULL,
  `status_pedido` BIT(1) NOT NULL,
  `id_usuario` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK9y5m7rl9rveoac7hjgiviht5q` (`id_usuario` ASC) VISIBLE,
  CONSTRAINT `FK9y5m7rl9rveoac7hjgiviht5q`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `fretes_infinity`.`usuario` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fretes_infinity`.`fretes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fretes_infinity`.`fretes` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `status_entrega` ENUM('pendente', 'em_andamento', 'concluido') NOT NULL,
  `status_entregador` BIT(1) NOT NULL,
  `id_empresa` BIGINT NOT NULL,
  `id_pedido_usuario` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKfihh2tbq4wkwa2dta7f0p5m33` (`id_empresa` ASC) VISIBLE,
  INDEX `FKfh72gmaa005aotrvj0ouvtcc2` (`id_pedido_usuario` ASC) VISIBLE,
  CONSTRAINT `FKfh72gmaa005aotrvj0ouvtcc2`
    FOREIGN KEY (`id_pedido_usuario`)
    REFERENCES `fretes_infinity`.`pedido_usuario` (`id`),
  CONSTRAINT `FKfihh2tbq4wkwa2dta7f0p5m33`
    FOREIGN KEY (`id_empresa`)
    REFERENCES `fretes_infinity`.`empresas` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fretes_infinity`.`entregador_frete`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fretes_infinity`.`entregador_frete` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `id_entregador` BIGINT NOT NULL,
  `id_frete` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK2x0oew7gmnqklo7blaoi7ujgh` (`id_entregador` ASC) VISIBLE,
  INDEX `FKj7mo4pjwqbmwoeq91k4vei3a8` (`id_frete` ASC) VISIBLE,
  CONSTRAINT `FK2x0oew7gmnqklo7blaoi7ujgh`
    FOREIGN KEY (`id_entregador`)
    REFERENCES `fretes_infinity`.`entregadores` (`id`),
  CONSTRAINT `FKj7mo4pjwqbmwoeq91k4vei3a8`
    FOREIGN KEY (`id_frete`)
    REFERENCES `fretes_infinity`.`fretes` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fretes_infinity`.`entregador_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fretes_infinity`.`entregador_role` (
  `entregador_id` BIGINT NOT NULL,
  `role_id` BIGINT NOT NULL,
  PRIMARY KEY (`entregador_id`, `role_id`),
  INDEX `FKl08v6hl8u1cwdti3osediqqfs` (`role_id` ASC) VISIBLE,
  CONSTRAINT `FK7y7usf1fdp5u0u5lcir5px5g8`
    FOREIGN KEY (`entregador_id`)
    REFERENCES `fretes_infinity`.`entregadores` (`id`),
  CONSTRAINT `FKl08v6hl8u1cwdti3osediqqfs`
    FOREIGN KEY (`role_id`)
    REFERENCES `fretes_infinity`.`roles` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fretes_infinity`.`produto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fretes_infinity`.`produto` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `descricao_produto` VARCHAR(255) NULL DEFAULT NULL,
  `nome` VARCHAR(255) NULL DEFAULT NULL,
  `peso` DOUBLE NULL DEFAULT NULL,
  `valor` DOUBLE NULL DEFAULT NULL,
  `id_empresa` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK6xq2vbefn0ehnpm80aj6xf8xg` (`id_empresa` ASC) VISIBLE,
  CONSTRAINT `FK6xq2vbefn0ehnpm80aj6xf8xg`
    FOREIGN KEY (`id_empresa`)
    REFERENCES `fretes_infinity`.`empresas` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fretes_infinity`.`produto_usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fretes_infinity`.`produto_usuario` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `descricao_produto` VARCHAR(255) NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `peso` DOUBLE NOT NULL,
  `valor` DOUBLE NOT NULL,
  `id_usuario` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKnqaxiwh8tchfg6dn9vx25lxfq` (`id_usuario` ASC) VISIBLE,
  CONSTRAINT `FKnqaxiwh8tchfg6dn9vx25lxfq`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `fretes_infinity`.`usuario` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fretes_infinity`.`produtos_frete`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fretes_infinity`.`produtos_frete` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `quantidade` INT NOT NULL,
  `id_frete` BIGINT NOT NULL,
  `id_produto` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK89wql7p9e8j2u97ta16hd6qw9` (`id_frete` ASC) VISIBLE,
  INDEX `FKaww82jnbs3kd599o73n3t0iv2` (`id_produto` ASC) VISIBLE,
  CONSTRAINT `FK89wql7p9e8j2u97ta16hd6qw9`
    FOREIGN KEY (`id_frete`)
    REFERENCES `fretes_infinity`.`fretes` (`id`),
  CONSTRAINT `FKaww82jnbs3kd599o73n3t0iv2`
    FOREIGN KEY (`id_produto`)
    REFERENCES `fretes_infinity`.`produto` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fretes_infinity`.`produtos_usuario_frete`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fretes_infinity`.`produtos_usuario_frete` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `quantidade` INT NOT NULL,
  `id_pedido` BIGINT NOT NULL,
  `id_produto_usuario` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKfgibj2nl787vnfas3nhm9s7be` (`id_pedido` ASC) VISIBLE,
  INDEX `FK8nvob7ymodkydxttirsy68pcm` (`id_produto_usuario` ASC) VISIBLE,
  CONSTRAINT `FK8nvob7ymodkydxttirsy68pcm`
    FOREIGN KEY (`id_produto_usuario`)
    REFERENCES `fretes_infinity`.`produto_usuario` (`id`),
  CONSTRAINT `FKfgibj2nl787vnfas3nhm9s7be`
    FOREIGN KEY (`id_pedido`)
    REFERENCES `fretes_infinity`.`pedido_usuario` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fretes_infinity`.`usuario_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fretes_infinity`.`usuario_role` (
  `usuario_id` BIGINT NOT NULL,
  `role_id` BIGINT NOT NULL,
  PRIMARY KEY (`usuario_id`, `role_id`),
  INDEX `FKhajnij2gu5sift45hmthgvxwl` (`role_id` ASC) VISIBLE,
  CONSTRAINT `FKhajnij2gu5sift45hmthgvxwl`
    FOREIGN KEY (`role_id`)
    REFERENCES `fretes_infinity`.`roles` (`id`),
  CONSTRAINT `FKpc2qjts6sqq4hja9f6i3hf0ep`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `fretes_infinity`.`usuario` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
