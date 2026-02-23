#!/bin/bash

cd "$(dirname "$0")"

echo "========================================="
echo "   INICIANDO O SISTEMA B30 (LOCAL)..."
echo "========================================="

if ! command -v java &> /dev/null; then
    echo "ERRO: Java não encontrado."
    echo "Por favor, instale o JDK 17 ou superior."
    exit 1
fi

echo "🚀 Compilando e iniciando a aplicação..."


./mvnw spring-boot:run &

sleep 15
open http://localhost:8080
