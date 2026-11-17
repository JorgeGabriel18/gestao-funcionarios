package br.edu.folha.model;

public class Gerente extends Funcionario implements Tributavel, Identificavel {
    private double participacaoLucros;

    public Gerente(String codigo, String nome, String cpf, double salarioBase, double participacaoLucros) {
        super(codigo, nome, cpf, salarioBase);
        setParticipacaoLucros(participacaoLucros);
    }

    public double getParticipacaoLucros() { return participacaoLucros; }
    public void setParticipacaoLucros(double participacaoLucros) {
        if (participacaoLucros < 0) throw new IllegalArgumentException("Participação nos lucros não pode ser negativa.");
        this.participacaoLucros = participacaoLucros;
    }

    @Override
    public double calcularRemuneracao() {
        return getSalarioBase() + participacaoLucros;
    }

    @Override
    public double calcularTributos() {
        return calcularRemuneracao() * 0.20;
    }

    @Override
    public String getIdentificador() {
        return getCodigo();
    }
}
