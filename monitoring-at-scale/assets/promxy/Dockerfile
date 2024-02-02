FROM ubuntu:24.04

WORKDIR /app

RUN apt update && apt install curl -y && \
    curl -Lk 'https://github.com/jacksontj/promxy/releases/download/v0.0.84/promxy-v0.0.84-linux-amd64' -o /usr/bin/promxy && \
    chmod +x /usr/bin/promxy

CMD ["promxy", "--config=./promxy.yaml"]