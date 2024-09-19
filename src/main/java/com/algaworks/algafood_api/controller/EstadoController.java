package com.algaworks.algafood_api.controller;

import com.algaworks.algafood_api.model.Estado;

import com.algaworks.algafood_api.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    EstadoRepository estadoRepository;

    @GetMapping
    public List<Estado> listar(){
        return estadoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estado> buscar(@PathVariable("id") long id){
        Optional<Estado> data = estadoRepository.findById(id);

        if (data.isPresent()){
            return ResponseEntity.ok(data.get());
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<Estado> adicionar(@RequestBody Estado estado){
        estadoRepository.save(estado);

        return ResponseEntity.status(HttpStatus.CREATED).body(estado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estado> atualizar(@PathVariable("id") Long id, @RequestBody Estado newEstado) {
        Optional<Estado> data = estadoRepository.findById(id);

        if (data.isPresent()){
            Estado oldEstado = data.get();
            BeanUtils.copyProperties(newEstado, oldEstado, "id");
            estadoRepository.save(oldEstado);

            return ResponseEntity.status(HttpStatus.OK).body(oldEstado);
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Estado> deletar(@PathVariable("id") long id){
        Optional<Estado> data = estadoRepository.findById(id);

        if (data.isPresent()){
            try{
                estadoRepository.deleteById(id);
                return ResponseEntity.ok().build();

            }catch (DataIntegrityViolationException e) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
        return ResponseEntity.notFound().build();
    }
}
