#!/bin/bash

# Function to delete all containers
delete_containers() {
  echo "Deleting all containers..."
  docker rm -f $(docker ps -a -q)
}

# Function to delete all images
delete_images() {
  echo "Deleting all images..."
  docker rmi -f $(docker images -q)
}

# Function to delete all volumes
delete_volumes() {
  echo "Deleting all volumes..."
  docker volume rm $(docker volume ls -q)
}

# Function to delete all builds
delete_builds() {
  echo "Deleting all builds..."
  sudo docker builder prune --all --force
}

# Call functions
delete_containers
delete_images
delete_volumes
delete_builds

echo "Cleanup complete!"
