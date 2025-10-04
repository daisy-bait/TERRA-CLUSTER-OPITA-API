package co.opita.cluster.terraclusteropita.controllers;

import co.opita.cluster.terraclusteropita.dto.ProjectionDTO;
import co.opita.cluster.terraclusteropita.services.GibsService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/gibs")
public class TerraClusterController {

    private final GibsService gibsService;

    @GetMapping("/projection")
    public ResponseEntity<byte[]> getProjection(@RequestBody ProjectionDTO projection) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(
                gibsService.fetchProjection(projection)
        );
    }

}
