package com.algaworks.algafood_api.repository;

import com.algaworks.algafood_api.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
}
