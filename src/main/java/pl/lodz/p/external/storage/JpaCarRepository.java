package pl.lodz.p.external.storage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCarRepository extends JpaRepository<CarEntity, Integer> {
}
