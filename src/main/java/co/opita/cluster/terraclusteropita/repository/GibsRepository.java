package co.opita.cluster.terraclusteropita.repository;

import co.opita.cluster.terraclusteropita.entities.GibsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GibsRepository extends JpaRepository<GibsEntity,Long> {

}
