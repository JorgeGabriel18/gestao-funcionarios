package br.edu.folha.repository;

import br.edu.folha.exception.FuncionarioJaCadastradoException;
import br.edu.folha.exception.FuncionarioNaoEncontradoException;
import br.edu.folha.model.Funcionario;
import br.edu.folha.util.CpfValidator;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioRepository {
    private List<Funcionario> listaFuncionarios = new ArrayList<>();

    public void salvar(Funcionario funcionario) {
        if (funcionario == null) {
            throw new IllegalArgumentException("O funcionário não pode ser nulo.");
        }
        boolean existe = listaFuncionarios.stream()
                .anyMatch(f -> f.getCodigo().equals(funcionario.getCodigo()));
        if (existe) {
            throw new FuncionarioJaCadastradoException("Funcionário com código " + funcionario.getCodigo() + " já está cadastrado.");
        }
        listaFuncionarios.add(funcionario);
    }

    public Funcionario buscarPorCodigo(String codigo) {
        return listaFuncionarios.stream()
                .filter(f -> f.getCodigo().equals(codigo))
                .findFirst()
                .orElseThrow(() -> new FuncionarioNaoEncontradoException("Funcionário com código " + codigo + " não foi encontrado."));
    }

    public Funcionario buscarPorCpf(String cpf) {
        String digitos = CpfValidator.somenteDigitos(cpf);
        return listaFuncionarios.stream()
                .filter(f -> f.getCpf().equals(digitos))
                .findFirst()
                .orElseThrow(() -> new FuncionarioNaoEncontradoException("Funcionário com CPF " + cpf + " não foi encontrado."));
    }

    public List<Funcionario> buscarPorNome(String nome, boolean buscaExata) {
        String alvo = nome.trim().toLowerCase();
        List<Funcionario> resultado = new ArrayList<>();
        for (Funcionario f : listaFuncionarios) {
            String atual = f.getNome().toLowerCase();
            boolean encontrou = buscaExata ? atual.equals(alvo) : atual.contains(alvo);
            if (encontrou) {
                resultado.add(f);
            }
        }
        return resultado;
    }

    public List<Funcionario> listarTodos() {
        return new ArrayList<>(listaFuncionarios);
    }

    public void remover(String codigo) {
        Funcionario f = buscarPorCodigo(codigo);
        listaFuncionarios.remove(f);
    }
}
