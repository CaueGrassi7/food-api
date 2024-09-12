USE algafood;
CREATE TABLE cidade (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nome_cidade VARCHAR(60) NOT NULL,
  nome_estado VARCHAR(60) NOT NULL,
  PRIMARY KEY (id)
);