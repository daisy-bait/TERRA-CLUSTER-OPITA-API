package co.opita.cluster.terraclusteropita.services;

import co.opita.cluster.terraclusteropita.entities.LayersEntity;

import java.util.List;
import java.util.Optional;

public interface LayerService {

    Optional<LayersEntity> getLayerById(Long id);
    List<LayersEntity> getAllLayers();
}
