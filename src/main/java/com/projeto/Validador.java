package com.projeto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Validador {

    public static boolean validarCPF(String cpf) {
        return cpf != null && cpf.matches("\\d{11}");
    }

    public static boolean validarSalario(BigDecimal salario) {
        return salario != null && salario.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean validarDataNascimento(LocalDate dataNascimento) {
        return dataNascimento != null && dataNascimento.isBefore(LocalDate.now().minusYears(18));
    }

    public static boolean validarMatricula(String matricula) {
        return matricula != null && !matricula.trim().isEmpty();
    }

    public static boolean validarCEP(String cep) {
        return cep != null && cep.matches("\\d{8}");
    }
}