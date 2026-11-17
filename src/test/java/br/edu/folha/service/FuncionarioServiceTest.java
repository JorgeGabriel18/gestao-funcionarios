package br.edu.folha.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.edu.folha.exception.FuncionarioJaCadastradoException;
import br.edu.folha.exception.FuncionarioNaoEncontradoException;
import br.edu.folha.model.Funcionario;
import br.edu.folha.model.FuncionarioCLT;
import br.edu.folha.model.FuncionarioComissionado;
import br.edu.folha.model.FuncionarioPJ;
import br.edu.folha.repository.FuncionarioRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FuncionarioServiceTest {

    private FuncionarioRepository repository;
    private FuncionarioService service;

    @BeforeEach
    public void setUp() {
        repository = new FuncionarioRepository();
        service = new FuncionarioService(repository);
        service.cadastrar(new FuncionarioCLT("001", "Ana Souza", "529.982.247-25", 3000, 500));
        service.cadastrar(new FuncionarioPJ("002", "Bruno Lima", "111.444.777-35", 1500, 2, 2000));
        service.cadastrar(new FuncionarioComissionado("003", "Carla Dias", "123.456.789-09", 2000, 10000, 10));
    }

    @Test
    public void construtorRejeitaRepositorioNulo() {
        assertThrows(IllegalArgumentException.class, () -> new FuncionarioService(null));
    }

    @Test
    public void cadastraEListaFuncionarios() {
        assertEquals(3, service.listar().size());
    }

    @Test
    public void cadastroComCodigoDuplicadoLancaExcecao() {
        Funcionario duplicado = new FuncionarioPJ("001", "Outro", "987.654.321-00", 100, 1, 100);
        assertThrows(FuncionarioJaCadastradoException.class, () -> service.cadastrar(duplicado));
    }

    // ---------- sobrecarga de buscarFuncionario ----------

    @Test
    public void buscaPorCodigo() {
        assertEquals("Ana Souza", service.buscarFuncionario("001").getNome());
    }

    @Test
    public void buscaPorCodigoInexistenteLancaExcecao() {
        assertThrows(FuncionarioNaoEncontradoException.class, () -> service.buscarFuncionario("999"));
    }

    @Test
    public void buscaPorNomeExataEParcial() {
        List<Funcionario> exata = service.buscarFuncionario("Carla Dias", true);
        List<Funcionario> parcial = service.buscarFuncionario("a", false);
        assertEquals(1, exata.size());
        assertEquals(3, parcial.size());
    }

    @Test
    public void buscaPorNomeSemResultadoLancaExcecao() {
        assertThrows(FuncionarioNaoEncontradoException.class, () -> service.buscarFuncionario("Zé", true));
    }

    @Test
    public void buscaPorNomeVazioLancaExcecao() {
        assertThrows(IllegalArgumentException.class, () -> service.buscarFuncionario("  ", false));
    }

    @Test
    public void buscaPorCpf() {
        assertEquals("Bruno Lima", service.buscarPorCpf("111.444.777-35").getNome());
        assertEquals("Bruno Lima", service.buscarPorCpf("11144477735").getNome());
    }

    @Test
    public void buscaPorCpfInexistenteLancaExcecao() {
        assertThrows(FuncionarioNaoEncontradoException.class, () -> service.buscarPorCpf("987.654.321-00"));
    }

    // ---------- remoção ----------

    @Test
    public void removeFuncionarioExistente() {
        service.remover("002");
        assertEquals(2, service.listar().size());
        assertThrows(FuncionarioNaoEncontradoException.class, () -> service.buscarFuncionario("002"));
    }

    @Test
    public void removerInexistenteLancaExcecao() {
        assertThrows(FuncionarioNaoEncontradoException.class, () -> service.remover("999"));
    }

    // ---------- cálculos ----------

    @Test
    public void calculaRemuneracaoPorCodigo() {
        assertEquals(3500.0, service.calcularRemuneracao("001"), 0.001);
        assertEquals(5500.0, service.calcularRemuneracao("002"), 0.001);
        assertEquals(3000.0, service.calcularRemuneracao("003"), 0.001);
    }

    @Test
    public void calculaRemuneracaoDeInexistenteLancaExcecao() {
        assertThrows(FuncionarioNaoEncontradoException.class, () -> service.calcularRemuneracao("999"));
    }

    @Test
    public void deveCalcularTotalRemuneracoesCorretamente() {
        assertEquals(12000.0, service.calcularTotalRemuneracoes(), 0.001);
    }

    @Test
    public void totalDaFolhaVaziaEhZero() {
        FuncionarioService vazio = new FuncionarioService(new FuncionarioRepository());
        assertEquals(0.0, vazio.calcularTotalRemuneracoes(), 0.001);
    }

    @Test
    public void deveCalcularTotalTributosCorretamente() {
        // CLT 350 + Comissionado 450; PJ não é tributável
        assertEquals(800.0, service.calcularTotalTributos(), 0.001);
    }

    // ---------- exibir dados e relatório ----------

    @Test
    public void exibirDadosDeUmFuncionario() {
        String dados = service.exibirDados("001");
        assertTrue(dados.contains("Ana Souza"));
        assertTrue(dados.contains("529.982.247-25"));
    }

    @Test
    public void exibirDadosDeInexistenteLancaExcecao() {
        assertThrows(FuncionarioNaoEncontradoException.class, () -> service.exibirDados("999"));
    }

    @Test
    public void relatorioContemTodosOsFuncionariosEOTotal() {
        String relatorio = service.gerarRelatorio();
        assertTrue(relatorio.contains("Ana Souza"));
        assertTrue(relatorio.contains("Bruno Lima"));
        assertTrue(relatorio.contains("Carla Dias"));
        assertTrue(relatorio.contains("TOTAL DA FOLHA"));
    }

    @Test
    public void relatorioSemFuncionariosInformaListaVazia() {
        FuncionarioService vazio = new FuncionarioService(new FuncionarioRepository());
        assertTrue(vazio.gerarRelatorio().contains("Nenhum funcionário cadastrado"));
    }
}
