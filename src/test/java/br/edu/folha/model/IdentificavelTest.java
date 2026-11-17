package br.edu.folha.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class IdentificavelTest {

    @Test
    void todasAsClassesConcretasImplementamIdentificavel() {
        Identificavel[] todos = {
            new FuncionarioCLT("001", "Ana", "529.982.247-25", 1000, 0),
            new FuncionarioPJ("002", "Bruno", "111.444.777-35", 1000, 1, 100),
            new FuncionarioComissionado("003", "Carla", "123.456.789-09", 1000, 0, 5),
            new Desenvolvedor("004", "Davi", "529.982.247-25", 1000, 0, 0),
            new Gerente("005", "Eva", "111.444.777-35", 1000, 0)
        };
        String[] esperados = {"001", "002", "003", "004", "005"};
        for (int i = 0; i < todos.length; i++) {
            assertEquals(esperados[i], todos[i].getIdentificador());
        }
    }
}
