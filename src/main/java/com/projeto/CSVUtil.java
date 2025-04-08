package com.projeto;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CSVUtil {

    private static final String FILE_PATH = "funcionarios.csv";

    public static List<Funcionario> carregarFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(FILE_PATH))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                Endereco endereco = new Endereco(dados[7], dados[8], dados[9], dados[10], dados[11], dados[12], dados[13]);
                Funcionario funcionario = new Funcionario(
                        dados[0], // Matrícula
                        dados[1], // Nome
                        dados[2], // CPF
                        LocalDate.parse(dados[3]), // Data de nascimento
                        dados[4], // Cargo
                        new BigDecimal(dados[5]), // Salário
                        LocalDate.parse(dados[6]), // Data de contratação
                        endereco // Endereço
                );
                funcionarios.add(funcionario);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return funcionarios;
    }

    public static void salvarFuncionarios(List<Funcionario> funcionarios) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_PATH))) {
            for (Funcionario funcionario : funcionarios) {
                writer.write(funcionario.getMatricula() + ";");
                writer.write(funcionario.getNome() + ";");
                writer.write(funcionario.getCpf() + ";");
                writer.write(funcionario.getDataNascimento() + ";");
                writer.write(funcionario.getCargo() + ";");
                writer.write(funcionario.getSalario() + ";");
                writer.write(funcionario.getDataContratacao() + ";");
                writer.write(funcionario.getEndereco().getLogradouro() + ";");
                writer.write(funcionario.getEndereco().getNumero() + ";");
                writer.write(funcionario.getEndereco().getComplemento() + ";");
                writer.write(funcionario.getEndereco().getBairro() + ";");
                writer.write(funcionario.getEndereco().getCidade() + ";");
                writer.write(funcionario.getEndereco().getEstado() + ";");
                writer.write(funcionario.getEndereco().getCep());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
