package br.edu.folha.service;

import br.edu.folha.exception.FuncionarioJaCadastradoException;
import br.edu.folha.exception.FuncionarioNaoEncontradoException;
import br.edu.folha.model.Funcionario;
import br.edu.folha.model.Tributavel;
import br.edu.folha.repository.FuncionarioRepository;

import java.util.List;
import java.util.Locale;

public class FuncionarioService {
    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

    private FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("O repositório é obrigatório.");
        }
        this.repository = repository;
    }

    public void cadastrar(Funcionario funcionario) throws FuncionarioJaCadastradoException {
        repository.salvar(funcionario);
    }

    public List<Funcionario> listar() {
        return repository.listarTodos();
    }

    // ---------- sobrecarga (overload) de buscarFuncionario ----------

    /** Busca pelo código (identificador) do funcionário. */
    public Funcionario buscarFuncionario(String codigo) {
        return repository.buscarPorCodigo(codigo);
    }

    /** Busca por nome: exata (igual) ou parcial (contém). */
    public List<Funcionario> buscarFuncionario(String nome, boolean buscaExata) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }
        List<Funcionario> encontrados = repository.buscarPorNome(nome, buscaExata);
        if (encontrados.isEmpty()) {
            throw new FuncionarioNaoEncontradoException("Nenhum funcionário encontrado para o nome \"" + nome + "\".");
        }
        return encontrados;
    }

    public Funcionario buscarPorCpf(String cpf) {
        return repository.buscarPorCpf(cpf);
    }

    public void remover(String codigo) {
        repository.remover(codigo);
    }

    public double calcularRemuneracao(String codigo) {
        return repository.buscarPorCodigo(codigo).calcularRemuneracao();
    }

    public String exibirDados(String codigo) {
        return repository.buscarPorCodigo(codigo).exibirDados();
    }

    /** Total da folha: soma das remunerações de todos os funcionários. */
    public double calcularTotalRemuneracoes() {
        List<Funcionario> todos = repository.listarTodos();
        double total = 0;
        for (Funcionario f : todos) {
            total += f.calcularRemuneracao(); // polimorfismo
        }
        return total;
    }

    public double calcularTotalTributos() {
        List<Funcionario> todos = repository.listarTodos();
        double totalTributos = 0;
        for (Funcionario f : todos) {
            if (f instanceof Tributavel) {
                totalTributos += ((Tributavel) f).calcularTributos();
            }
        }
        return totalTributos;
    }

    public String gerarRelatorio() {
        List<Funcionario> todos = repository.listarTodos();
        String quebra = System.lineSeparator();
        StringBuilder sb = new StringBuilder();
        sb.append("===== RELATÓRIO DA FOLHA DE PAGAMENTO =====").append(quebra);
        if (todos.isEmpty()) {
            sb.append("Nenhum funcionário cadastrado.").append(quebra);
        }
        for (Funcionario f : todos) {
            sb.append(String.format(PT_BR, "%-6s %-24s %-26s R$ %,12.2f",
                    f.getCodigo(), f.getClass().getSimpleName(), f.getNome(), f.calcularRemuneracao()))
              .append(quebra);
        }
        sb.append(String.format(PT_BR, "TOTAL DA FOLHA: R$ %,.2f", calcularTotalRemuneracoes())).append(quebra);
        return sb.toString();
    }
}
