package br.edu.folha.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TributavelTest {

    @Test
    void tributaveisImplementamAInterface() {
        assertTrue(new FuncionarioCLT("001", "Ana", "529.982.247-25", 1000, 0) instanceof Tributavel);
        assertTrue(new FuncionarioComissionado("003", "Carla", "123.456.789-09", 1000, 0, 5) instanceof Tributavel);
        assertTrue(new Desenvolvedor("004", "Davi", "529.982.247-25", 1000, 0, 0) instanceof Tributavel);
        assertTrue(new Gerente("005", "Eva", "111.444.777-35", 1000, 0) instanceof Tributavel);
    }

    @Test
    void pjNaoEhTributavel() {
        assertFalse(new FuncionarioPJ("002", "Bruno", "111.444.777-35", 1000, 1, 100) instanceof Tributavel);
    }

    @Test
    void cadaImplementacaoCalculaSeusTributos() {
        Tributavel clt = new FuncionarioCLT("001", "Ana", "529.982.247-25", 1000, 0);
        Tributavel ger = new Gerente("005", "Eva", "111.444.777-35", 1000, 0);
        assertEquals(100.0, clt.calcularTributos(), 0.001);
        assertEquals(200.0, ger.calcularTributos(), 0.001);
    }
}
