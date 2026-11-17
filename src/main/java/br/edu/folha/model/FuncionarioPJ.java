package br.edu.folha.model;

public class FuncionarioPJ extends Funcionario implements Identificavel {
    private int quantidadeProjetos;
    private double valorPorProjeto;

    public FuncionarioPJ(String codigo, String nome, String cpf, double salarioBase, int quantidadeProjetos, double valorPorProjeto) {
        super(codigo, nome, cpf, salarioBase);
        setQuantidadeProjetos(quantidadeProjetos);
        setValorPorProjeto(valorPorProjeto);
    }

    public int getQuantidadeProjetos() {
        return quantidadeProjetos;
    }

    public void setQuantidadeProjetos(int quantidadeProjetos) {
        if (quantidadeProjetos < 0) {
            throw new IllegalArgumentException("A quantidade de projetos não pode ser negativa.");
        }
        this.quantidadeProjetos = quantidadeProjetos;
    }

    public double getValorPorProjeto() {
        return valorPorProjeto;
    }

    public void setValorPorProjeto(double valorPorProjeto) {
        if (valorPorProjeto < 0) {
            throw new IllegalArgumentException("O valor por projeto não pode ser negativo.");
        }
        this.valorPorProjeto = valorPorProjeto;
    }

    @Override
    public double calcularRemuneracao() {
        return getSalarioBase() + (quantidadeProjetos * valorPorProjeto);
    }

    @Override
    public String getIdentificador() {
        return getCodigo();
    }
}