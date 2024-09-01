import java.util.Scanner;

public class InverteString {
    public static void main(String[] args) {
        // Cria um objeto Scanner para capturar a entrada do usuário
        Scanner scanner = new Scanner(System.in);

        // Solicita ao usuário a entrada da string
        System.out.print("Digite a string que deseja inverter: ");
        String textoOriginal = scanner.nextLine();

        // Chamando o método que inverte a string
        String resultado = inverterString(textoOriginal);

        // Exibindo o resultado
        System.out.println("String original: " + textoOriginal);
        System.out.println("String invertida: " + resultado);

        // Fecha o scanner
        scanner.close();
    }

    // Método para inverter a string
    public static String inverterString(String texto) {
        // Inicializa a string invertida como uma String vazia
        String invertida = "";

        // Percorre a string de trás para frente e adiciona cada caractere à string invertida
        for (int i = texto.length() - 1; i >= 0; i--) {
            invertida += texto.charAt(i);
        }
        
        return invertida;
    }
}
