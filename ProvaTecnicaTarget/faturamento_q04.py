import json

# Nome do arquivo JSON
arquivo_json = 'faturamento_q04.json'

# Inicializa os dados de faturamento
faturamento_inicial = {
    "SP": 67836.43,
    "RJ": 36678.66,
    "MG": 29229.88,
    "ES": 27165.48,
    "Outros": 19849.53
}

# Função para salvar dados no arquivo JSON
def salvar_dados(dados):
    with open(arquivo_json, 'w') as arquivo:
        json.dump(dados, arquivo, indent=4)

# Função para carregar dados do arquivo JSON
def carregar_dados():
    try:
        with open(arquivo_json, 'r') as arquivo:
            return json.load(arquivo)
    except FileNotFoundError:
        salvar_dados(faturamento_inicial)
        return faturamento_inicial

# Função para normalizar o nome do estado
def normalizar_estado(estado):
    return estado.strip().upper()

# Função para listar o faturamento
def listar_faturamento(dados):
    print("\nFaturamento por Estado:")
    for estado, valor in dados.items():
        print(f"{estado}: R${valor:.2f}")
    print()

# Função para calcular o percentual de faturamento de um estado
def calcular_percentual(dados, estado):
    estado = normalizar_estado(estado)
    total = sum(dados.values())
    if estado in dados:
        percentual = (dados[estado] / total) * 100
        print(f"\nPercentual de {estado}: {percentual:.2f}%\n")
    else:
        print("\nEstado não encontrado!\n")

# Função para corrigir o faturamento de um estado
def corrigir_faturamento(dados, estado, novo_valor):
    estado = normalizar_estado(estado)
    if estado in dados:
        dados[estado] = novo_valor
        salvar_dados(dados)
        print("\nFaturamento atualizado com sucesso!\n")
    else:
        print("\nEstado não encontrado!\n")

# Função principal do menu
def menu():
    dados = carregar_dados()

    while True:
        print("Menu de Opções:")
        print("1. Listar Faturamento")
        print("2. Calcular Faturamento")
        print("3. Corrigir Faturamento")
        print("4. Sair")

        opcao = input("Escolha uma opção: ")

        if opcao == '1':
            listar_faturamento(dados)
        elif opcao == '2':
            estado = input("Digite o estado (SP, RJ, MG, ES, Outros): ")
            calcular_percentual(dados, estado)
        elif opcao == '3':
            estado = input("Digite o estado (SP, RJ, MG, ES, Outros): ")
            try:
                novo_valor = float(input("Digite o novo valor de faturamento: "))
                corrigir_faturamento(dados, estado, novo_valor)
            except ValueError:
                print("\nValor inválido!\n")
        elif opcao == '4':
            print("Saindo do programa.")
            break
        else:
            print("\nOpção inválida!\n")

# Executa o menu
menu()
