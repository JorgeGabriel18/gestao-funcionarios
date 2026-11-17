package br.edu.folha.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ExcecoesPersonalizadasTest {

    @Test
    void funcionarioNaoEncontradoGuardaMensagem() {
        FuncionarioNaoEncontradoException e = new FuncionarioNaoEncontradoException("não achou");
        assertEquals("não achou", e.getMessage());
        assertInstanceOf(RuntimeException.class, e);
    }

    @Test
    void funcionarioJaCadastradoGuardaMensagem() {
        FuncionarioJaCadastradoException e = new FuncionarioJaCadastradoException("duplicado");
        assertEquals("duplicado", e.getMessage());
        assertInstanceOf(RuntimeException.class, e);
    }

    @Test
    void cpfInvalidoGuardaMensagemEEhIllegalArgumentException() {
        CpfInvalidoException e = new CpfInvalidoException("cpf ruim");
        assertEquals("cpf ruim", e.getMessage());
        assertInstanceOf(IllegalArgumentException.class, e);
    }

    @Test
    void excecoesPodemSerLancadas() {
        assertThrows(FuncionarioNaoEncontradoException.class, () -> {
            throw new FuncionarioNaoEncontradoException("x");
        });
        assertThrows(FuncionarioJaCadastradoException.class, () -> {
            throw new FuncionarioJaCadastradoException("x");
        });
        assertThrows(CpfInvalidoException.class, () -> {
            throw new CpfInvalidoException("x");
        });
    }
}
