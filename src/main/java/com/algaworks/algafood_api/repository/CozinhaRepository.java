package com.algaworks.algafood_api.repository;

import com.algaworks.algafood_api.model.Cozinha;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {

    Optional<Cozinha> findByNome(String Nome);
}
