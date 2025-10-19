# 🏦 Proyecto Prueba Técnica - IESS

Proyecto backend desarrollado con **Java 21** y **Quarkus**, conectado a una base de datos **Oracle XE** mediante **Docker Compose**.  
El sistema implementa entidades como *Cliente*, *Cuenta* y *Movimiento*, aplicando reglas de negocio financieras básicas.

---

## ⚙️ **Arquitectura del Proyecto**

El entorno está completamente dockerizado:

+----------------------+ +--------------------+
| Quarkus Backend | <-----> | Oracle XE DB |
| Java 21 + Swagger | | Usuario IESS_USER|
+----------------------+ +--------------------+


- **Backend:** Quarkus + RESTEasy + Panache (JPA)
- **Base de datos:** Oracle XE (Docker)
- **Build:** Maven
- **Documentación API:** Swagger UI

---

## 🚀 **Ejecución Rápida con Docker**

1️⃣ **Clonar el repositorio**
```bash
git clone https://github.com/diegolopez/prueba-tecnica-iess.git
cd prueba-tecnica-iess

2️⃣ Levantar los servicios
docker-compose up --build

Esto descargará y ejecutará:

Oracle XE 21c

El backend Quarkus (API REST)

🔹 Durante la primera ejecución, Docker creará automáticamente:

El usuario IESS_USER con contraseña IESS123

Las tablas cliente, cuenta y movimiento

🌐 Acceso a la aplicación
Recurso	URL
API Swagger UI	http://localhost:8085/q/swagger-ui

Oracle DB (SQL Developer)	localhost:1521/XEPDB1

Credenciales base de datos:

Usuario: IESS_USER
Contraseña: IESS123

📚 Estructura del proyecto

prueba-tecnica/
│
├── db/                        # Scripts SQL de inicialización
│   ├── init-user.sql
│   └── init-tables.sql
│
├── src/
│   ├── main/java/org/iess/    # Código fuente del backend
│   ├── main/resources/         # Configuración de Quarkus
│   └── test/java/org/iess/     # Pruebas unitarias
│
├── pom.xml                    # Dependencias Maven
├── Dockerfile                 # Imagen de aplicación
├── docker-compose.yml         # Orquestación de servicios
└── README.md

🧠 Reglas de negocio implementadas

Los valores de tipo crédito son positivos.

Los valores de tipo débito son negativos.

Cada movimiento actualiza automáticamente el saldo disponible.

Si el saldo es 0 y se intenta un débito, se lanza la excepción:

Saldo no disponible

🧰 Tecnologías Utilizadas

☕ Java 21

🧩 Quarkus Framework

🐘 Oracle Database XE 21c

🧱 JPA / Hibernate / Panache

🐋 Docker y Docker Compose

📘 Swagger UI (OpenAPI 3)

🧪 JUnit 5

🧑‍💻 Ejecución manual (sin Docker)

mvn quarkus:dev

API disponible en:
👉 http://localhost:8085/q/swagger-ui

👤 Autor

Diego López
💼 Analista informático
📧 contacto: diego.lopezo@iess.gob.ec



