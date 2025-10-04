package co.opita.cluster.terraclusteropita.services;

import co.opita.cluster.terraclusteropita.dto.ProjectionDTO;

public interface GibsService {

    public byte[] fetchProjection(ProjectionDTO projection);

}
