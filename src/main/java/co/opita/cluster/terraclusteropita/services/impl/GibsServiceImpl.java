package co.opita.cluster.terraclusteropita.services.impl;

import co.opita.cluster.terraclusteropita.clients.GibsEarthDataClient;
import co.opita.cluster.terraclusteropita.dto.ProjectionDTO;
import co.opita.cluster.terraclusteropita.services.GibsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class GibsServiceImpl implements GibsService {

    private final GibsEarthDataClient gibClient;

    public byte[] fetchProjection(ProjectionDTO projection) {
        return gibClient.executeGetGibsGeographicProjection(
                projection.getLayerIdentifier(),
                projection.getTime(),
                projection.getTileMatrixSet(),
                projection.getTileMatrix(),
                projection.getTileRow(),
                projection.getTileCol(),
                projection.getFormat()
        );
    }
}
