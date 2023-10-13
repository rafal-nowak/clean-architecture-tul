package pl.lodz.p.domain;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CarRepository {

    Car save(Car car);

    void update(Car car);

    void remove(Integer id);

    Optional<Car> findById(Integer id);

    List<Car> findAll();

    PageCar findAll(Pageable pageable);

}