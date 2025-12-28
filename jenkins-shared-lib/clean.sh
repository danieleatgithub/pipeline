#!/bin/bash

echo "🧹 Avvio pulizia completa dell'ambiente Docker..."

echo ""
echo "🔍 1) Rimozione container fermi..."
docker rm -f $(docker ps -aq) 2>/dev/null || echo "✔ Nessun container da rimuovere."

echo ""
echo "🔍 2) Rimozione immagini tranne 'hello-world'..."
IMMAGINI=$(docker images --format "{{.Repository}}:{{.Tag}}" | grep -v "^hello-world")

if [ -z "$IMMAGINI" ]; then
    echo "✔ Nessuna immagine da rimuovere."
else
    echo "$IMMAGINI" | xargs -r docker rmi -f
fi

echo ""
echo "🔍 3) Rimozione volumi inutilizzati..."
docker volume prune -f

echo ""
echo "🔍 4) Rimozione network inutilizzate..."
docker network prune -f

echo ""
echo "🔍 5) Pulizia cache build Docker..."
docker builder prune -f

echo ""
echo "✨ Pulizia completata!"

