# Paso 1: Usamos una imagen de Java (OpenJDK)
FROM eclipse-temurin:21-jdk-jammy

# Paso 2: Creamos una carpeta dentro del contenedor para nuestra app
WORKDIR /app

# Paso 3: Copiamos el archivo .jar que genera Maven/IntelliJ
# Asegúrate de que el nombre coincida con el de tu carpeta target
# Cambia la línea original por esta:
COPY target/*.jar app.jar

# Paso 4: El comando para arrancar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]