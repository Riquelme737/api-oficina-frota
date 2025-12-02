# 1. Usa uma imagem base com Java 21
FROM eclipse-temurin:21-jdk-alpine

# 2. Define o diretório de trabalho dentro do container
WORKDIR /app

# 3. Cria um volume para arquivos temporários (opcional, mas boa prática pro Tomcat)
VOLUME /tmp

# 4. Copia o arquivo .jar gerado pelo Maven para dentro do container
# IMPORTANTE: O nome do jar pode variar, verifique na pasta target/
COPY target/*.jar app.jar

# 5. Expõe a porta 8080
EXPOSE 8080

# 6. Comando para rodar a aplicação
ENTRYPOINT ["java","-jar","/app/app.jar"]