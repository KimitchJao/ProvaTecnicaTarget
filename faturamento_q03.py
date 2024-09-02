import json
import os
import locale

# Configura o locale para o Brasil
locale.setlocale(locale.LC_ALL, 'pt_BR.UTF-8')

def formatar_moeda(valor):
    return locale.currency(valor, grouping=True)

def criar_arquivo_json(nome_arquivo):
    with open(nome_arquivo, 'w') as arquivo:
        json.dump({}, arquivo)

def adicionar_faturamento(nome_arquivo):
    try:
        with open(nome_arquivo, 'r') as arquivo:
            faturamento = json.load(arquivo)
    except FileNotFoundError:
        faturamento = {}

    while True:
        dia = input("Digite o dia (ou 0 para finalizar): ")
        if dia == '0':
            break
        valor = float(input(f"Digite o faturamento para o dia {dia}: "))
        faturamento[dia] = valor

    with open(nome_arquivo, 'w') as arquivo:
        json.dump(faturamento, arquivo)
    print("Dados adicionados com sucesso.")

def corrigir_faturamento(nome_arquivo):
    try:
        with open(nome_arquivo, 'r') as arquivo:
            faturamento = json.load(arquivo)
    except FileNotFoundError:
        print("Arquivo não encontrado. Nenhum dado para corrigir.")
        return

    dia = input("Digite o dia do faturamento a ser corrigido: ")
    if dia in faturamento:
        valor = float(input(f"Digite o novo faturamento para o dia {dia}: "))
        faturamento[dia] = valor
        with open(nome_arquivo, 'w') as arquivo:
            json.dump(faturamento, arquivo)
        print("Dados corrigidos com sucesso.")
    else:
        print("Dia não encontrado.")

def listar_faturamento(nome_arquivo):
    try:
        with open(nome_arquivo, 'r') as arquivo:
            faturamento = json.load(arquivo)
    except FileNotFoundError:
        print("Arquivo não encontrado. Nenhum dado para listar.")
        return

    if not faturamento:
        print("Nenhum dado para exibir.")
        return

    print("Faturamento diário:")
    valores = []
    for dia, valor in faturamento.items():
        print(f"Dia: {dia}, Faturamento: {formatar_moeda(valor)}")
        valores.append(valor)

    # Calcular menor e maior valor de faturamento e média mensal
    menor_valor = min(valores)
    maior_valor = max(valores)
    media_mensal = sum(valores) / len(valores)
    dias_acima_media = len([v for v in valores if v > media_mensal])

    print("\nMenor valor de faturamento em um dia do mês:", formatar_moeda(menor_valor))
    print("Maior valor de faturamento em um dia do mês:", formatar_moeda(maior_valor))
    print("Número de dias com faturamento acima da média mensal:", dias_acima_media)

def apagar_dados(nome_arquivo):
    if os.path.exists(nome_arquivo):
        os.remove(nome_arquivo)
        print("Dados apagados com sucesso.")
    else:
        print("Arquivo não encontrado.")

def main():
    nome_arquivo = "faturamento_q03.json"  # Novo nome de arquivo
    criar_arquivo_json(nome_arquivo)

    while True:
        print("""\nMenu:
        1. Adicionar Faturamento
        2. Corrigir Faturamento")
        3. Listar Faturamento
        4. Apagar Dados
        5. Sair""")

        opcao = input("Escolha uma opção: ")

        if opcao == '1':
            adicionar_faturamento(nome_arquivo)
        elif opcao == '2':
            corrigir_faturamento(nome_arquivo)
        elif opcao == '3':
            listar_faturamento(nome_arquivo)
        elif opcao == '4':
            apagar_dados(nome_arquivo)
        elif opcao == '5':
            break
        else:
            print("Opção inválida. Tente novamente.")

if __name__ == "__main__":
    main()
