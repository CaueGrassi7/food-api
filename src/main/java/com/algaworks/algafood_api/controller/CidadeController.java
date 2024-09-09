package com.algaworks.algafood_api.controller;

import com.algaworks.algafood_api.domain.model.Cidade;
import com.algaworks.algafood_api.repository.CidadeRepository;
import org.springframework.util.ReflectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    CidadeRepository cidadeRepository;

    @GetMapping
    public List<Cidade> listar(){
        return cidadeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cidade> buscar(@PathVariable("id") Long id){
        Optional<Cidade> data = cidadeRepository.findById(id);

        if (data.isPresent()){
            return ResponseEntity.ok(data.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cidade> atualizar(@PathVariable("id") Long id, @RequestBody Cidade newCidade) {
        Optional<Cidade> data = cidadeRepository.findById(id);

        if (data.isPresent()) {
            Cidade cidade = data.get();

            // Copia as propriedades de 'newCidade' para 'cidade', ignorando 'id'
            BeanUtils.copyProperties(newCidade, cidade, "id");

            // Salva a entidade atualizada no banco de dados
            cidadeRepository.save(cidade);

            // Retorna o cidade atualizada com status HTTP 200 OK
            return ResponseEntity.ok(cidade);
        }

        // Retorna 404 Not Found se a cidade não for encontrada
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Cidade> adicionar(@Validated @RequestBody Cidade cidade) {
        cidadeRepository.save(cidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cidade> deletar(@PathVariable("id") Long id) {
        Optional<Cidade> data = cidadeRepository.findById(id);

        if (data.isPresent()){
            try {
                cidadeRepository.deleteById(id);
                return ResponseEntity.ok().build();

            } catch (DataIntegrityViolationException e){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
        return ResponseEntity.notFound().build();
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Cidade> atualizarParcial(@PathVariable("id") Long id, @RequestBody Map<String, Object> campos) {
        Optional<Cidade> data = cidadeRepository.findById(id);

        if (data.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cidade cidadeExistente = data.get();

        // Itera sobre os campos fornecidos e atualiza os valores no objeto Cidade existente
        campos.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Cidade.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, cidadeExistente, value);
            }
        });

        cidadeRepository.save(cidadeExistente);
        return ResponseEntity.ok(cidadeExistente);
    }

}
