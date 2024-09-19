package com.algaworks.algafood_api.controller;


import com.algaworks.algafood_api.model.Restaurante;
import com.algaworks.algafood_api.repository.RestauranteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> buscar(@PathVariable("id") long id) {
        Optional<Restaurante> data = restauranteRepository.findById(id);

        if (data.isPresent()){
            return ResponseEntity.ok(data.get());
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<Restaurante> adicionar( @Validated @RequestBody Restaurante restaurante) {
        Restaurante newRestaurante = restauranteRepository.save(restaurante);
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurante> atualizar(@PathVariable("id") Long id, @RequestBody Restaurante newRestaurante) {
        Optional<Restaurante> data = restauranteRepository.findById(id);

        if (data.isPresent()) {
            Restaurante restaurante = data.get();

            // Copia as propriedades de 'newRestaurante' para 'restaurante', ignorando 'id', 'formasPagamento', 'endereço'
            BeanUtils.copyProperties(newRestaurante, restaurante,
                    "id", "formasPagamento", "endereco", "dataCadastro", "produtos");

            // Salva a entidade atualizada no banco de dados
            restauranteRepository.save(restaurante);

            // Retorna o restaurante atualizado com status HTTP 200 OK
            return ResponseEntity.status(HttpStatus.OK).body(restaurante);
        }

        // Retorna 404 Not Found se o restaurante não for encontrado
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Restaurante> atualizarParcial(@PathVariable("id") Long id, @RequestBody Map<String, Object> campos) {
        Optional<Restaurante> data = restauranteRepository.findById(id);

        if (data.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Restaurante restauranteExistente = data.get();

        campos.forEach((key, value) -> {
            if ("id".equals(key)) {
                // Ignora o campo 'id' para evitar atualizações indesejadas no identificador
                return;
            }

            Field field = ReflectionUtils.findField(Restaurante.class, key);
            if (field != null) {
                field.setAccessible(true);
                try {
                    // Definir o valor diretamente, sem conversões ou verificações
                    ReflectionUtils.setField(field, restauranteExistente, value);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace(); // Isso pode ser substituído por um tratamento de erro mais adequado
                }
            }
        });

        restauranteRepository.save(restauranteExistente);
        return ResponseEntity.ok(restauranteExistente);
    }


}
