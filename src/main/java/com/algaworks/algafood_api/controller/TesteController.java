package com.algaworks.algafood_api.controller;

import com.algaworks.algafood_api.model.Cozinha;
import com.algaworks.algafood_api.model.Restaurante;
import com.algaworks.algafood_api.repository.CozinhaRepository;
import com.algaworks.algafood_api.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/testes")
public class TesteController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;


//    @GetMapping("/restaurantes/por-nome")
//    public List<Restaurante> restaurantesPorTaxaFrete(
//            String nome, Long cozinhaId) {
//        return restauranteRepository.consultarPorNome(nome, cozinhaId);
//    }



    @GetMapping("/restaurantes/por-nome-e-frete")
    public List<Restaurante> restaurantesPorNomeFrete(String nome,
                                                      BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
    }

    @GetMapping("/restaurantes/com-frete-gratis")
    public List<Restaurante> restaurantesComFreteGratis(String nome) {

        return restauranteRepository.findComFreteGratis(nome);
    }

    @GetMapping("/restaurantes/primeiro")
    public Optional<Restaurante> restaurantePrimeiro() {
        return restauranteRepository.buscarPrimeiro();
    }

    @GetMapping("/cozinhas/primeiro")
    public Optional<Cozinha> cozinhaPrimeiro() {
        return cozinhaRepository.buscarPrimeiro();
    }

}
