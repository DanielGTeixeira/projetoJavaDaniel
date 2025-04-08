package com.projeto;

import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class FuncionarioController {
    private List<Funcionario> funcionarios;

    public FuncionarioController() {
        funcionarios = CSVUtil.carregarFuncionarios();
    }

    public void cadastrarFuncionario(String matricula, String nome, String cpf, LocalDate dataNascimento, String cargo, BigDecimal salario, LocalDate dataContratacao, Endereco endereco) {
        Funcionario funcionario = new Funcionario(matricula, nome, cpf, dataNascimento, cargo, salario, dataContratacao, endereco);
        funcionarios.add(funcionario);
        CSVUtil.salvarFuncionarios(funcionarios);
    }

    public Funcionario consultarFuncionario(String matricula) {
        return funcionarios.stream()
                .filter(f -> f.getMatricula().equals(matricula))
                .findFirst()
                .orElse(null);
    }

    public void excluirFuncionario(String matricula) {
        funcionarios.removeIf(f -> f.getMatricula().equals(matricula));
        CSVUtil.salvarFuncionarios(funcionarios);
    }

    public List<Funcionario> listarFuncionarios() {
        return funcionarios;
    }
}
