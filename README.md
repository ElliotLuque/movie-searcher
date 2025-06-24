<div align="center">

# 🎬 Movie Searcher

![image](https://github.com/user-attachments/assets/99427ecb-5bd2-4c92-ba72-ab43e76d52e5)

*Prueba técnica para **Izertis***

![Angular](https://img.shields.io/badge/angular-%23DD0031.svg?style=for-the-badge&logo=angular&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

</div>

## 🧾 Resumen
Este proyecto es una prueba técnica planteada por Izertis compuesta por un frontend en Angular v20 y un backend en Spring Boot 3.

### 🎯 Objetivo
Desarrollar una aplicación que permita:

- Consultar un listado de películas a partir de un texto de búsqueda.
- Visualizar el detalle de cada película seleccionada (nombre, fecha, idioma, sinopsis, etc.).
- Separar claramente las responsabilidades de frontend y backend, donde:
   - El frontend actúa como cliente visual.
   - El backend actúa como proxy/wrapper ante servicios de terceros (OMDb y TMDb).



## 🚀 Instalación

### 🔁 Opción 1: Usando Docker Compose
1. Clona el repositorio:
```bash
git clone https://github.com/ElliotLuque/izertis-tech-test.git
cd izertis-tech-test
```
2. Crea un archivo .env en la raíz del proyecto con las siguientes variables:
```env
OMDB_API_KEY=tu_clave_omdb
TMDB_API_KEY=tu_clave_tmdb
JWT_AUTH_SECRET=tu_clave_secreta
```
> [!NOTE]
> Solo necesitas una única ```API_KEY```. Asegúrate de cambiar el proveedor ```movies.provider``` en ```resources/application.yml``` y seleccionar el mismo que corresponde a tu clave, en caso de que solo estés utilizando una.
> 
> Para generar una clave secreta que se utilizará en la autenticación mediante JWT con el algoritmo HS256, podemos hacerlo de la siguiente manera:
> 
> *Linux*:
>   ```bash
> openssl rand -base64 64
>   ```
>
> *Windows (Powershell)*:
>  ```powershell
> [Convert]::ToBase64String((1..64 | ForEach-Object {Get-Random -Maximum 256}) -as [byte[]])
>  ```

3. Asegúrate de tener Docker y Docker Compose instalados.

4. Ejecuta los servicios:
```bash
docker-compose up --build
```

5. Accede a la aplicación:
- Frontend: http://localhost:4200
- Backend: http://localhost:8080


### 🧩 Opción 2: Sin Docker Compose (modo manual)

> [!WARNING]
> Necesitas tener instalado:
> - Java 21
> - Node.js 20

1. Ejecutar el backend
```bash
cd src/backend
./mvnw spring-boot:run
```
> También puedes empaquetarlo
```bash
./mvnw clean package -DskipTests
java -jar target/*.jar
```

2. Ejecutar el frontend
```bash
cd src/frontend
npm install
ng serve
```
> Accede a: http://localhost:4200


## 📁 Estructura del Proyecto
```
izertis-tech-test/
├── src/                     # Carpeta raíz del código fuente
│   ├── backend/             # Código fuente de Spring Boot
│   │   ├── src/main/java
│   │   ├── src/main/resources
│   │   └── Dockerfile
│   └── frontend/            # Código fuente de Angular
│       ├── src/
│       └── Dockerfile
├── docker-compose.yml       # Orquestación local de contenedores
```

## 🍃 Spring

El backend está desarrollado con Spring Boot 3, Java 21 y Spring WebFlux, ofreciendo una API REST reactiva y no bloqueante.
Funciona como un proxy entre el frontend y los servicios públicos de películas (OMDb y TMDb), añadiendo lógica adicional como autenticación, adaptación de respuestas y caché.

### ✨ Características principales
- API RESTful reactiva para búsqueda y detalle de películas
- Autenticación con JWT usando cookies seguras (HttpOnly)
- Documentación completa con OpenAPI [SwaggerUI](https://izertis-tech-test-backend.onrender.com/docs)
- Integración con OMDb y TMDb a través de Spring WebClient
- Configuración de CORS para habilitar peticiones desde frontend web
- Caché server-side con Caffeine y client-side con HTTP headers
- Variables externas mediante .env para facilitar configuración y despliegue

### 🧱 Arquitectura
Se ha adoptado una arquitectura hexagonal (Ports & Adapters) que separa claramente el dominio de los detalles de infraestructura. Este enfoque permite extender el sistema fácilmente en el futuro (por ejemplo, añadiendo nuevas integraciones con otras APIs de películas), sin modificar el núcleo de negocio ni comprometer su mantenibilidad.

## 🅰 Angular

El frontend está desarrollado con Angular 20, utilizando las últimas capacidades del framework como signals, componentes standalone, sintaxis de control de flujo moderno (@if, @for) y estilos con TailwindCSS. La interfaz ofrece una experiencia fluida e interactiva para buscar y explorar películas, adaptándose correctamente al estado de carga, errores o ausencia de resultados.

### ✨ Características principales
- Interfaz moderna con Angular 20 + TailwindCSS
- Gestión del estado con signals y componentes desacoplados
- Integración completa con el backend mediante peticiones autenticadas (cookies con JWT)
- Fallbacks visuales para imágenes rotas y loaders esqueléticos durante la carga


### 🧱 Arquitectura
El frontend se ha estructurado como una Single Page Application (SPA) modular, que separa claramente los servicios (API, autenticación), vistas (pantallas de listado y detalle) y componentes reutilizables.

## ☁ Despliegue
La aplicación se ha desplegado en [Render.com](https://render.com) aprovechando el tier gratuito:

### 🐳 Requisitos
- Configurar el frontend como Static Site, con ruta /frontend/dist/movie-searcher/browser
- Backend como servicio web (Docker)
- Añadir variables de entorno desde el panel de configuración de Render

## 📧 Contacto
- **Autor**: Elliot Luque Pascual
- **Email**: elliotluque@gmail.com
