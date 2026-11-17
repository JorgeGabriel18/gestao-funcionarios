package br.edu.folha.exception;

/** Lançada quando um CPF informado é inválido. */
public class CpfInvalidoException extends IllegalArgumentException {

    private static final long serialVersionUID = 1L;

    public CpfInvalidoException(String mensagem) {
        super(mensagem);
    }
}
