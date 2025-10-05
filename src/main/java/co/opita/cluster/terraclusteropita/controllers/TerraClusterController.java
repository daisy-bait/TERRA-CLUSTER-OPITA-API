package co.opita.cluster.terraclusteropita.controllers;

import co.opita.cluster.terraclusteropita.dto.ProjectionDTO;
import co.opita.cluster.terraclusteropita.entities.GibsEntity;
import co.opita.cluster.terraclusteropita.services.impl.GibsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/gibs")
public class TerraClusterController {

    private final GibsServiceImpl gibsService;

    @GetMapping("/projection")
    public ResponseEntity<byte[]> getProjection(@RequestBody ProjectionDTO projection) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(
                gibsService.fetchProjection(projection)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GibsEntity> getLogById(@PathVariable Long id) {
        return gibsService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/stats")
    public ResponseEntity<Long> getStats() {
        return ResponseEntity.ok(gibsService.countRequests());
    }

}
