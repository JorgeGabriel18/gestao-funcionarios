package br.edu.folha.model;

public class Desenvolvedor extends Funcionario implements Tributavel, Identificavel {
    private int horasExtras;
    private double valorHoraExtra;

    public Desenvolvedor(String codigo, String nome, String cpf, double salarioBase, int horasExtras, double valorHoraExtra) {
        super(codigo, nome, cpf, salarioBase);
        setHorasExtras(horasExtras);
        setValorHoraExtra(valorHoraExtra);
    }

    public int getHorasExtras() { return horasExtras; }
    public void setHorasExtras(int horasExtras) {
        if (horasExtras < 0) throw new IllegalArgumentException("Horas extras não podem ser negativas.");
        this.horasExtras = horasExtras;
    }

    public double getValorHoraExtra() { return valorHoraExtra; }
    public void setValorHoraExtra(double valorHoraExtra) {
        if (valorHoraExtra < 0) throw new IllegalArgumentException("Valor da hora extra não pode ser negativo.");
        this.valorHoraExtra = valorHoraExtra;
    }

    @Override
    public double calcularRemuneracao() {
        return getSalarioBase() + (horasExtras * valorHoraExtra);
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