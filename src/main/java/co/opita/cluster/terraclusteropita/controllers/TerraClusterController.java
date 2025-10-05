package co.opita.cluster.terraclusteropita.controllers;

import co.opita.cluster.terraclusteropita.dto.ProjectionDTO;
import co.opita.cluster.terraclusteropita.entities.GibsEntity;
import co.opita.cluster.terraclusteropita.entities.LayersEntity;
import co.opita.cluster.terraclusteropita.services.LayerService;
import co.opita.cluster.terraclusteropita.services.GibsService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/gibs")
@Tag(
        name = "GIBS - A.D.A API",
        description = "API que permite obtener proyecciones satelitales desde el sistema GIBS (Global Imagery Browse Services), incluyendo imágenes individuales, series temporales, capas y registros históricos del sistema."
)
public class TerraClusterController {

    private final LayerService layerService;
    private final GibsService gibsService;

    @Operation(
            summary = "Obtener una proyección satelital única",
            description = "Genera una proyección geoespacial única basada en las capas (LAYERS) seleccionadas, el formato, la fecha (TIME) y el área de visualización definida por BBOX. Devuelve una imagen en formato PNG codificada como bytes.",
            parameters = {
                    @Parameter(name = "LAYERS", description = "Identificadores de las capas a renderizar (separadas por comas)", example = "ASTER_GDEM_Greyscale_Shaded_Relief%2CCoastlines_15m%2CMODIS_Terra_NDSI_Snow_Cover"),
                    @Parameter(name = "FORMAT", description = "Formato de salida de la imagen", example = "image%2Fpng"),
                    @Parameter(name = "TIME", description = "Fecha de la proyección (YYYY-MM-DD)", example = "2003-08-01"),
                    @Parameter(name = "BBOX", description = "Caja delimitadora geográfica en coordenadas Web Mercator", example = "-10378806.119699232%2C-1690187.0606696845%2C-6378806.119699233%2C2309812.939330315"),
                    @Parameter(name = "WIDTH", description = "Ancho de la imagen generada en píxeles", example = "2560"),
                    @Parameter(name = "HEIGHT", description = "Alto de la imagen generada en píxeles", example = "1440")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Proyección generada correctamente", content = @Content(mediaType = "image/png")),
                    @ApiResponse(responseCode = "400", description = "Parámetros inválidos o faltantes"),
                    @ApiResponse(responseCode = "500", description = "Error interno al comunicarse con el servidor GIBS")
            }
    )
    @GetMapping("/projection")
    public ResponseEntity<byte[]> getProjection(
            @RequestParam(name = "LAYERS") String layerIdentifiers,
            @RequestParam(name = "FORMAT") String format,
            @RequestParam(name = "TIME") String time,
            @RequestParam(name = "BBOX") String bbox,
            @RequestParam(name = "WIDTH") String width,
            @RequestParam(name = "HEIGHT") String height
    ) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(
                gibsService.fetchProjection(new ProjectionDTO(
                        layerIdentifiers, format, time, null, bbox, width, height))
        );
    }

    @Operation(
            summary = "Obtener una serie temporal de proyecciones satelitales",
            description = "Genera una lista de imágenes codificadas en Base64 representando la evolución temporal de las capas seleccionadas entre las fechas indicadas.",
            parameters = {
                    @Parameter(name = "LAYERS", description = "Capas a visualizar", example = "ASTER_GDEM_Greyscale_Shaded_Relief%2CCoastlines_15m%2CMOPITT_CO_Monthly_Surface_Mixing_Ratio_Day"),
                    @Parameter(name = "FORMAT", description = "Formato de salida (solo 'image/png' soportado)", example = "image%2Fpng"),
                    @Parameter(name = "START_DATE", description = "Fecha inicial de la serie (YYYY-MM-DD)", example = "2003-08-01"),
                    @Parameter(name = "END_DATE", description = "Fecha final de la serie (YYYY-MM-DD)", example = "2003-10-01"),
                    @Parameter(name = "BBOX", description = "Caja delimitadora geográfica", example = "-10378806.119699232%2C-1690187.0606696845%2C-6378806.119699233%2C2309812.939330315"),
                    @Parameter(name = "WIDTH", description = "Ancho en píxeles", example = "2560"),
                    @Parameter(name = "HEIGHT", description = "Alto en píxeles", example = "1440")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de imágenes en Base64 generada exitosamente",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "[\"data:image/png;base64,iVBORw0KGgoAAAANS...\", \"data:image/png;base64,ABC123...\"]"))),
                    @ApiResponse(responseCode = "400", description = "Error en los parámetros de entrada"),
                    @ApiResponse(responseCode = "500", description = "Error al comunicarse con el servicio GIBS")
            }
    )
    @GetMapping("/projections")
    public ResponseEntity<List<String>> getProjections(
            @RequestParam(name = "LAYERS") String layerIdentifiers,
            @RequestParam(name = "FORMAT") String format,
            @RequestParam(name = "START_DATE") String startDate,
            @RequestParam(name = "END_DATE") String endDate,
            @RequestParam(name = "BBOX") String bbox,
            @RequestParam(name = "WIDTH") String width,
            @RequestParam(name = "HEIGHT") String height
    ) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(
                gibsService.fetchProjections(new ProjectionDTO(
                        layerIdentifiers, format, startDate, endDate, bbox, width, height))
        );
    }

    @Operation(
            summary = "Consultar un log específico por ID",
            description = "Obtiene un registro histórico de ejecución o solicitud del sistema GIBS, incluyendo metadatos como fecha, capa y parámetros utilizados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Log encontrado", content = @Content(schema = @Schema(implementation = GibsEntity.class))),
                    @ApiResponse(responseCode = "404", description = "Log no encontrado")
            }
    )
    @GetMapping("logs/{id}")
    public ResponseEntity<GibsEntity> getLogById(@PathVariable Long id) {
        return gibsService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Obtener una capa específica",
            description = "Permite consultar la información detallada de una capa registrada en la base de datos local del sistema (SQLite).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Capa encontrada", content = @Content(schema = @Schema(implementation = LayersEntity.class))),
                    @ApiResponse(responseCode = "404", description = "Capa no encontrada")
            }
    )
    @GetMapping("/layers/{id}")
    public ResponseEntity<LayersEntity> getLayerById(@PathVariable Long id) {
        return layerService.getLayerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Listar todas las capas disponibles",
            description = "Devuelve todas las capas satelitales registradas en la base de datos SQLite del proyecto, con su información técnica (nombre, identificador, proyección, etc.).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado de capas obtenido correctamente", content = @Content(mediaType = "application/json"))
            }
    )
    @GetMapping("/layers")
    public ResponseEntity<List<LayersEntity>> getLayers() {
        List<LayersEntity> layers = layerService.getAllLayers();
        return ResponseEntity.ok(layers);
    }

    @Operation(
            summary = "Obtener estadísticas de peticiones GIBS",
            description = "Devuelve el número total de peticiones o logs almacenados en la base de datos. Útil para métricas o paneles administrativos.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Conteo obtenido correctamente", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "42")))
            }
    )
    @GetMapping("logs/count")
    public ResponseEntity<Long> getStats() {
        return ResponseEntity.ok(gibsService.countRequests());
    }
}