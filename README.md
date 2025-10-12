# clientes-cuentas-microservicio

Microservicio para la gestión de clientes y cuentas bancarias desarrollado en **Java 21** con **Spring Boot 3.3.1**.  
La persistencia se realiza sobre una base de datos **H2 en memoria**, con mapeo de entidades mediante **MapStruct**  
y generación automática de controladores y DTOs a partir de un contrato **OpenAPI** mediante el plugin  
**openapi-generator-maven**.

El microservicio se levanta en el siguiente context-path: **/clientes/cuentas/microservicio**. 
URL de microservicio: http://localhost:8080/clientes/cuentas/microservicio

## Funcionalidades
El microservicio presenta las siguientes funcionalidades:
- Servicio **/clientes**: Listado de los clientes existentes y las cuentas asociadas.
- Servicio **/clientes/mayores-de-edad**: Listado de los clientes mayores de edad y las cuentas asociadas.
- Servicio **/clientes/con-cuenta-superior-a/{cantidad}**: Listado de los clientes cuyo saldo total en sus cuentas sea superior a una cantidad dada.
- Servicio **/clientes/{dni}**: Obtencion de un cliente y sus cuentas asociadas en base a un DNI dado.
- Servicio **/cuentas**: Creacion de nuevas cuentas asociandolas a clientes ya existentes como nuevos clientes en caso de que el 
indicado no exista previamente.
- Servicio **/cuentas/{idCuenta}**: Actualizacion del saldo de una cuenta en base a el identificador indicado.

## Stack Tecnológico

| Componente     | Versión  | Descripción                        |
|----------------|-----------|------------------------------------|
| **Java**       | 21        | Lenguaje base                     |
| **Spring Boot**| 3.3.1     | Framework principal                |
| **MapStruct**  | 1.6.3      | Mapeo de entidades y DTOs          |
| **Lombok**     | 1.18.30   | Simplificación de boilerplate      |
| **H2 Database**| 2.2.224       | Base de datos en memoria           |
| **JUnit 5**    | 5.10.0      | Testing unitario                   |
| **Maven**      | 3.9       | Build & gestión de dependencias    |

## OpenAPI / Swagger

El microservicio expone su especificación OpenAPI y una interfaz Swagger UI para probar los endpoints.  
Disponible en:  
👉[http://localhost:8080/clientes/cuentas/microservicio/swagger-ui/index.html](http://localhost:8080/clientes/cuentas/microservicio/swagger-ui/index.html)

*(Es necesario tener el microservicio en ejecución para acceder a esta URL).*

## Ejecución del proyecto

### Clonación repositorio
El proyecto se encuentra en el siguiente repositorio de GitHub: 
https://github.com/javierGomez18/clientes-cuentas-microservicio.git (Rama: develop). Previamente es necesario clonar el 
repositorio via integracion con un IDE o por comandos de Git.

    git clone https://github.com/javierGomez18/clientes-cuentas-microservicio.git

### Arrancar proyecto

#### Arranque con Intellij
Una vez importado el proyecto en Intellij, es necesario definir una Configuración de Arranque (Run/debug Configurtion).
Hacemos click en la opcion Run -> "Edit Configuration". En la ventana que aparecera hacemos click en "+" y añadimos una 
configuración de tipo "Application" y rellenamos la siguiente informacion: 

- Name: el nombre que queramos que tenga la configuración.
- JDK/JRE: un JDK o JRE Java 21+
- Module: indicamos que arranque desde el modulo clientes-cuentas-microservicio-infrastructure 
(-cp clientes-cuentas-microservicio-infrastructure).
- Main class: ClientesCuentasMicroservicioApplication
- Working directory: el directorio en el hayamos clonado el repositorio.

Una vez configurado hacemos click en el botón de Run.

## Baterías de pruebas
Se incluye una colección de **Postman** en formato JSON en el directorio `/postman`,  
con ejemplos de peticiones a todos los endpoints del microservicio.

Para importarla:
1. Abrir Postman -> File -> *Import*.
2. Seleccionar el archivo `clientes-cuentas-microservicio.postman_collection.json`.
3. Asegurarse de que el microservicio está corriendo en `http://localhost:8080`.

## Test Automáticos
Los tests unitarios definidos en el proyecto se ejecutan automáticamente durante las fases estándar de Maven:

```bash
    mvn clean test          # Ejecuta únicamente los tests
    mvn clean verify        # Ejecuta tests y verifica el build
    mvn clean package       # Compila, ejecuta tests y empaqueta el artefacto final