package co.opita.cluster.terraclusteropita.services.impl;

import co.opita.cluster.terraclusteropita.clients.GibsEarthDataClient;
import co.opita.cluster.terraclusteropita.dto.ProjectionDTO;
import co.opita.cluster.terraclusteropita.entities.GibsEntity;
import co.opita.cluster.terraclusteropita.entities.LayersEntity;
import co.opita.cluster.terraclusteropita.repository.GibsRepository;
import co.opita.cluster.terraclusteropita.repository.LayersRepository;
import co.opita.cluster.terraclusteropita.services.GibsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class GibsServiceImpl implements GibsService {

    private final GibsEarthDataClient gibClient;
    private final GibsRepository gibsRepository;

    public byte[] fetchProjection(ProjectionDTO projection) {
        GibsEntity log = new GibsEntity();
        log.setEndpoint("/projection");
        log.setDate(LocalDateTime.now());
        log.setFormat(projection.getFormat());
        log.setResolution(projection.getTileMatrixSet());
        log.setLayers(projection.getLayerIdentifier());
        gibsRepository.save(log);

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

    @Override
    public Optional<GibsEntity> getGibsById(Long id) {
        return gibsRepository.findById(id);
    }


    public Long countRequests() {
        return gibsRepository.count();
    }

}
