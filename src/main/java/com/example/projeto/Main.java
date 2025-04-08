package com.example.projeto;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main extends Application {

    private FuncionarioController controller = new FuncionarioController();
    private ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();
    private FuncionarioRelatorioService relatorioService = new FuncionarioRelatorioService();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gerenciamento de Funcionários");

        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));

        // === Campos de entrada ===
        TextField matriculaField = new TextField();
        matriculaField.setPromptText("Matrícula");

        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome");

        TextField cpfField = new TextField();
        cpfField.setPromptText("CPF");

        DatePicker dataNascimentoPicker = new DatePicker();
        dataNascimentoPicker.setPromptText("Data de Nascimento");

        TextField cargoField = new TextField();
        cargoField.setPromptText("Cargo");

        TextField salarioField = new TextField();
        salarioField.setPromptText("Salário");

        DatePicker dataContratacaoPicker = new DatePicker();
        dataContratacaoPicker.setPromptText("Data de Contratação");

        Button cadastrarButton = new Button("Cadastrar Funcionário");

        cadastrarButton.setOnAction(e -> {
            try {
                String matricula = matriculaField.getText();
                String nome = nomeField.getText();
                String cpf = cpfField.getText();
                LocalDate dataNascimento = dataNascimentoPicker.getValue();
                String cargo = cargoField.getText();
                BigDecimal salario = new BigDecimal(salarioField.getText());
                LocalDate dataContratacao = dataContratacaoPicker.getValue();

                Endereco endereco = new Endereco("Rua A", "123", "", "Centro", "Cidade", "Estado", "12345678");

                controller.cadastrarFuncionario(matricula, nome, cpf, dataNascimento, cargo, salario, dataContratacao, endereco);

                funcionarios.setAll(controller.listarFuncionarios());

                matriculaField.clear();
                nomeField.clear();
                cpfField.clear();
                cargoField.clear();
                salarioField.clear();
                dataNascimentoPicker.setValue(null);
                dataContratacaoPicker.setValue(null);
            } catch (Exception ex) {
                showAlert("Erro", "Falha ao cadastrar funcionário: " + ex.getMessage());
            }
        });

        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        formGrid.add(new Label("Matrícula:"), 0, 0);
        formGrid.add(matriculaField, 1, 0);
        formGrid.add(new Label("Nome:"), 0, 1);
        formGrid.add(nomeField, 1, 1);
        formGrid.add(new Label("CPF:"), 0, 2);
        formGrid.add(cpfField, 1, 2);
        formGrid.add(new Label("Nascimento:"), 0, 3);
        formGrid.add(dataNascimentoPicker, 1, 3);
        formGrid.add(new Label("Cargo:"), 0, 4);
        formGrid.add(cargoField, 1, 4);
        formGrid.add(new Label("Salário:"), 0, 5);
        formGrid.add(salarioField, 1, 5);
        formGrid.add(new Label("Contratação:"), 0, 6);
        formGrid.add(dataContratacaoPicker, 1, 6);
        formGrid.add(cadastrarButton, 1, 7);

        // === Tabela de funcionários ===
        TableView<Funcionario> tabela = new TableView<>(funcionarios);

        TableColumn<Funcionario, String> matriculaCol = new TableColumn<>("Matrícula");
        matriculaCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getMatricula()));

        TableColumn<Funcionario, String> nomeCol = new TableColumn<>("Nome");
        nomeCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNome()));

        TableColumn<Funcionario, String> cargoCol = new TableColumn<>("Cargo");
        cargoCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCargo()));

        TableColumn<Funcionario, String> salarioCol = new TableColumn<>("Salário");
        salarioCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getSalario().toString()));

        tabela.getColumns().addAll(matriculaCol, nomeCol, cargoCol, salarioCol);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // === Relatórios ===
        Button cargoButton = new Button("Filtrar por Cargo");
        cargoButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Filtrar por Cargo");
            dialog.setHeaderText("Digite o cargo desejado:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(cargo -> {
                List<Funcionario> filtrados = relatorioService.filtrarPorCargo(funcionarios, cargo);
                showTextDialog("Funcionários com cargo: " + cargo, formatarFuncionarios(filtrados));
            });
        });

        Button faixaSalarioButton = new Button("Filtrar por Faixa Salarial");
        faixaSalarioButton.setOnAction(e -> {
            TextInputDialog dialogMin = new TextInputDialog("0");
            dialogMin.setTitle("Filtrar por Faixa Salarial");
            dialogMin.setHeaderText("Digite o salário mínimo:");
            Optional<String> minResult = dialogMin.showAndWait();

            TextInputDialog dialogMax = new TextInputDialog("10000");
            dialogMax.setTitle("Filtrar por Faixa Salarial");
            dialogMax.setHeaderText("Digite o salário máximo:");
            Optional<String> maxResult = dialogMax.showAndWait();

            if (minResult.isPresent() && maxResult.isPresent()) {
                BigDecimal min = new BigDecimal(minResult.get());
                BigDecimal max = new BigDecimal(maxResult.get());
                List<Funcionario> filtrados = relatorioService.filtrarPorFaixaSalarial(funcionarios, min, max);
                showTextDialog("Funcionários entre " + min + " e " + max, formatarFuncionarios(filtrados));
            }
        });

        Button mediaSalarialButton = new Button("Média Salarial por Cargo");
        mediaSalarialButton.setOnAction(e -> {
            Map<String, Double> medias = relatorioService.mediaSalarialPorCargo(funcionarios);
            StringBuilder sb = new StringBuilder();
            medias.forEach((cargo, media) -> sb.append(cargo).append(": R$ ").append(String.format("%.2f", media)).append("\n"));
            showTextDialog("Média Salarial por Cargo", sb.toString());
        });

        Button porCidadeButton = new Button("Agrupar por Cidade");
        porCidadeButton.setOnAction(e -> {
            Map<String, List<Funcionario>> mapa = relatorioService.agruparPorCidade(funcionarios);
            StringBuilder sb = new StringBuilder();
            mapa.forEach((cidade, list) -> {
                sb.append(cidade).append(": ").append(list.size()).append(" funcionário(s)\n");
            });
            showTextDialog("Funcionários por Cidade", sb.toString());
        });

        Button porEstadoButton = new Button("Agrupar por Estado");
        porEstadoButton.setOnAction(e -> {
            Map<String, List<Funcionario>> mapa = relatorioService.agruparPorEstado(funcionarios);
            StringBuilder sb = new StringBuilder();
            mapa.forEach((estado, list) -> {
                sb.append(estado).append(": ").append(list.size()).append(" funcionário(s)\n");
            });
            showTextDialog("Funcionários por Estado", sb.toString());
        });

        HBox relatorioButtons = new HBox(10, cargoButton, faixaSalarioButton, mediaSalarialButton, porCidadeButton, porEstadoButton);

        vbox.getChildren().addAll(formGrid, new Label("Funcionários cadastrados:"), tabela, new Label("Relatórios:"), relatorioButtons);

        primaryStage.setScene(new Scene(vbox, 800, 700));
        primaryStage.show();
    }

    private String formatarFuncionarios(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .map(Funcionario::toString)
                .collect(Collectors.joining("\n"));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showTextDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);

        TextArea textArea = new TextArea(content);
        textArea.setWrapText(true);
        textArea.setEditable(false);
        alert.getDialogPane().setContent(textArea);

        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
