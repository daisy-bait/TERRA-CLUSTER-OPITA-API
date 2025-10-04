package co.opita.cluster.terraclusteropita.controllers;

import co.opita.cluster.terraclusteropita.clients.GibsEarthDataClient;
import co.opita.cluster.terraclusteropita.dto.ProjectionDTO;
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

    private final GibsEarthDataClient gibsClient;

    @GetMapping("/projection")
    public ResponseEntity<byte[]> getProjection(@RequestBody ProjectionDTO projection) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(
                gibsClient.executeGetGibsGeographicProjection(
                        projection.getLayerIdentifier(),
                        projection.getTime(),
                        projection.getTileMatrixSet(),
                        projection.getTileMatrix(),
                        projection.getTileRow(),
                        projection.getTileCol(),
                        projection.getFormat()
                )
        );
    }

}
