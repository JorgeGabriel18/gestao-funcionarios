package br.edu.folha.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.edu.folha.exception.CpfInvalidoException;
import java.lang.reflect.Modifier;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Testa a classe abstrata (usando as subclasses) e o comportamento polimórfico. */
class FuncionarioTest {

    private static final String CPF = "529.982.247-25";

    @Test
    void funcionarioEhClasseAbstrata() {
        assertTrue(Modifier.isAbstract(Funcionario.class.getModifiers()));
    }

    @Test
    void guardaDadosComunsENormalizaCpf() {
        Funcionario f = new FuncionarioCLT("001", "Ana Souza", CPF, 3000, 0);
        assertEquals("001", f.getCodigo());
        assertEquals("Ana Souza", f.getNome());
        assertEquals("52998224725", f.getCpf());
        assertEquals("529.982.247-25", f.getCpfFormatado());
        assertEquals(3000.0, f.getSalarioBase(), 0.001);
    }

    @Test
    void cpfInvalidoLancaCpfInvalidoException() {
        assertThrows(CpfInvalidoException.class, () -> new FuncionarioCLT("001", "Ana", "111.111.111-11", 1, 0));
        assertThrows(CpfInvalidoException.class, () -> new FuncionarioCLT("001", "Ana", "123", 1, 0));
    }

    @Test
    void rejeitaCodigoNomeECpfVazios() {
        assertThrows(IllegalArgumentException.class, () -> new FuncionarioCLT("", "Ana", CPF, 1, 0));
        assertThrows(IllegalArgumentException.class, () -> new FuncionarioCLT(null, "Ana", CPF, 1, 0));
        assertThrows(IllegalArgumentException.class, () -> new FuncionarioCLT("001", "  ", CPF, 1, 0));
        assertThrows(IllegalArgumentException.class, () -> new FuncionarioCLT("001", null, CPF, 1, 0));
        assertThrows(IllegalArgumentException.class, () -> new FuncionarioCLT("001", "Ana", "", 1, 0));
        assertThrows(IllegalArgumentException.class, () -> new FuncionarioCLT("001", "Ana", null, 1, 0));
    }

    @Test
    void rejeitaSalarioBaseNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new FuncionarioCLT("001", "Ana", CPF, -1, 0));
    }

    @Test
    void settersValidamEAtualizam() {
        Funcionario f = new FuncionarioCLT("001", "Ana", CPF, 1000, 0);
        f.setNome("Ana Maria");
        f.setSalarioBase(1500);
        f.setCpf("111.444.777-35");
        assertEquals("Ana Maria", f.getNome());
        assertEquals(1500.0, f.getSalarioBase(), 0.001);
        assertEquals("11144477735", f.getCpf());
        assertThrows(IllegalArgumentException.class, () -> f.setSalarioBase(-1));
        assertThrows(CpfInvalidoException.class, () -> f.setCpf("123"));
    }

    @Test
    void exibirDadosMostraInformacoesDoFuncionario() {
        String dados = new FuncionarioCLT("001", "Ana Souza", CPF, 3000, 500).exibirDados();
        assertTrue(dados.contains("001"));
        assertTrue(dados.contains("Ana Souza"));
        assertTrue(dados.contains("529.982.247-25"));
        assertTrue(dados.contains("FuncionarioCLT"));
        assertTrue(dados.contains("Tributos"));
    }

    @Test
    void exibirDadosDePJNaoMostraTributos() {
        String dados = new FuncionarioPJ("002", "Bruno", "111.444.777-35", 1500, 2, 2000).exibirDados();
        assertTrue(dados.contains("Bruno"));
        assertTrue(!dados.contains("Tributos"));
    }

    @Test
    void polimorfismoCadaTipoExecutaSuaPropriaRegra() {
        Funcionario clt = new FuncionarioCLT("001", "Ana", CPF, 3000, 500);
        Funcionario pj = new FuncionarioPJ("002", "Bruno", "111.444.777-35", 1500, 2, 2000);
        Funcionario com = new FuncionarioComissionado("003", "Carla", "123.456.789-09", 2000, 10000, 10);

        assertEquals(3500.0, clt.calcularRemuneracao(), 0.001);
        assertEquals(5500.0, pj.calcularRemuneracao(), 0.001);
        assertEquals(3000.0, com.calcularRemuneracao(), 0.001);
    }

    @Test
    void polimorfismoEmListaDeFuncionarios() {
        List<Funcionario> lista = List.of(
                new FuncionarioCLT("001", "Ana", CPF, 3000, 500),
                new FuncionarioPJ("002", "Bruno", "111.444.777-35", 1500, 2, 2000),
                new FuncionarioComissionado("003", "Carla", "123.456.789-09", 2000, 10000, 10));

        double total = 0;
        for (Funcionario f : lista) {
            total += f.calcularRemuneracao();
        }
        assertEquals(12000.0, total, 0.001);
    }
}
