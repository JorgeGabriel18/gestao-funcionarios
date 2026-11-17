package br.edu.folha.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.edu.folha.exception.FuncionarioJaCadastradoException;
import br.edu.folha.exception.FuncionarioNaoEncontradoException;
import br.edu.folha.model.Funcionario;
import br.edu.folha.model.FuncionarioCLT;
import br.edu.folha.model.FuncionarioPJ;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FuncionarioRepositoryTest {

    private FuncionarioRepository repository;
    private Funcionario ana;
    private Funcionario bruno;

    @BeforeEach
    void setUp() {
        repository = new FuncionarioRepository();
        ana = new FuncionarioCLT("001", "Ana Souza", "529.982.247-25", 3000, 500);
        bruno = new FuncionarioPJ("002", "Bruno Lima", "111.444.777-35", 1500, 2, 2000);
    }

    @Test
    void salvaERecuperaPorCodigo() {
        repository.salvar(ana);
        assertEquals(ana, repository.buscarPorCodigo("001"));
    }

    @Test
    void buscaPorCodigoInexistenteLancaExcecao() {
        assertThrows(FuncionarioNaoEncontradoException.class, () -> repository.buscarPorCodigo("999"));
    }

    @Test
    void naoPermiteCodigoDuplicado() {
        repository.salvar(ana);
        Funcionario mesmoCodigo = new FuncionarioPJ("001", "Outro", "123.456.789-09", 100, 1, 100);
        assertThrows(FuncionarioJaCadastradoException.class, () -> repository.salvar(mesmoCodigo));
        assertEquals(1, repository.listarTodos().size());
    }

    @Test
    void rejeitaFuncionarioNulo() {
        assertThrows(IllegalArgumentException.class, () -> repository.salvar(null));
    }

    @Test
    void buscaPorCpfComESemMascara() {
        repository.salvar(ana);
        assertEquals(ana, repository.buscarPorCpf("529.982.247-25"));
        assertEquals(ana, repository.buscarPorCpf("52998224725"));
    }

    @Test
    void buscaPorCpfInexistenteLancaExcecao() {
        repository.salvar(ana);
        assertThrows(FuncionarioNaoEncontradoException.class, () -> repository.buscarPorCpf("111.444.777-35"));
    }

    @Test
    void buscaPorNomeExataEParcial() {
        repository.salvar(ana);
        repository.salvar(bruno);
        assertEquals(1, repository.buscarPorNome("ana souza", true).size());
        assertEquals(0, repository.buscarPorNome("ana", true).size());
        assertEquals(1, repository.buscarPorNome("ana", false).size());
        assertEquals(2, repository.buscarPorNome("a", false).size());
    }

    @Test
    void removeFuncionarioExistente() {
        repository.salvar(ana);
        repository.remover("001");
        assertEquals(0, repository.listarTodos().size());
    }

    @Test
    void removerInexistenteLancaExcecao() {
        assertThrows(FuncionarioNaoEncontradoException.class, () -> repository.remover("999"));
    }

    @Test
    void listarTodosMantemOrdemERetornaCopia() {
        repository.salvar(ana);
        repository.salvar(bruno);
        List<Funcionario> lista = repository.listarTodos();
        assertEquals(List.of(ana, bruno), lista);
        lista.clear();
        assertEquals(2, repository.listarTodos().size());
    }
}
