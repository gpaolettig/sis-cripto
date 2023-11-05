# sis-cripto
API REST para la gestión de intercambio de criptodivisas, cuenta tanto con operaciones CRUD de usuarios, carteras, criptodivisas y otras como intercambio de criptodivisas entre usuarios y seguimiento de saldo.
Programado en Java y Spring Boot. Se ha utilizado Hibernate como ORM para el mapeo de objetos - relacional.
Se aplicaron patrones de diseño como DAO, DTO, Inyección de dependencias.

---
## Índice
- [Instalacion](#Instalación)

## Instalación

### Configuración y ejecución de la aplicación
Para configurar, instalar y ejecutar la aplicación seguí estos pasos. Debes tener Java 17 y MySQL instalados.

### Cloná el repositorio
Primero, cloná este repositorio en tu máquina local usando el siguiente comando en tu terminal:
git clone https://github.com/gipage/sis-cripto.git

### Abrí el Proyecto en tu Entorno de Desarrollo (IDE)
Abrí tu entorno de desarrollo (IntelliJ IDEA, NetBeans, Eclipse, Spring Tool Suite) y seleccioná "Open Project" (Abrir Proyecto) o su equivalente. Navegá hasta la carpeta del proyecto que acabas de clonar y ábrilo.

### Configurá la base de datos
En el archivo application.properties, que se encuentra en la carpeta de recursos del proyecto (src/main/resources/application.properties), realizá los siguientes cambios:

#configuracion bd
spring.datasource.username= tu-nombre-de-usuario
spring.datasource.password= tu-contraseña
spring.datasource.url=jdbc:mysql://localhost/nombre-de-tu-bd?useUnicode=true&characterEncoding=UTF-8

### Creá la Base de Datos
Abrí tu cliente de MySQL y crea la base de datos con el nombre que especificaste en la URL anterior. Utilizá el script de creación de base de datos que se proporciona .....

### Ejecutá la Aplicación
Una vez que hayas configurado la base de datos y guardado los cambios en application.properties, podes ejecutar la aplicación. Busca la clase principal "SisCriptoApplication" (etiquetada como @SpringBootApplication) y dale al botón run (ejecutar) de tu entorno de desarrollo.

### Accede a la Aplicación
A través de postman podes probar los diferentes endpoints de la API. 

