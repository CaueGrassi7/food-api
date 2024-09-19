package com.algaworks.algafood_api.controller;

import com.algaworks.algafood_api.model.Cozinha;

import com.algaworks.algafood_api.repository.CozinhaRepository;
import com.algaworks.algafood_api.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cozinhas", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @GetMapping()
    public List<Cozinha> listar() {
        return cozinhaRepository.findAll();
    }

    // @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ResponseEntity<Cozinha> buscar(@PathVariable("id") Long id) {
        Optional<Cozinha> data = cozinhaRepository.findById(id);

        if (data.isPresent()) {
            return ResponseEntity.ok(data.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Cozinha> adicionar(@Validated @RequestBody Cozinha cozinha){
        if (cozinha.getNome() == null || cozinha.getNome().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        Cozinha savedCozinha = cozinhaRepository.save(cozinha);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCozinha);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable("id") Long id, @RequestBody  Cozinha cozinha){
        Optional<Cozinha> data = cozinhaRepository.findById(id);

        if (data.isPresent()) {

            Cozinha cozinhaExistente = data.get();
            BeanUtils.copyProperties(cozinha, cozinhaExistente, "id");
            cozinhaRepository.save(cozinhaExistente);

            return ResponseEntity.status(HttpStatus.OK).body(cozinhaExistente);
        }

        return ResponseEntity.notFound().build();

    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
//        Optional<Cozinha> data = cozinhaRepository.findById(id);
//
//        if (data.isPresent()) {
//            try {
//                cozinhaRepository.deleteById(id);
//                return ResponseEntity.ok().build();
//            } catch (DataIntegrityViolationException e) {
//                // Retorna 409 Conflict se a exclusão violar uma restrição de integridade
//                return ResponseEntity.status(HttpStatus.CONFLICT).build();
//            }
//        }
//
//        // Retorna 404 Not Found se a entidade não for encontrada
//        return ResponseEntity.notFound().build();
//    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
       cadastroCozinhaService.excluir(cozinhaId);
    }


    @GetMapping("/pesquisar/{nome}")
    public Optional<Cozinha> cozinhasPorNome(@PathVariable("nome") String nome) {
        return cozinhaRepository.findByNome(nome);
    }

}