package pl.lodz.p.external.storage.car;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import pl.lodz.p.domain.car.Car;
import pl.lodz.p.domain.car.CarRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Log
public class InMemoryCarRepositoryAdapter implements CarRepository {

    private final CarEntityMapper mapper;
    private final Map<Integer, CarEntity> cars = new HashMap<>();
    private static Integer id = 0;

    @Override
    public Car save(final Car car) {
        id++;
        car.setId(id);
        cars.put(id, mapper.toEntity(car));
        return car;
    }

    @Override
    public void update(final Car car) {
        cars.put(id, mapper.toEntity(car));
    }

    @Override
    public void remove(final Integer id) {
        cars.remove(id);
    }

    @Override
    public Optional<Car> findById(final Integer id) {
        return Optional.ofNullable(cars.get(id)).map(mapper::toDomain);
    }

    @Override
    public List<Car> findAll() {
        return cars.values().stream().map(mapper::toDomain).toList();
    }

}
