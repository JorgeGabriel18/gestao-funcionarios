package br.edu.folha.view;

import br.edu.folha.exception.FuncionarioJaCadastradoException;
import br.edu.folha.exception.FuncionarioNaoEncontradoException;
import br.edu.folha.model.Desenvolvedor;
import br.edu.folha.model.Funcionario;
import br.edu.folha.model.FuncionarioCLT;
import br.edu.folha.model.FuncionarioComissionado;
import br.edu.folha.model.FuncionarioPJ;
import br.edu.folha.model.Gerente;
import br.edu.folha.service.FuncionarioService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/** Interface de console: lê dados do usuário e apresenta resultados. */
public class FuncionarioView {

    private final FuncionarioService service;
    private final Scanner scanner;
    private final PrintStream out;

    public FuncionarioView(FuncionarioService service, InputStream in, PrintStream out) {
        this.service = service;
        this.scanner = new Scanner(in);
        this.out = out;
    }

    public void iniciar() {
        try {
            int opcao;
            do {
                exibirMenu();
                opcao = lerInt("Opção: ");
                executar(opcao);
            } while (opcao != 0);
        } catch (NoSuchElementException e) {
            out.println("Entrada encerrada.");
        }
    }

    private void exibirMenu() {
        out.println();
        out.println("===== GESTÃO DE FUNCIONÁRIOS =====");
        out.println("1 - Cadastrar funcionário");
        out.println("2 - Listar funcionários");
        out.println("3 - Buscar por código");
        out.println("4 - Buscar por CPF");
        out.println("5 - Buscar por nome");
        out.println("6 - Remover funcionário");
        out.println("7 - Exibir dados completos");
        out.println("8 - Relatório da folha");
        out.println("9 - Total da folha");
        out.println("0 - Sair");
    }

    private void executar(int opcao) {
        try {
            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listar();
                case 3 -> out.println(service.buscarFuncionario(lerTexto("Código: ")));
                case 4 -> out.println(service.buscarPorCpf(lerTexto("CPF: ")));
                case 5 -> buscarPorNome();
                case 6 -> remover();
                case 7 -> out.println(service.exibirDados(lerTexto("Código: ")));
                case 8 -> out.print(service.gerarRelatorio());
                case 9 -> out.printf("Total da folha: %.2f%n", service.calcularTotalRemuneracoes());
                case 0 -> out.println("Até logo!");
                default -> out.println("Opção inválida.");
            }
        } catch (FuncionarioNaoEncontradoException | FuncionarioJaCadastradoException
                 | IllegalArgumentException e) {
            // IllegalArgumentException cobre também CpfInvalidoException
            out.println("Erro: " + e.getMessage());
        }
    }

    private void cadastrar() {
        out.println("Tipo: 1 - CLT | 2 - PJ | 3 - Comissionado | 4 - Desenvolvedor | 5 - Gerente");
        int tipo = lerInt("Tipo: ");
        if (tipo < 1 || tipo > 5) {
            throw new IllegalArgumentException("Tipo de funcionário inválido.");
        }
        String codigo = lerTexto("Código: ");
        String nome = lerTexto("Nome: ");
        String cpf = lerTexto("CPF: ");
        double salarioBase = lerDouble("Salário-base: ");

        Funcionario funcionario;
        switch (tipo) {
            case 1 -> funcionario = new FuncionarioCLT(codigo, nome, cpf, salarioBase,
                    lerDouble("Benefício: "));
            case 2 -> funcionario = new FuncionarioPJ(codigo, nome, cpf, salarioBase,
                    lerInt("Quantidade de projetos: "), lerDouble("Valor por projeto: "));
            case 3 -> funcionario = new FuncionarioComissionado(codigo, nome, cpf, salarioBase,
                    lerDouble("Total de vendas: "), lerDouble("Percentual de comissão (0-100): "));
            case 4 -> funcionario = new Desenvolvedor(codigo, nome, cpf, salarioBase,
                    lerInt("Horas extras: "), lerDouble("Valor da hora extra: "));
            default -> funcionario = new Gerente(codigo, nome, cpf, salarioBase,
                    lerDouble("Participação nos lucros: "));
        }
        service.cadastrar(funcionario);
        out.println("Funcionário cadastrado com sucesso.");
    }

    private void listar() {
        List<Funcionario> todos = service.listar();
        if (todos.isEmpty()) {
            out.println("Nenhum funcionário cadastrado.");
            return;
        }
        todos.forEach(out::println);
    }

    private void buscarPorNome() {
        String nome = lerTexto("Nome: ");
        boolean exata = lerTexto("Busca exata? (s/n): ").equalsIgnoreCase("s");
        service.buscarFuncionario(nome, exata).forEach(out::println);
    }

    private void remover() {
        service.remover(lerTexto("Código: "));
        out.println("Funcionário removido.");
    }

    // ---------- leitura de dados ----------

    private String lerTexto(String mensagem) {
        out.print(mensagem);
        return scanner.nextLine();
    }

    private int lerInt(String mensagem) {
        while (true) {
            try {
                return Integer.parseInt(lerTexto(mensagem).trim());
            } catch (NumberFormatException e) {
                out.println("Digite um número inteiro válido.");
            }
        }
    }

    private double lerDouble(String mensagem) {
        while (true) {
            try {
                return Double.parseDouble(lerTexto(mensagem).trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                out.println("Digite um número válido.");
            }
        }
    }
}
