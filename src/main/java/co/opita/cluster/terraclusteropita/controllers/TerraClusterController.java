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

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/gibs")
public class TerraClusterController {

    private final LayerService layerService;
    private final GibsService gibsService;

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

    @GetMapping("logs/{id}")
    public ResponseEntity<GibsEntity> getLogById(@PathVariable Long id) {
        return gibsService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/layers/{id}")
    public ResponseEntity<LayersEntity> getLayerById(@PathVariable Long id) {
        return layerService.getLayerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/layers")
    public ResponseEntity<List<LayersEntity>> getLayers() {
        List<LayersEntity> layers = layerService.getAllLayers();
        return ResponseEntity.ok(layers);
    }

    @GetMapping("logs/count")
    public ResponseEntity<Long> getStats() {
        return ResponseEntity.ok(gibsService.countRequests());
    }

}
