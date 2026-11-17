package br.edu.folha.model;

public class FuncionarioComissionado extends Funcionario implements Tributavel, Identificavel {
    private double totalVendas;
    private double percentualComissao;

    public FuncionarioComissionado(String codigo, String nome, String cpf, double salarioBase, double totalVendas, double percentualComissao) {
        super(codigo, nome, cpf, salarioBase);
        setTotalVendas(totalVendas);
        setPercentualComissao(percentualComissao);
    }

    public double getTotalVendas() {
        return totalVendas;
    }

    public void setTotalVendas(double totalVendas) {
        if (totalVendas < 0) {
            throw new IllegalArgumentException("O total de vendas não pode ser negativo.");
        }
        this.totalVendas = totalVendas;
    }

    public double getPercentualComissao() {
        return percentualComissao;
    }

    public void setPercentualComissao(double percentualComissao) {
        if (percentualComissao < 0 || percentualComissao > 100) {
            throw new IllegalArgumentException("Percentual de comissão inválido.");
        }
        this.percentualComissao = percentualComissao;
    }

    @Override
    public double calcularRemuneracao() {
        return getSalarioBase() + (totalVendas * (percentualComissao / 100.0));
    }

    @Override
    public double calcularTributos() {
        return calcularRemuneracao() * 0.15;
    }

    @Override
    public String getIdentificador() {
        return getCodigo();
    }
}