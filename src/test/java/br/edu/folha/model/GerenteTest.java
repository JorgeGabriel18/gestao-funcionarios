package br.edu.folha.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class GerenteTest {

    private static final String CPF = "111.444.777-35";

    @Test
    void remuneracaoSomaParticipacaoNosLucros() {
        Gerente g = new Gerente("005", "Eva", CPF, 5000, 2000);
        assertEquals(7000.0, g.calcularRemuneracao(), 0.001);
    }

    @Test
    void tributosSaoVintePorCento() {
        Gerente g = new Gerente("005", "Eva", CPF, 5000, 2000);
        assertEquals(1400.0, g.calcularTributos(), 0.001);
    }

    @Test
    void identificadorEhOCodigo() {
        assertEquals("005", new Gerente("005", "Eva", CPF, 5000, 0).getIdentificador());
    }

    @Test
    void rejeitaParticipacaoNegativa() {
        assertThrows(IllegalArgumentException.class, () -> new Gerente("005", "Eva", CPF, 5000, -1));
    }
}
