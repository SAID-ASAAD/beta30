#!/bin/bash

echo "========================================="
echo "   PARANDO O SISTEMA B30 (LOCAL)..."
echo "========================================="

if command -v jps &> /dev/null; then
    pids=$(jps -l | grep "B30Application" | cut -d " " -f 1)
    if [ -n "$pids" ]; then
        echo "Encontrado via jps. Matando PID: $pids"
        kill -9 $pids
        echo "✅ Aplicação parada."
        exit 0
    fi
fi

echo "Tentando parar via pkill..."
pkill -f "B30Application" || pkill -f "spring-boot:run"

echo "✅ Comandos de parada enviados."
sleep 3
