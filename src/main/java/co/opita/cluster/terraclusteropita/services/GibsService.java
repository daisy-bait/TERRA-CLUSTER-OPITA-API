package co.opita.cluster.terraclusteropita.services;

import co.opita.cluster.terraclusteropita.dto.ProjectionDTO;
import co.opita.cluster.terraclusteropita.entities.GibsEntity;
import co.opita.cluster.terraclusteropita.entities.LayersEntity;

import java.util.List;
import java.util.Optional;

public interface GibsService {

    byte[] fetchProjection(ProjectionDTO projection);
    Optional<GibsEntity> getGibsById(Long id);
}
