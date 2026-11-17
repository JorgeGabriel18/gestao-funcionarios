package br.edu.folha.exception;

public class FuncionarioJaCadastradoException extends RuntimeException {
    public FuncionarioJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}