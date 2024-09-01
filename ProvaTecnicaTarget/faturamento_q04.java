
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class faturamento_q04 {
    private static final String FILE_NAME = "faturamento_q04.txt";

    public static void main(String[] args) {
        Map<String, Double> faturamento = carregarDados();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menu de Opções:");
            System.out.println("1. Listar Faturamento");
            System.out.println("2. Calcular Faturamento");
            System.out.println("3. Corrigir Faturamento");
            System.out.println("4. Sair");

            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    listarFaturamento(faturamento);
                    break;
                case "2":
                    System.out.print("Digite o estado (SP, RJ, MG, ES, Outros): ");
                    String estado = normalizarEstado(scanner.nextLine());
                    calcularPercentual(faturamento, estado);
                    break;
                case "3":
                    System.out.print("Digite o estado (SP, RJ, MG, ES, Outros): ");
                    String estadoParaCorrigir = normalizarEstado(scanner.nextLine());
                    System.out.print("Digite o novo valor de faturamento: ");
                    double novoValor = scanner.nextDouble();
                    scanner.nextLine();  // Clear buffer
                    corrigirFaturamento(faturamento, estadoParaCorrigir, novoValor);
                    break;
                case "4":
                    System.out.println("Saindo do programa.");
                    salvarDados(faturamento);
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // Função para normalizar o nome do estado
    private static String normalizarEstado(String estado) {
        return estado.trim().toUpperCase();
    }

    // Função para listar o faturamento
    private static void listarFaturamento(Map<String, Double> dados) {
        System.out.println("\nFaturamento por Estado:");
        for (Map.Entry<String, Double> entry : dados.entrySet()) {
            System.out.printf("%s: R$%.2f%n", entry.getKey(), entry.getValue());
        }
        System.out.println();
    }

    // Função para calcular o percentual de faturamento de um estado
    private static void calcularPercentual(Map<String, Double> dados, String estado) {
        double total = dados.values().stream().mapToDouble(Double::doubleValue).sum();
        if (dados.containsKey(estado)) {
            double percentual = (dados.get(estado) / total) * 100;
            System.out.printf("\nPercentual de %s: %.2f%%%n%n", estado, percentual);
        } else {
            System.out.println("\nEstado não encontrado!\n");
        }
    }

    // Função para corrigir o faturamento de um estado
    private static void corrigirFaturamento(Map<String, Double> dados, String estado, double novoValor) {
        if (dados.containsKey(estado)) {
            dados.put(estado, novoValor);
            System.out.println("\nFaturamento atualizado com sucesso!\n");
        } else {
            System.out.println("\nEstado não encontrado!\n");
        }
    }

    // Função para salvar dados no arquivo TXT
    private static void salvarDados(Map<String, Double> dados) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<String, Double> entry : dados.entrySet()) {
                writer.printf("%s:%.2f%n", entry.getKey(), entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados no arquivo: " + e.getMessage());
        }
    }

    // Função para carregar dados do arquivo TXT
    private static Map<String, Double> carregarDados() {
        Map<String, Double> dados = new HashMap<>();
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String[] linha = scanner.nextLine().split(":");
                    dados.put(linha[0], Double.parseDouble(linha[1]));
                }
            } catch (FileNotFoundException e) {
                System.out.println("Erro ao abrir o arquivo: " + e.getMessage());
            }
        } else {
            dados.put("SP", 67836.43);
            dados.put("RJ", 36678.66);
            dados.put("MG", 29229.88);
            dados.put("ES", 27165.48);
            dados.put("Outros", 19849.53);
        }
        return dados;
    }
}

