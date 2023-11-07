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
	- [Wallet](#wallet)
   		- [Create Wallet](#create-wallet)
		- [Get Wallet](#get-wallet)
		- [Get All](#get-user-wallet)
		- [Update User](#update-wallet)
   		- [Delete User](#delete-wallet)
		- [Get Balance](#get-balance)
		- [Get All balance](#get-all-balance)
	- [Currency](#currency)
		- [Create Currency](#create-currency)
		- [Update Currency](#update-currency)
		- [Delete Currency](#delete-currency)
  	- [Transaction](#transaction)
		- [Create Transaction](#create-transaction)
			- [Intercambio](#1.-intercambio)
			- [Deposito](#2.-deposito)
       

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
Operaciones Create, Read, Update, Delete, GetBalance, GetAllBalance de una billetera.

#### Create Wallet
```http
  POST localhost:8080/api/v1/wallets
```
| Parámetro | Tipo     | Descripción              | Ejemplo|
| :-------- | :------- | :------------------------- | :------------------------- |
| dni| `String` | **Requerido** por body.  |44913425
| balance| `BigDecimal` | **Requerido** por body.  |6500.00

- URL: localhost:8080/api/v1/wallets
- Método: POST
- Respuesta:

  201 - CREATED: id, dni, balance. (WalletDTO)
  
  404 - NOT FOUND: El usuario asociado a la billetera no existe + Excepción personalizada (UserDoesNotExist)
> [!NOTE]
> Al crear una billetera, el id de la misma se generará automaticamente. El tipo es UUID. 

Ejemplo en POSTMAN

<img width="517" alt="image" src="https://github.com/gipage/sis-cripto/assets/83784311/81c84575-428c-40c9-882f-a3b9ebb3280e">

#### Get Wallet
```http
  GET localhost:8080/api/v1/wallets/{id}
```
| Parámetro | Tipo     | Descripción              | Ejemplo|
| :-------- | :------- | :------------------------- | :------------------------- |
| id| `UUID` | **Requerido** por url.  |c0a80067-8ba6-169e-818b-a6d684c70003


- URL: localhost:8080/api/v1/wallets/{id}
- Método: GET
- Respuesta:

  200 - OK: id, dni, balance. (WalletDTO)
  
  404 - NOT FOUND: La billetera a buscar no existe + Excepción personalizada (WalletDoesNotExist)


Ejemplo en POSTMAN

<img width="518" alt="image" src="https://github.com/gipage/sis-cripto/assets/83784311/84acac41-4552-4d11-84f4-3c8f3dcf5bf7">

#### Get User Wallets
```http
  GET localhost:8080/api/v1/wallets/user/{dni}
```
| Parámetro | Tipo     | Descripción              | Ejemplo|
| :-------- | :------- | :------------------------- | :------------------------- |
| dni| `String` | **Requerido** por url.  |44913425


- URL: localhost:8080/api/v1/wallets/user/{dni}
- Método: GET
- Respuesta:

  200 - OK: Array JSON con objetos WalletDTO o vacío.
  
  404 - NOT FOUND: El usuario asociado a la billetera no existe + Excepción personalizada (UserDoesNotExist)


Ejemplo en POSTMAN

<img width="511" alt="image" src="https://github.com/gipage/sis-cripto/assets/83784311/8b0efb8e-cb21-46f4-8976-0e233dfc2fdb">

#### Update Wallet
```http
  PUT localhost:8080/api/v1/wallets/{id}
```
| Parámetro | Tipo     | Descripción              | Ejemplo|
| :-------- | :------- | :------------------------- | :------------------------- |
| id| `UUID` | **Requerido** por url.  |c0a80067-8ba6-169e-818b-a6d684c70003
| dni| `String` | **Requerido** por body.  |44913425
| balance| `BigDecimal` | **Requerido** por body.  |4000.00


- URL: localhost:8080/api/v1/wallets/{id}
- Método: PUT
- Respuesta:

  200 - OK: id, dni, balance. (WalletDTO)
  
  404 - NOT FOUND: La billetera a actualizar no existe + Excepción personalizada (WalletDoesNotExist)


Ejemplo en POSTMAN

<img width="509" alt="image" src="https://github.com/gipage/sis-cripto/assets/83784311/b09524ce-6aad-4262-addb-dd306f1a1045">

#### Delete Wallet
```http
  DELETE localhost:8080/api/v1/wallets/{id}
```
| Parámetro | Tipo     | Descripción              | Ejemplo|
| :-------- | :------- | :------------------------- | :------------------------- |
| id| `UUID` | **Requerido** por url.  |c0a80067-8ba6-169e-818b-a6d684c70003


- URL: localhost:8080/api/v1/wallets/{id}
- Método: DELETE
- Respuesta:

  200 - OK: id, dni, balance. (WalletDTO)
  
  404 - NOT FOUND: La billetera a eliminar no existe + Excepción personalizada (WalletDoesNotExist)


Ejemplo en POSTMAN

<img width="512" alt="image" src="https://github.com/gipage/sis-cripto/assets/83784311/9e77947b-0fd4-4857-9816-09c67750419c">

#### Get Balance
```http
  GET localhost:8080/api/v1/wallets/balance/{id}
```
| Parámetro | Tipo     | Descripción              | Ejemplo|
| :-------- | :------- | :------------------------- | :------------------------- |
| id| `UUID` | **Requerido** por url.  |c0a80067-8ba6-169e-818b-a6d53ea40001


- URL: localhost:8080/api/v1/wallets/balance/{id}
- Método: GET
- Respuesta:

  200 - OK: balance. 
  
  404 - NOT FOUND: La billetera a consultar su saldo no existe + Excepción personalizada (WalletDoesNotExist)


Ejemplo en POSTMAN

<img width="512" alt="image" src="https://github.com/gipage/sis-cripto/assets/83784311/592bd9eb-f743-4347-a23d-44aad84b8e68">

#### Get All Balance
```http
  GET localhost:8080/api/v1/wallets/user/balance/{dni}
```
| Parámetro | Tipo     | Descripción              | Ejemplo|
| :-------- | :------- | :------------------------- | :------------------------- |
| dni| `String` | **Requerido** por url.  |44913425

- URL: localhost:8080/api/v1/wallets/user/balance/{dni}
- Método: GET
- Respuesta:

  200 - OK: balance. 
  
  404 - NOT FOUND: El usuario asociado a la billetera no existe + Excepción personalizada (UserDoesNotExist)


Ejemplo en POSTMAN

<img width="518" alt="image" src="https://github.com/gipage/sis-cripto/assets/83784311/b4da49c3-72d8-414c-b9f9-a3247010b587">

### Currency
Operaciones Create, Update y Delete de una criptodivisa.

#### Create Currency
```http
  POST localhost:8080/api/v1/currencies
```
| Parámetro | Tipo     | Descripción              | Ejemplo|
| :-------- | :------- | :------------------------- | :------------------------- |
| ticker| `String` | **Requerido** por body.  |BTC
| name| `String` | **Requerido** por body.  |Bitcoin
| value| `BigDecimal` | **Requerido** por body.  |100.00

- URL: localhost:8080/api/v1/currencies
- Método: POST
- Respuesta:

  201 - CREATED: ticker, name, value. (CurrencyDTO)
  
  409 - CONFLICT: La criptodivisa ya existe + Excepcion personalizada (CurrencyAlreadyExist)
  
> [!NOTE]
> La divisa ARS está presente en la base de datos con valor igual a 1, el valor de todas las criptodivisas es en ARS

Ejemplo en POSTMAN

<img width="509" alt="image" src="https://github.com/gipage/sis-cripto/assets/83784311/fa1fe6cd-b7b0-44b0-9fbc-6febc6072e0b">

#### Update Currency
```http
  PUT localhost:8080/api/v1/currencies/{ticker}
```
| Parámetro | Tipo     | Descripción              | Ejemplo|
| :-------- | :------- | :------------------------- | :------------------------- |
| ticker| `String` | **Requerido** por url.  |BTC
| ticker| `String` | **Requerido** por body.  |BTC2
| name| `String` | **Requerido** por body.  |Bitcoin2
| value| `BigDecimal` | **Requerido** por body.  |200.00



- URL: localhost:8080/api/v1/currencies/{ticker}
- Método: PUT
- Respuesta:

  200 - OK: ticker, name, value. (CurrencyDTO)
  
  404 - NOT FOUND: La criptodivisa a actualizar no existe + Excepcion personalizada (CurrencyDoesNotExist)


Ejemplo en POSTMAN

<img width="517" alt="image" src="https://github.com/gipage/sis-cripto/assets/83784311/059af39f-6cee-4cd7-b813-77f496188ce4">

#### Delete Currency
```http
  DELETE localhost:8080/api/v1/currencies/{ticker}
```
| Parámetro | Tipo     | Descripción              | Ejemplo|
| :-------- | :------- | :------------------------- | :------------------------- |
| ticker| `String` | **Requerido** por url.  |BTC




- URL: localhost:8080/api/v1/currencies/{ticker}
- Método: DELETE
- Respuesta:

  200 - OK: ticker, name, value. (CurrencyDTO)
  
  404 - NOT FOUND: La criptodivisa a eliminar no existe + Excepcion personalizada (CurrencyDoesNotExist)


Ejemplo en POSTMAN

<img width="509" alt="image" src="https://github.com/gipage/sis-cripto/assets/83784311/6584f784-cb41-4fc0-acab-ee5afbcdbd3c">

### Transaction
Operaciones: Depósito de criptodivisa e intercambio de criptodivisas.

#### Create Transaction
```http
  POST localhost:8080/api/v1/transactions
```
##### 1. Intercambio
   
| Parámetro | Tipo     | Descripción              | Ejemplo|
| :-------- | :------- | :------------------------- | :------------------------- |
| type| `String` | **Requerido** por body.  |Intercambio
| origin_currency_ticker| `String` | **Requerido** por body.  |BTC
| destination_currency_ticker| `String` | **Requerido** por body.  |ETH
| origin_wallet_id| `UUID` | **Requerido** por body.  | c0a80067-8ba6-169e-818b-a6a6b11e0000
| destination_wallet_id| `UUID` | **Requerido** por body.  | c0a80067-8ba6-169e-818b-a6d53ea40001
| origin_amount | `BigDecimal` | **Requerido** por body. | 1

- URL: localhost:8080/api/v1/transactions
- Método: POST
- Respuesta:
  
  200 - OK: idtransaction, date_transaction, type, origen_wallet_id, destination_wallet_id (TransactionSuccesfullyDTO).
  varias excepciones personalizadas:
	- CurrencyDoesNotExist: Una criptodivisa a intercambiar/depositar no existe.
	- WalletDoesNotExist: Una billetera no existe.
	- HoldingDoesNotExist: Una billetera no posee tenencias de cripto.
	- NotEnoughFunds: Una billetera no posee los sufiencientes fondos para realizar un intercambio.
Ejemplo en Postman

Billetera de origen:

<img width="311" alt="image" src="https://github.com/gipage/sis-cripto/assets/83784311/1da04565-9adf-47db-a9db-33ae448ec108">

Billetera de destino:

<img width="281" alt="image" src="https://github.com/gipage/sis-cripto/assets/83784311/d5cebe0a-4247-40b9-9266-4a89a8566547">

Tenencias:

<img width="336" alt="image" src="https://github.com/gipage/sis-cripto/assets/83784311/3c0e56dd-b0c0-40d5-aefd-8889e787965a">

Intercambio:

Se intercambia 1 BTC por el equivalente en ETH, en este caso, 5 ETH.

<img width="511" alt="image" src="https://github.com/gipage/sis-cripto/assets/83784311/f997ae13-a62b-4a7e-b9c9-444d588d70a5">

Veamos las tenencias de ambas billeteras luego del intercambio.

<img width="331" alt="image" src="https://github.com/gipage/sis-cripto/assets/83784311/b338d065-aec0-403e-8f17-5d8c3b4bdf62">

##### 2. Depósito.

 | Parámetro | Tipo     | Descripción              | Ejemplo|
| :-------- | :------- | :------------------------- | :------------------------- |
| type| `String` | **Requerido** por body.  |Deposito
| destination_currency_ticker| `String` | **Requerido** por body.  |BTC
| destination_wallet_id| `UUID` | **Requerido** por body.  | c0a80067-8ba6-169e-818b-a6f3ed790004
| destination_amount | `BigDecimal` | **Requerido** por body. | 5

- URL: localhost:8080/api/v1/transactions
- Método: POST
- Respuesta:

  200 - OK: idtransaction, date_transaction, type, origen_wallet_id, destination_wallet_id (TransactionSuccesfullyDTO).

  varias excepciones personalizadas:
	- CurrencyDoesNotExist: Una criptodivisa a intercambiar/depositar no existe.
	- WalletDoesNotExist: Una billetera no existe.
	- HoldingDoesNotExist: Una billetera no posee tenencias de cripto.
	- NotEnoughFunds: Una billetera no posee los sufiencientes fondos para realizar un intercambio.

 La transacción depósito tiene una comision del 0.25% de la cantidad a depositar.
 
 Billetera destino:
   
<img width="275" alt="image" src="https://github.com/gipage/sis-cripto/assets/83784311/3c24961b-c5ba-464d-8934-cd7dbf0ebed4">

Depósito

<img width="508" alt="image" src="https://github.com/gipage/sis-cripto/assets/83784311/5054e044-ef32-4f00-848e-b4d39a5752da">

Billetera destino luego del depósito: 

<img width="281" alt="image" src="https://github.com/gipage/sis-cripto/assets/83784311/e0ceb0d1-ef60-4873-a95f-aa496aa9c877">

El 0.25% de la cantidad depositada va a la cuenta de la empresa. (Ya tiene varias transacciones cobradas, es el 0.25% de 1 BTC multiplicado por el valor de BTC (100 ARS) )

<img width="441" alt="image" src="https://github.com/gipage/sis-cripto/assets/83784311/a71c5f3b-840e-4c5d-8672-34e18b31caa4">

