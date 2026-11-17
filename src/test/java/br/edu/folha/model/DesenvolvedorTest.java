package br.edu.folha.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class DesenvolvedorTest {

    private static final String CPF = "529.982.247-25";

    @Test
    void remuneracaoSomaHorasExtras() {
        Desenvolvedor d = new Desenvolvedor("004", "Davi", CPF, 3000, 10, 50);
        assertEquals(3500.0, d.calcularRemuneracao(), 0.001);
    }

    @Test
    void tributosSaoDezPorCento() {
        Desenvolvedor d = new Desenvolvedor("004", "Davi", CPF, 3000, 10, 50);
        assertEquals(350.0, d.calcularTributos(), 0.001);
    }

    @Test
    void identificadorEhOCodigo() {
        assertEquals("004", new Desenvolvedor("004", "Davi", CPF, 3000, 0, 0).getIdentificador());
    }

    @Test
    void rejeitaHorasExtrasOuValorNegativos() {
        assertThrows(IllegalArgumentException.class, () -> new Desenvolvedor("004", "Davi", CPF, 3000, -1, 50));
        assertThrows(IllegalArgumentException.class, () -> new Desenvolvedor("004", "Davi", CPF, 3000, 1, -50));
    }
}
