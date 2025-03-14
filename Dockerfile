# Usa a imagem oficial do Redis
FROM redis:latest

# Expõe a porta padrão do Redis
EXPOSE 6379

# Inicia o Redis
CMD ["redis-server"]
