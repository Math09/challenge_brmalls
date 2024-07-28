#!/bin/bash

# Build of the new image.
docker build -t java_api .

echo
echo ---
echo

# Stop existing container
echo stopping container..
docker stop api

echo

# Remove existing container.
echo removing container..
docker rm api

echo
echo ---
echo

# Run the new container.
docker run -d --name api -e DEBUG=true -p 5005:5005 -p 8080:8080 java_api
