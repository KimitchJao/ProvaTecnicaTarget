import java.util.Scanner;

public class Fibonacci {
    public static boolean isFibonacciNumber(int n) {
        // Função para verificar se um número pertence à sequência de Fibonacci
        int a = 0, b = 1;
        while (b < n) {
            int temp = b;
            b = a + b;
            a = temp;
        }
        return (b == n || n == 0);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Digite um número para verificar se pertence à sequência de Fibonacci (ou 'sair' para encerrar): ");
            String input = scanner.nextLine();
            
            // Verifica se o usuário quer sair
            if (input.equalsIgnoreCase("sair")) {
                System.out.println("Encerrando o programa.");
                break;
            }

            try {
                // Tenta converter a entrada para um número inteiro
                int numero = Integer.parseInt(input);
                
                // Verifica se o número pertence à sequência de Fibonacci e exibe a mensagem apropriada
                if (isFibonacciNumber(numero)) {
                    System.out.println("O número " + numero + " pertence à sequência de Fibonacci.");
                } else {
                    System.out.println("O número " + numero + " não pertence à sequência de Fibonacci.");
                }
            } catch (NumberFormatException e) {
                // Mensagem para entradas inválidas que não sejam "sair"
                System.out.println("Entrada inválida! Por favor, digite um número ou 'sair' para encerrar.");
            }
        }
        
        scanner.close();
    }
}
