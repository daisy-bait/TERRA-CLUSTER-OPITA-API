package co.opita.cluster.terraclusteropita.services;

import co.opita.cluster.terraclusteropita.dto.ProjectionDTO;
import co.opita.cluster.terraclusteropita.entities.GibsEntity;

import java.util.Optional;

public interface GibsService {

    byte[] fetchProjection(ProjectionDTO projection);
    Optional<GibsEntity> getById(Long id);
}
