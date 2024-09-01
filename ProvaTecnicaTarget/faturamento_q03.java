import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.text.NumberFormat;

public class faturamento_q03 {

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public static void criarArquivoTxt(String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            // Cria um arquivo vazio
        } catch (IOException e) {
            System.err.println("Erro ao criar o arquivo: " + e.getMessage());
        }
    }

    public static void adicionarDadosAoTxt(String nomeArquivo) {
        List<String> faturamentoDiario = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        try {
            List<String> linhas = Files.readAllLines(Paths.get(nomeArquivo));
            faturamentoDiario.addAll(linhas);
        } catch (IOException e) {
            System.out.println("Arquivo não encontrado. Criando novo arquivo.");
        }

        while (true) {
            System.out.print("Digite o dia (ou 0 para finalizar): ");
            int dia = scanner.nextInt();
            if (dia == 0) {
                break;
            }
            System.out.print("Digite o faturamento para o dia " + dia + ": ");
            double faturamento = scanner.nextDouble();
            faturamentoDiario.add(dia + "," + faturamento);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (String linha : faturamentoDiario) {
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados no arquivo: " + e.getMessage());
        }
        System.out.println("Dados adicionados com sucesso.");
    }

    public static void corrigirFaturamento(String nomeArquivo) {
        List<String> faturamentoDiario = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        try {
            List<String> linhas = Files.readAllLines(Paths.get(nomeArquivo));
            faturamentoDiario.addAll(linhas);
        } catch (IOException e) {
            System.out.println("Arquivo não encontrado. Nenhum dado para corrigir.");
            return;
        }

        System.out.print("Digite o dia do faturamento a ser corrigido: ");
        int dia = scanner.nextInt();
        boolean encontrado = false;
        for (int i = 0; i < faturamentoDiario.size(); i++) {
            String[] partes = faturamentoDiario.get(i).split(",");
            if (Integer.parseInt(partes[0]) == dia) {
                System.out.print("Digite o novo faturamento para o dia " + dia + ": ");
                double novoFaturamento = scanner.nextDouble();
                faturamentoDiario.set(i, dia + "," + novoFaturamento);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Dia não encontrado.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (String linha : faturamentoDiario) {
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados no arquivo: " + e.getMessage());
        }
        System.out.println("Dados corrigidos com sucesso.");
    }

    public static void listarFaturamento(String nomeArquivo) {
        List<String> faturamentoDiario = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        try {
            List<String> linhas = Files.readAllLines(Paths.get(nomeArquivo));
            faturamentoDiario.addAll(linhas);
        } catch (IOException e) {
            System.out.println("Arquivo não encontrado. Nenhum dado para listar.");
            return;
        }

        if (faturamentoDiario.isEmpty()) {
            System.out.println("Nenhum dado para exibir.");
            return;
        }

        System.out.println("Faturamento diário:");
        List<Double> faturamentos = new ArrayList<>();
        for (String linha : faturamentoDiario) {
            String[] partes = linha.split(",");
            int dia = Integer.parseInt(partes[0]);
            double faturamento = Double.parseDouble(partes[1]);
            System.out.println("Dia: " + dia + ", Faturamento: " + formatarMoeda(faturamento));
            faturamentos.add(faturamento);
        }

        // Calcular menor e maior valor de faturamento e média mensal
        double menorValor = faturamentos.stream().min(Double::compare).orElse(0.0);
        double maiorValor = faturamentos.stream().max(Double::compare).orElse(0.0);
        double mediaMensal = faturamentos.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        long diasAcimaDaMedia = faturamentos.stream().filter(f -> f > mediaMensal).count();

        System.out.println("\nMenor valor de faturamento em um dia do mês: " + formatarMoeda(menorValor));
        System.out.println("Maior valor de faturamento em um dia do mês: " + formatarMoeda(maiorValor));
        System.out.println("Número de dias com faturamento acima da média mensal: " + diasAcimaDaMedia);
    }

    public static void apagarDados(String nomeArquivo) {
        File arquivo = new File(nomeArquivo);
        if (arquivo.exists()) {
            arquivo.delete();
            System.out.println("Dados apagados com sucesso.");
        } else {
            System.out.println("Arquivo não encontrado.");
        }
    }

    private static String formatarMoeda(double valor) {
        return currencyFormat.format(valor);
    }

    public static void main(String[] args) {
        String nomeArquivo = "faturamento_q03.txt";  // Novo nome de arquivo
        criarArquivoTxt(nomeArquivo);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Adicionar Faturamento");
            System.out.println("2. Corrigir Faturamento");
            System.out.println("3. Listar Faturamento");
            System.out.println("4. Apagar Dados");
            System.out.println("5. Sair");

            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    adicionarDadosAoTxt(nomeArquivo);
                    break;
                case 2:
                    corrigirFaturamento(nomeArquivo);
                    break;
                case 3:
                    listarFaturamento(nomeArquivo);
                    break;
                case 4:
                    apagarDados(nomeArquivo);
                    break;
                case 5:
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
