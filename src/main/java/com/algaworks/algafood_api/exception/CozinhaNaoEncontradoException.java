package com.algaworks.algafood_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CozinhaNaoEncontradoException extends EntidadeNaoEncontradaException{

    @Serial
    private static final long serialVersionUID = 1L;

    public CozinhaNaoEncontradoException(String mensagem) {
       super(mensagem);
    }

    public CozinhaNaoEncontradoException(Long cozinhaId) {
        this(String.format("Não existe um cadastro de cozinha com código %d", cozinhaId));
    }

}