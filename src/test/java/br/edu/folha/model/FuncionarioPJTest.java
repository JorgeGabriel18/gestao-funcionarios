package br.edu.folha.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FuncionarioPJTest {

    private static final String CPF = "111.444.777-35";

    @Test
    void remuneracaoEhSalarioBaseMaisProjetos() {
        FuncionarioPJ f = new FuncionarioPJ("002", "Bruno", CPF, 1500, 2, 2000);
        assertEquals(5500.0, f.calcularRemuneracao(), 0.001);
    }

    @Test
    void semProjetosRecebeSomenteSalarioBase() {
        assertEquals(1500.0, new FuncionarioPJ("002", "Bruno", CPF, 1500, 0, 2000).calcularRemuneracao(), 0.001);
    }

    @Test
    void naoEhTributavel() {
        assertFalse(new FuncionarioPJ("002", "Bruno", CPF, 1500, 2, 2000) instanceof Tributavel);
    }

    @Test
    void identificadorEhOCodigo() {
        assertEquals("002", new FuncionarioPJ("002", "Bruno", CPF, 1500, 2, 2000).getIdentificador());
    }

    @Test
    void rejeitaQuantidadeDeProjetosNegativa() {
        assertThrows(IllegalArgumentException.class, () -> new FuncionarioPJ("002", "Bruno", CPF, 1500, -1, 2000));
    }

    @Test
    void rejeitaValorPorProjetoNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new FuncionarioPJ("002", "Bruno", CPF, 1500, 1, -1));
    }

    @Test
    void settersAtualizamValores() {
        FuncionarioPJ f = new FuncionarioPJ("002", "Bruno", CPF, 1000, 1, 1000);
        f.setQuantidadeProjetos(3);
        f.setValorPorProjeto(500);
        assertEquals(2500.0, f.calcularRemuneracao(), 0.001);
    }
}
