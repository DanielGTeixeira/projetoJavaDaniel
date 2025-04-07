package com.example.projeto;

import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class FuncionarioController {
    private List<Funcionario> funcionarios;

    public FuncionarioController() {
        funcionarios = CSVUtil.carregarFuncionarios(); // Carrega os funcionários do CSV
    }

    public void cadastrarFuncionario(String matricula, String nome, String cpf, LocalDate dataNascimento, String cargo, BigDecimal salario, LocalDate dataContratacao, Endereco endereco) {
        Funcionario funcionario = new Funcionario(matricula, nome, cpf, dataNascimento, cargo, salario, dataContratacao, endereco);
        funcionarios.add(funcionario);
        CSVUtil.salvarFuncionarios(funcionarios); // Salva os funcionários de volta no CSV
    }

    public Funcionario consultarFuncionario(String matricula) {
        return funcionarios.stream()
                .filter(f -> f.getMatricula().equals(matricula))
                .findFirst()
                .orElse(null);
    }

    public void excluirFuncionario(String matricula) {
        funcionarios.removeIf(f -> f.getMatricula().equals(matricula));
        CSVUtil.salvarFuncionarios(funcionarios); // Atualiza o CSV após exclusão
    }

    public List<Funcionario> listarFuncionarios() {
        return funcionarios;
    }
}
