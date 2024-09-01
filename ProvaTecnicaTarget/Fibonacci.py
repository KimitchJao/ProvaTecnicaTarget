#Definição do Número de Fibonacci

def numero_fibonacci(n):
    # Função para verificar se um número pertence à sequência de Fibonacci
    a, b = 0, 1
    while b < n:
        a, b = b, a + b
    return b == n or n == 0

#Programa Principal

while True:
    # Tenta capturar a entrada do usuário
    entrada = input("Digite um número para verificar se pertence à sequência de Fibonacci (ou digite sair para fechar o programa): ")
    # Verifica se a entrada é um número inteiro
    if entrada.isdigit():
        entrada = int(entrada)
        if numero_fibonacci(entrada):
            print(f"O número {entrada} pertence à sequência de Fibonacci.")
        else:
            print(f"O número {entrada} não pertence à sequência de Fibonacci.")
    else:
        if entrada.upper() == "SAIR":
            print("Saindo do programa.")
            break
        else:
            print("Input Inválido, digite um número, ou digite 'sair' para sair do programa:")
