package br.edu.folha.util;

/** Utilitário para normalização e validação de CPF (dígitos verificadores). */
public final class CpfValidator {

    private CpfValidator() {
    }

    /** Remove tudo que não for dígito. */
    public static String somenteDigitos(String cpf) {
        return cpf == null ? "" : cpf.replaceAll("\\D", "");
    }

    public static boolean isValido(String cpf) {
        String digitos = somenteDigitos(cpf);
        if (digitos.length() != 11) {
            return false;
        }
        if (digitos.chars().distinct().count() == 1) {
            return false; // 111.111.111-11 etc.
        }
        int dv1 = calcularDigito(digitos, 9);
        int dv2 = calcularDigito(digitos, 10);
        return dv1 == digitos.charAt(9) - '0' && dv2 == digitos.charAt(10) - '0';
    }

    private static int calcularDigito(String digitos, int quantidade) {
        int soma = 0;
        for (int i = 0; i < quantidade; i++) {
            soma += (digitos.charAt(i) - '0') * (quantidade + 1 - i);
        }
        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }
}
