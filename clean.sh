#!/bin/bash

# Função para deletar todos os containers
delete_containers() {
  echo "Deletando todos os containers..."
  docker rm -f $(docker ps -a -q)
}

# Função para deletar todas as imagens
delete_images() {
  echo "Deletando todas as imagens..."
  docker rmi -f $(docker images -q)
}

# Função para deletar todos os volumes
delete_volumes() {
  echo "Deletando todos os volumes..."
  docker volume rm $(docker volume ls -q)
}

# Função para deletar todos os builds
delete_builds() {
  echo "Deletando todos os builds..."
  sudo docker builder prune --all --force
}

# Chamar funções
delete_containers
delete_images
delete_volumes
delete_builds

echo "Cleanup completo!"