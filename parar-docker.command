#!/bin/bash

# Navega para o diretório onde este arquivo está localizado
cd "$(dirname "$0")"

echo "========================================="
echo "   PARANDO O SISTEMA B30..."
echo "========================================="

docker compose down

echo ""
echo "✅ Sistema parado com sucesso."
echo "Pode fechar esta janela."
sleep 3
