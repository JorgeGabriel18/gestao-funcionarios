package br.edu.folha.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FuncionarioComissionadoTest {

    private static final String CPF = "123.456.789-09";

    @Test
    void remuneracaoEhSalarioMaisComissaoSobreVendas() {
        FuncionarioComissionado f = new FuncionarioComissionado("003", "Carla", CPF, 2000, 10000, 10);
        assertEquals(3000.0, f.calcularRemuneracao(), 0.001);
    }

    @Test
    void tributosSaoQuinzePorCentoDaRemuneracao() {
        FuncionarioComissionado f = new FuncionarioComissionado("003", "Carla", CPF, 2000, 10000, 10);
        assertEquals(450.0, f.calcularTributos(), 0.001);
    }

    @Test
    void identificadorEhOCodigo() {
        assertEquals("003", new FuncionarioComissionado("003", "Carla", CPF, 2000, 0, 0).getIdentificador());
    }

    @Test
    void aceitaPercentualNosLimites() {
        new FuncionarioComissionado("003", "Carla", CPF, 2000, 0, 0);
        new FuncionarioComissionado("004", "Carla", CPF, 2000, 0, 100);
    }

    @Test
    void rejeitaPercentualNegativoOuAcimaDeCem() {
        assertThrows(IllegalArgumentException.class, () -> new FuncionarioComissionado("003", "Carla", CPF, 2000, 0, -1));
        assertThrows(IllegalArgumentException.class, () -> new FuncionarioComissionado("003", "Carla", CPF, 2000, 0, 100.5));
    }

    @Test
    void rejeitaVendasNegativas() {
        assertThrows(IllegalArgumentException.class, () -> new FuncionarioComissionado("003", "Carla", CPF, 2000, -1, 10));
    }
}
