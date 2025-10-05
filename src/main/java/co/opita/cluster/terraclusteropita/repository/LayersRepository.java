package co.opita.cluster.terraclusteropita.repository;

import co.opita.cluster.terraclusteropita.entities.LayersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LayersRepository extends JpaRepository<LayersEntity, Long> {
}
