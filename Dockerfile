# Usa una imagen base con Java 17 (puedes ajustar según tus necesidades)
FROM adoptopenjdk:17-jre-hotspot

# Copia el archivo WAR a la imagen
COPY target/login-0.0.1-SNAPSHOT.war /app/login-0.0.1-SNAPSHOT.war

# Establece el directorio de trabajo
WORKDIR /app

# Comando para ejecutar la aplicación (ajusta según tu aplicación)
RUN mvn install

# Crea nueva imagen
FROM openjdk:17-jre-hotspot

# Exponwer el puerto
EXPOSE 9091

COPY --from=build /app/build/libs/login-0.0.1-SNAPSHOT.war /app/login-0.0.1-SNAPSHOT.war

ENTRYPOINT["java","-war","/app/login-0.0.1-SNAPSHOT.war"]