# =============================
# Etapa 1: Build con Maven
# =============================
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# =============================
# Etapa 2: Runtime con Instant Client
# =============================
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Instalamos dependencias necesarias
RUN apt-get update && apt-get install -y unzip libaio-dev netcat-openbsd && rm -rf /var/lib/apt/lists/*

# Copiamos los ZIP locales de Oracle Instant Client (debes tenerlos en la misma carpeta que el Dockerfile)
COPY instantclient-basiclite-linux.x64-21.19.0.0.0dbru.zip /tmp/
COPY instantclient-sqlplus-linux.x64-21.19.0.0.0dbru.zip /tmp/

# Instalamos el Instant Client
RUN mkdir -p /opt/oracle && \
    cd /opt/oracle && \
    unzip -o /tmp/instantclient-basiclite-linux.x64-21.19.0.0.0dbru.zip && \
    unzip -o /tmp/instantclient-sqlplus-linux.x64-21.19.0.0.0dbru.zip && \
    rm -f /tmp/instantclient-*.zip && \
    echo /opt/oracle/instantclient_* > /etc/ld.so.conf.d/oracle-instantclient.conf && \
    ldconfig && \
    ln -s /opt/oracle/instantclient_21_19/sqlplus /usr/bin/sqlplus

# Copiamos los artefactos generados del build
COPY --from=build /build/target/quarkus-app/ /app/

CMD ["java", "-jar", "/app/quarkus-run.jar"]
