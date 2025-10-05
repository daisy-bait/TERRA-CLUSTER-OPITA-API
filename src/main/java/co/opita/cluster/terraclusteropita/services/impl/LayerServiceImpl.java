package co.opita.cluster.terraclusteropita.services.impl;

import co.opita.cluster.terraclusteropita.entities.LayersEntity;
import co.opita.cluster.terraclusteropita.repository.LayersRepository;
import co.opita.cluster.terraclusteropita.services.LayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LayerServiceImpl implements LayerService {

    private final LayersRepository layersRepository;


    @Override
    public Optional<LayersEntity> getLayerById(Long id) {
        return layersRepository.findById(id);
    }

    @Override
    public List<LayersEntity> getAllLayers() {
        return layersRepository.findAll();
    }

}
