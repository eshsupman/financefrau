#!/bin/bash

set -e

echo "Сборка unit тестов и jar"
docker run --rm -v "$PWD":/app -w /app gradle:8.2.1-jdk17 gradle clean build test --no-daemon

echo "Запуск docker-compose"
docker-compose up --build