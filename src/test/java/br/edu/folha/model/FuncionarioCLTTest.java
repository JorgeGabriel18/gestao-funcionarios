package br.edu.folha.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FuncionarioCLTTest {

    private static final String CPF = "529.982.247-25";

    @Test
    void remuneracaoEhSalarioBaseMaisBeneficio() {
        FuncionarioCLT f = new FuncionarioCLT("001", "Ana", CPF, 3000, 500);
        assertEquals(3500.0, f.calcularRemuneracao(), 0.001);
    }

    @Test
    void tributosSaoDezPorCentoDaRemuneracao() {
        FuncionarioCLT f = new FuncionarioCLT("001", "Ana", CPF, 3000, 500);
        assertEquals(350.0, f.calcularTributos(), 0.001);
    }

    @Test
    void identificadorEhOCodigo() {
        assertEquals("001", new FuncionarioCLT("001", "Ana", CPF, 3000, 500).getIdentificador());
    }

    @Test
    void setterAtualizaBeneficio() {
        FuncionarioCLT f = new FuncionarioCLT("001", "Ana", CPF, 3000, 500);
        f.setBeneficio(800);
        assertEquals(800.0, f.getBeneficio(), 0.001);
        assertEquals(3800.0, f.calcularRemuneracao(), 0.001);
    }

    @Test
    void rejeitaBeneficioNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new FuncionarioCLT("001", "Ana", CPF, 3000, -1));
    }
}
