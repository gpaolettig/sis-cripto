# sis-cripto
API REST para la gestión de intercambio de criptodivisas, cuenta tanto con operaciones CRUD de usuarios, carteras, criptodivisas y otras como intercambio de criptodivisas entre usuarios y seguimiento de saldo.
Programado en Java y Spring Boot. Se ha utilizado Hibernate como ORM para el mapeo de objetos - relacional.
Se aplicaron patrones de diseño como DAO, DTO, Inyección de dependencias.

---
## Índice
- [Instalacion](#Instalación)
- [Endpoints API](#endpoints-api)
	- [User](#user)
	- [Create User](#create-user)
	- [Get User](#get-user)
	- [Get All](#get-all)
	- [Update User](#update-user)
   	- [Delete User](#delete-user)

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
Abrí tu cliente de MySQL y crea la base de datos con el nombre que especificaste en la URL anterior. Utilizá el script de creación de base de datos que se proporciona en 
scripts/creacion_bd.sql
### Realizá una carga de datos de prueba
Abrí tu cliente de MySQL y abrí el archivo que se encuentra en scripts/datos_prueba_bd.sql seleccioná la base de datos y ejecutá el archivo.

### Ejecutá la Aplicación
Una vez que hayas configurado la base de datos y guardado los cambios en application.properties, podes ejecutar la aplicación. Busca la clase principal "SisCriptoApplication" (etiquetada como @SpringBootApplication) y dale al botón run (ejecutar) de tu entorno de desarrollo.

### Accede a la Aplicación
A través de Postman podes probar los diferentes endpoints de la API. 
Abrí Postman y cargá en tu workspace la colleción que se encuentra en scripts/API REST- Cripto.postman_collection.json
Por último proba los diferentes endpoints siguiendo las instrucciones en la misma docu.

## Endpoints API
### User
Operaciones Create, Read, Update, Delete de un usuario.
#### Create User
```http
  POST localhost:8080/api/v1/usuarios
```
| Parámetro | Tipo     | Descripción              | Ejemplo|
| :-------- | :------- | :------------------------- | :------------------------- |
| dni| `String` | **Requerido** por body.  |44913425
| name| `String` | **Requerido** por body.  |Juan
| surname| `String` | **Requerido** por body.  |Pérez
| gender| `String` | **Requerido** por body.  | Masculino
| email| `String` | **Requerido** por body.  | juan@gmail.com
| tel| `String` |                            | 2664123456
- URL: localhost:8080/api/v1/usuarios
- Método: POST
- Respuesta:  
	201 - CREATED: dni,name,surname,gender,email,tel
  
  409 - CONFLICT: El usuario a dar de alta ya existe. + Excepción personalizada (UserAlreadyExist)
> [!NOTE]
> Al crear un usuario, se creará una billetera asociada a él.

Ejemplo en POSTMAN

<img width="470" alt="image" src="https://github.com/gipage/sis-cripto/assets/83784311/a0799e70-080e-4f47-a715-86fc9aad7d7f">

#### Get User
```http
  GET localhost:8080/api/v1/usuarios/{dni}
```
| Parámetro | Tipo     | Descripción              | Ejemplo|
| :-------- | :------- | :------------------------- | :------------------------- |
| dni| `String` | **Requerido** por url.  |44913425

- URL: localhost:8080/api/v1/usuarios/{id}
- Método: GET
- Respuesta:  
	200 - OK: dni,name,surname,gender,email,tel (UserDTO)
  404 - NOT FOUND: El usuario a buscar no existe + Excepción personalizada (UserDoesNotExist)

Ejemplo en POSTMAN

<img width="511" alt="image" src="https://github.com/gipage/sis-cripto/assets/83784311/76f1f490-4dac-4738-b2bd-80af640a6ed2">

#### Get All
```http
  GET localhost:8080/api/v1/usuarios
```
| Parámetro | Tipo     | Descripción              | Ejemplo|
| :-------- | :------- | :------------------------- | :------------------------- |
| |  | No se requieren parametros.  |

- URL: localhost:8080/api/v1/usuarios
- Método: GET
- Respuesta: 
	200 - OK: Array JSON con objetos UserDTO. De no existir usuarios, se retorna un array vacío. 

Ejemplo en POSTMAN

<img width="515" alt="image" src="https://github.com/gipage/sis-cripto/assets/83784311/e8eca345-f86f-4cc8-bc63-46302972f17d">

#### Update User
```http
  PUT localhost:8080/api/v1/usuarios/{dni}
```
| Parámetro | Tipo     | Descripción              | Ejemplo|
| :-------- | :------- | :------------------------- | :------------------------- |
| dni| `String` | **Requerido** por url.  |44913425
| name| `String` | **Requerido** por body.  |Juan
| surname| `String` | **Requerido** por body.  |Pérez
| gender| `String` | **Requerido** por body.  | Masculino
| email| `String` | **Requerido** por body.  | juan@gmail.com
| tel| `String` |  **Requerido** por body.   | 2664123456
- URL: localhost:8080/api/v1/usuarios{dni}
- Método: PUT
- Respuesta:  
	200 - OK: dni,name,surname,gender,email,tel (UserDTO)
  
  404 - NOT FOUND: El usuario a actualizar no existe. + Excepción personalizada (UserDoesNotExist)

Ejemplo en POSTMAN 

<img width="514" alt="image" src="https://github.com/gipage/sis-cripto/assets/83784311/8b99a6e1-99e0-4900-9456-34fa78d6de25">

#### Delete User
```http
  DELETE localhost:8080/api/v1/usuarios/{dni}
```
| Parámetro | Tipo     | Descripción              | Ejemplo|
| :-------- | :------- | :------------------------- | :------------------------- |
|dni  | `String`| **Requerido** por url.  | 44913425

- URL: localhost:8080/api/v1/usuarios/{dni}
- Método: DELETE
- Respuesta:

	200 - OK: dni,name,surname,gender,email,tel (UserDTO)

  404 - NOT FOUND: El usuario a actualizar no existe. + Excepción personalizada (UserDoesNotExist)
  

Ejemplo en POSTMAN

<img width="511" alt="image" src="https://github.com/gipage/sis-cripto/assets/83784311/8d0c67a1-9513-495b-be1a-c53a487a925a">

### Wallet






