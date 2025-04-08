package com.projeto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Funcionario implements Serializable {
    private String matricula;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String cargo;
    private BigDecimal salario;
    private LocalDate dataContratacao;
    private Endereco endereco;

    public Funcionario(String matricula, String nome, String cpf, LocalDate dataNascimento, String cargo, BigDecimal salario, LocalDate dataContratacao, Endereco endereco) {
        this.matricula = matricula;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.cargo = cargo;
        this.salario = salario;
        this.dataContratacao = dataContratacao;
        this.endereco = endereco;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getCargo() {
        return cargo;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public String toString() {
        return "Matrícula: " + matricula + "\n" +
                "Nome: " + nome + "\n" +
                "CPF: " + cpf + "\n" +
                "Nascimento: " + dataNascimento + "\n" +
                "Cargo: " + cargo + "\n" +
                "Salário: R$" + salario + "\n" +
                "Contratação: " + dataContratacao + "\n" +
                "Cidade: " + endereco.getCidade() + "\n" +
                "Estado: " + endereco.getEstado() + "\n\n";
    }
}
