package br.edu.folha.model;

public class FuncionarioCLT extends Funcionario implements Tributavel, Identificavel {
    private double beneficio;

    public FuncionarioCLT(String codigo, String nome, String cpf, double salarioBase, double beneficio) {
        super(codigo, nome, cpf, salarioBase);
        setBeneficio(beneficio);
    }

    public double getBeneficio() {
        return beneficio;
    }

    public void setBeneficio(double beneficio) {
        if (beneficio < 0) {
            throw new IllegalArgumentException("O benefício não pode ser negativo.");
        }
        this.beneficio = beneficio;
    }

    @Override
    public double calcularRemuneracao() {
        return getSalarioBase() + beneficio;
    }

    @Override
    public double calcularTributos() {
        return calcularRemuneracao() * 0.10;
    }

    @Override
    public String getIdentificador() {
        return getCodigo();
    }
}