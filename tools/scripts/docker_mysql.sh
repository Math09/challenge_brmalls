#!/bin/bash

# Define the image and container names
IMAGE_NAME="mysql"
CONTAINER_NAME="database"

# Function to check if the container is running
function container_exists() {
  docker ps -a --format '{{.Names}}' | grep -Eq "^${CONTAINER_NAME}\$"
}

# Function to check if the image exists
function image_exists() {
  docker images --format '{{.Repository}}' | grep -Eq "^${IMAGE_NAME}\$"
}

echo
echo "Building new image: ${IMAGE_NAME}"
echo
docker build -t ${IMAGE_NAME} . || { echo "Failed to build the Docker image"; exit 1; }

echo
echo "---"
echo

# Stop and remove the existing container, if it is running
if container_exists; then
  echo "Stopping container: ${CONTAINER_NAME}..."
  docker stop ${CONTAINER_NAME}

  echo
  echo "Removing container: ${CONTAINER_NAME}..."
  docker rm ${CONTAINER_NAME}
else
  echo "No existing container named '${CONTAINER_NAME}' found."
fi

echo
echo "---"
echo

# Remove the old Docker image, if it exists
if image_exists; then
  echo "Removing old image: ${IMAGE_NAME}..."
  docker rmi ${IMAGE_NAME} || { echo "Failed to remove the Docker image"; exit 1; }
else
  echo "No existing image named '${IMAGE_NAME}' found."
fi

echo
echo "---"
echo

# Build image
docker build -f Dockerfile -t ${IMAGE_NAME} .

# Run the new container
docker run -d --name ${CONTAINER_NAME} -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=testdb -p 3306:3306 ${IMAGE_NAME} || { echo "Failed to run the Docker container"; exit 1; }

echo
echo "Container ${CONTAINER_NAME} is up and running."
