package co.opita.cluster.terraclusteropita.services;

import co.opita.cluster.terraclusteropita.dto.ProjectionDTO;
import co.opita.cluster.terraclusteropita.entities.GibsEntity;

import java.util.List;
import java.util.Optional;

public interface GibsService {

    byte[] fetchProjection(ProjectionDTO projection);
    List<String> fetchProjections(ProjectionDTO projection);
    Optional<GibsEntity> getById(Long id);
    Long countRequests();
}
