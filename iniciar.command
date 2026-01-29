#!/bin/bash

# Navega para o diretório onde este arquivo está localizado
cd "$(dirname "$0")"

echo "========================================="
echo "   INICIANDO O SISTEMA B30..."
echo "========================================="

# Verifica se o Docker está rodando
if ! docker info > /dev/null 2>&1; then
  echo "ERRO: O Docker não parece estar rodando."
  echo "Por favor, abra o aplicativo 'Docker Desktop' primeiro."
  exit 1
fi

# Sobe os containers (sem forçar build toda vez para ser mais rápido no dia a dia)
docker compose up -d

echo ""
echo "✅ Sistema iniciado!"
echo "⏳ Aguardando 15 segundos para o banco de dados e a aplicação subirem..."
sleep 15

# Abre o navegador padrão
echo "🚀 Abrindo o sistema no navegador..."
open http://localhost:8080

# Mantém a janela aberta caso haja erro, ou fecha se der tudo certo (opcional)
# echo "Pressione qualquer tecla para fechar esta janela..."
# read -n 1
