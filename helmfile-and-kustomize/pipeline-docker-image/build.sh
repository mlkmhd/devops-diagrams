#!/bin/bash

IMAGE_NAME="mlkmhd/cd-pipeline:1"

docker build . --no-cache -t $IMAGE_NAME
docker push $IMAGE_NAME
