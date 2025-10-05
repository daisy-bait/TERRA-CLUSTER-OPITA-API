package co.opita.cluster.terraclusteropita.controllers;

import co.opita.cluster.terraclusteropita.dto.ProjectionDTO;
import co.opita.cluster.terraclusteropita.entities.GibsEntity;
import co.opita.cluster.terraclusteropita.entities.LayersEntity;
import co.opita.cluster.terraclusteropita.services.LayerService;
import co.opita.cluster.terraclusteropita.services.impl.GibsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/gibs")
public class TerraClusterController {

    private final GibsServiceImpl gibsService;
    private final LayerService layerService;

    @GetMapping("/projection")
    public ResponseEntity<byte[]> getProjection(@RequestBody ProjectionDTO projection) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(
                gibsService.fetchProjection(projection)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GibsEntity> getLogById(@PathVariable Long id) {
        return gibsService.getGibsById(id)
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

    @GetMapping("/stats")
    public ResponseEntity<Long> getStats() {
        return ResponseEntity.ok(gibsService.countRequests());
    }

}
