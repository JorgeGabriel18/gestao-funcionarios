package br.edu.folha.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CpfValidatorTest {

    @Test
    void aceitaCpfValidoComESemMascara() {
        assertTrue(CpfValidator.isValido("529.982.247-25"));
        assertTrue(CpfValidator.isValido("52998224725"));
        assertTrue(CpfValidator.isValido("111.444.777-35"));
    }

    @Test
    void rejeitaDigitosVerificadoresErrados() {
        assertFalse(CpfValidator.isValido("529.982.247-26"));
        assertFalse(CpfValidator.isValido("12345678900"));
    }

    @Test
    void rejeitaSequenciasRepetidas() {
        assertFalse(CpfValidator.isValido("111.111.111-11"));
        assertFalse(CpfValidator.isValido("00000000000"));
    }

    @Test
    void rejeitaTamanhoIncorretoNuloOuVazio() {
        assertFalse(CpfValidator.isValido("123"));
        assertFalse(CpfValidator.isValido(""));
        assertFalse(CpfValidator.isValido(null));
        assertFalse(CpfValidator.isValido("abc"));
    }

    @Test
    void somenteDigitosRemoveMascara() {
        assertEquals("52998224725", CpfValidator.somenteDigitos("529.982.247-25"));
        assertEquals("", CpfValidator.somenteDigitos(null));
    }
}
