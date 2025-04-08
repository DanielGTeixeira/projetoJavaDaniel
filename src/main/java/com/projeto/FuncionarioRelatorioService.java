package com.projeto;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class FuncionarioRelatorioService {

    public FuncionarioRelatorioService() {
    }

    public List<Funcionario> filtrarPorCargo(List<Funcionario> funcionarios, String cargo) {
        return funcionarios.stream()
                .filter(f -> f.getCargo().equalsIgnoreCase(cargo))
                .collect(Collectors.toList());
    }

    public List<Funcionario> filtrarPorFaixaSalarial(List<Funcionario> funcionarios, BigDecimal min, BigDecimal max) {
        return funcionarios.stream()
                .filter(f -> f.getSalario().compareTo(min) >= 0 && f.getSalario().compareTo(max) <= 0)
                .collect(Collectors.toList());
    }

    public Map<String, Double> mediaSalarialPorCargo(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .collect(Collectors.groupingBy(
                        Funcionario::getCargo,
                        Collectors.averagingDouble(f -> f.getSalario().doubleValue())
                ));
    }

    public Map<String, List<Funcionario>> agruparPorCidade(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .collect(Collectors.groupingBy(f -> f.getEndereco().getCidade()));
    }

    public Map<String, List<Funcionario>> agruparPorEstado(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .collect(Collectors.groupingBy(f -> f.getEndereco().getEstado()));
    }
}
