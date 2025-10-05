# A.D.A API

TerraCluster es una API desarrollada en Java con Spring Boot como parte del **NASA Space Apps Challenge**. Su propósito es consumir y exponer datos de **NASA GIBS (Global Imagery Browse Services)** para visualización y análisis ambiental.

## Características principales

* Obtención de proyecciones geográficas en formato imagen.
* Generación de timelapse con múltiples fechas.
* Registro y consulta de logs de peticiones.
* Contador de solicitudes realizadas.
* Documentación interactiva con **Swagger**.

## Documentación Swagger

La documentación de la API está disponible al ejecutar el proyecto en:

```
http://localhost:8080/swagger-ui.html
```

o en

```
http://localhost:8080/swagger-ui/index.html
```

## Endpoints disponibles

### 1. Proyección simple

Obtiene una imagen proyectada para una fecha específica.

```http
GET /api/gibs/projection
```

**Parámetros (query):**

* `LAYERS` (String) → Identificador de la capa.
* `FORMAT` (String) → Formato de salida (ej. `image/png`).
* `TIME` (String) → Fecha en formato `YYYY-MM-DD`.
* `BBOX` (String) → Extensión geográfica (ej. `-8295668.45,475018.02,-8195668.45,575018.02`).
* `WIDTH` (String) → Ancho de la imagen en píxeles.
* `HEIGHT` (String) → Alto de la imagen en píxeles.

**Ejemplo:**

```
GET /api/gibs/projection?LAYERS=CERES_Terra_Surface_UV_Index_All_Sky_Monthly&FORMAT=image/png&TIME=2000-07-12&BBOX=-8295668.45,475018.02,-8195668.45,575018.02&WIDTH=512&HEIGHT=512
```

**Respuesta:**
Imagen en bytes (MIME type: `image/png`).

---

### 2. Proyección múltiple (timelapse)

Genera una secuencia de imágenes en Base64 entre dos fechas.

```http
GET /api/gibs/projections
```

**Parámetros (query):**

* `LAYERS` (String) → Identificador de la capa.
* `FORMAT` (String) → Formato de salida.
* `START_DATE` (String) → Fecha de inicio (`YYYY-MM-DD`).
* `END_DATE` (String) → Fecha de fin (`YYYY-MM-DD`).
* `BBOX` (String) → Extensión geográfica.
* `WIDTH` (String) → Ancho de la imagen en píxeles.
* `HEIGHT` (String) → Alto de la imagen en píxeles.

**Ejemplo:**

```
GET /api/gibs/projections?LAYERS=MODIS_Terra_Thermal_Anomalies_All&FORMAT=image/png&START_DATE=2024-09-01&END_DATE=2024-09-30&BBOX=-8295668.45,475018.02,-8195668.45,575018.02&WIDTH=512&HEIGHT=512
```

**Respuesta:**

```json
[
  "iVBORw0KGgoAAAANSUhEUgAAAgAAAAIA...",
  "iVBORw0KGgoAAAANSUhEUgAAAgAAAAIA..."
]
```

---

### 3. Consultar log por ID

Obtiene un log de petición registrada.

```http
GET /api/gibs/logs/{id}
```

**Ejemplo:**

```
GET /api/gibs/logs/1
```

**Respuesta:**

```json
{
  "id": 1,
  "endpoint": "/projection",
  "date": "2025-10-04T13:20:15.112",
  "format": "image/png",
  "resolution": null
}
```

---

### 4. Contador de peticiones

Devuelve la cantidad total de peticiones realizadas.

```http
GET /api/gibs/logs/count
```

**Ejemplo de respuesta:**

```json
42
```

---

## Tecnologías usadas

* Java 17
* Spring Boot
* Spring Data JPA
* PostgreSQL (recomendado)
* Lombok
* Swagger / SpringDoc OpenAPI

## Ejecución del proyecto

1. Clonar el repositorio:

   ```bash
   git clone https://github.com/<usuario>/terracluster-api.git
   cd terracluster-api
   ```

2. Configurar `application.yml` con:

   * Credenciales de la base de datos
   * Configuración de cliente GIBS

3. Ejecutar:

   ```bash
   ./mvnw spring-boot:run
   ```

4. Acceder a la API en:

   ```
   http://localhost:8080/api/gibs
   ```

---

## Próximos pasos

* Integrar autenticación JWT.
* Cachear respuestas frecuentes para optimizar consumo de GIBS.
* Añadir más endpoints de consulta de capas (`layers findAll`, `findById`).
* Mejorar estadísticas con métricas de uso expuestas vía Actuator.
