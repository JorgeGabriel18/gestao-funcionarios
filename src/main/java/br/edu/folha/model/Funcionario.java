package br.edu.folha.model;

import br.edu.folha.exception.CpfInvalidoException;
import br.edu.folha.util.CpfValidator;

import java.util.Locale;

public abstract class Funcionario {
    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

    private String codigo;
    private String nome;
    private String cpf;
    private double salarioBase;

    // Construtor completo
    public Funcionario(String codigo, String nome, String cpf, double salarioBase) {
        setCodigo(codigo);
        setNome(nome);
        setCpf(cpf);
        setSalarioBase(salarioBase);
    }

    // Método abstrato exigido pela atividade
    public abstract double calcularRemuneracao();

    // Exibe os dados completos do funcionário
    public String exibirDados() {
        String quebra = System.lineSeparator();
        StringBuilder sb = new StringBuilder();
        sb.append("Código: ").append(codigo).append(quebra);
        sb.append("Tipo: ").append(getClass().getSimpleName()).append(quebra);
        sb.append("Nome: ").append(nome).append(quebra);
        sb.append("CPF: ").append(getCpfFormatado()).append(quebra);
        sb.append(String.format(PT_BR, "Salário-base: R$ %,.2f", salarioBase)).append(quebra);
        sb.append(String.format(PT_BR, "Remuneração: R$ %,.2f", calcularRemuneracao()));
        if (this instanceof Tributavel) {
            double tributos = ((Tributavel) this).calcularTributos();
            sb.append(quebra).append(String.format(PT_BR, "Tributos: R$ %,.2f", tributos));
        }
        return sb.toString();
    }

    // Getters e Setters com validações básicas
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("Código não pode ser vazio.");
        }
        this.codigo = codigo;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        this.nome = nome;
    }

    /** CPF guardado somente com dígitos. */
    public String getCpf() { return cpf; }

    public String getCpfFormatado() {
        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "."
                + cpf.substring(6, 9) + "-" + cpf.substring(9);
    }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio.");
        }
        if (!CpfValidator.isValido(cpf)) {
            throw new CpfInvalidoException("CPF inválido: " + cpf);
        }
        this.cpf = CpfValidator.somenteDigitos(cpf);
    }

    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salarioBase) {
        if (salarioBase < 0) {
            throw new IllegalArgumentException("Salário base não pode ser negativo.");
        }
        this.salarioBase = salarioBase;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[codigo=" + codigo + ", nome=" + nome + "]";
    }
}
