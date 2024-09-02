def inverter_string(texto):
    # Inicializa a string invertida vazia
    invertida = ""
    
    # Percorre cada caractere da string original de trás para frente
    for i in range(len(texto) - 1, -1, -1):
        invertida += texto[i]
    
    return invertida

# Solicita a entrada do usuário
texto_original = input("Digite a string que deseja inverter: ")
resultado = inverter_string(texto_original)

print(f"String original: {texto_original}")
print(f"String invertida: {resultado}")
