# TerraCluster API

TerraCluster es una API desarrollada en Java con Spring Boot como parte del reto de la NASA Space Apps Challenge. Su objetivo es facilitar el acceso a datos satelitales provenientes de **NASA GIBS (Global Imagery Browse Services)**, procesar proyecciones geográficas y exponer servicios para análisis ambiental.

## Características principales

* Conexión con **GIBS Earth Data** para obtener proyecciones geográficas.
* Descarga de imágenes en diferentes formatos y resoluciones.
* Generación de timelapses a partir de rangos de fechas.
* Persistencia de logs de peticiones realizadas al servicio.
* Endpoints para consultar y gestionar capas (`layers`).
* Métricas de uso mediante un contador de peticiones.

## Endpoints disponibles

### Projections

| Método | Endpoint                | Descripción                                                                                                                       |
| ------ | ----------------------- | --------------------------------------------------------------------------------------------------------------------------------- |
| `POST` | `/projection`           | Realiza una proyección con los parámetros enviados en el cuerpo (formato, fechas, bbox, capa, etc.). Devuelve la imagen en bytes. |
| `POST` | `/projection/timelapse` | Genera un conjunto de proyecciones para un rango de fechas. Devuelve las imágenes codificadas en Base64.                          |
| `GET`  | `/projection/{id}`      | Obtiene un log de proyección almacenado por su identificador.                                                                     |
| `GET`  | `/projection/count`     | Devuelve la cantidad total de proyecciones solicitadas.                                                                           |

### Layers

| Método | Endpoint       | Descripción                                    |
| ------ | -------------- | ---------------------------------------------- |
| `GET`  | `/layers`      | Lista todas las capas disponibles.             |
| `GET`  | `/layers/{id}` | Obtiene la información de una capa específica. |

## Ejemplo de uso

### 1. Solicitar una proyección

```http
POST /projection
Content-Type: application/json

{
  "layerIdentifiers": ["MODIS_Terra_Thermal_Anomalies_All"],
  "format": "image/png",
  "startDate": "2024-09-01",
  "endDate": "2024-09-30",
  "bbox": "-8295668.45,475018.02,-8195668.45,575018.02",
  "width": 512,
  "height": 512
}
```

### 2. Obtener proyección por ID

```http
GET /projection/1
```

### 3. Consultar total de peticiones

```http
GET /projection/count
```

### 4. Listar capas disponibles

```http
GET /layers
```

## Tecnologías usadas

* Java 17
* Spring Boot
* Lombok
* JPA / Hibernate
* Base de datos relacional (PostgreSQL recomendado)

## Cómo ejecutar el proyecto

1. Clonar el repositorio:

   ```bash
   git clone https://github.com/<usuario>/terracluster-api.git
   cd terracluster-api
   ```

2. Configurar las variables de entorno o `application.yml` con:

   * Credenciales de la base de datos
   * Configuración del cliente GIBS

3. Ejecutar el proyecto:

   ```bash
   ./mvnw spring-boot:run
   ```

4. Acceder a la API en:

   ```
   http://localhost:8080
   ```

## Próximos pasos

* Implementar autenticación y autorización.
* Exponer métricas con **Spring Actuator**.
* Añadir cacheo de respuestas para optimizar el consumo de datos de GIBS.
* Extender soporte para más servicios de NASA.
