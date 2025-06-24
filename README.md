<div align="center">

# Movie Searcher

*Prueba técnica para izertis*

![Angular](https://img.shields.io/badge/angular-%23DD0031.svg?style=for-the-badge&logo=angular&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

</div>

## Tabla de Contenidos
- [Instalación](#instalación)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Uso](#uso)
- [Despliegue en Render.com](#despliegue-en-rendercom)
- [Configuración de Docker](#configuración-de-docker)
- [Variables de Entorno](#variables-de-entorno)
- [Pruebas](#pruebas)
- [Contribuciones](#contribuciones)
- [Licencia](#licencia)
- [Contacto](#contacto)

## 🚀 Instalación
1. Clonar el repositorio:
   ```bash
   git clone https://github.com/usuario/proyecto-fullstack.git
   cd proyecto-fullstack
   ```
-- TODO --

## 📁 Estructura del Proyecto
```
izertis-tech-test/
├── backend/           # Código fuente de Spring Boot
│   ├── src/main/java
│   ├── src/main/resources
│   └── Dockerfile
├── frontend/          # Código fuente de Angular
│   ├── src/
│   └── Dockerfile
├── docker-compose.yml # Orquestación local de contenedores
```

## ⚙ Uso
### Ejecutar localmente con Docker
```bash
# Construir y levantar contenedores
docker-compose up --build
```
Accede a:
- Frontend: http://localhost:4200
- Backend: http://localhost:8080

## ☁ Despliegue
Se ha desplegado la aplicación en render.com con el tier free

-- Sección despliegue --

## 🍃 Spring

-- Sección spring -- 

### 🔧 Variables de Entorno
| Variable               | Descripción                                    | Ejemplo             |
|------------------------|------------------------------------------------|---------------------|
| SPRING_PROFILES_ACTIVE | Perfil de configuración para Spring (opcional) | dev                 |
| OMDB_API_KEY           | Clave para autenticarse contra OMDB            | ******              |
| TMDB_API_KEY           | Clave para autenticarse contra TMDB            | clave jwt           |
| JWT_AUTH_SECRET        | Clave secreta (HS256) para firma de tokens JWT | MiClaveSuperSecreta |

## ♦ Angular

-- Sección angular --

## 📧 Contacto
- **Autor**: Elliot Luque Pascual
- **Email**: elliotluque@gmail.com
