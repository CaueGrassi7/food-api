USE algafood;

-- Cria a tabela 'estado'
CREATE TABLE IF NOT EXISTS estado (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(60) NOT NULL,
  PRIMARY KEY (id)
);

-- Insere os estados distintos da tabela 'cidade'
INSERT INTO estado(nome)
SELECT DISTINCT nome_estado FROM cidade;

-- Adiciona uma nova coluna 'estado_id' na tabela 'cidade'
ALTER TABLE cidade ADD COLUMN estado_id BIGINT;

-- Atualiza a tabela 'cidade' para associar o 'estado_id' correto
UPDATE cidade c
SET c.estado_id = (SELECT e.id FROM estado e WHERE e.nome = c.nome_estado);

-- Cria a chave estrangeira para associar 'cidade' com 'estado'
ALTER TABLE cidade ADD CONSTRAINT fk_cidade_estado
FOREIGN KEY (estado_id) REFERENCES estado (id);

-- Remove a coluna antiga 'nome_estado' da tabela 'cidade'
ALTER TABLE cidade DROP COLUMN nome_estado;

-- Renomeia a coluna 'nome_cidade' para 'nome'
ALTER TABLE cidade CHANGE nome_cidade nome VARCHAR(80) NOT NULL;