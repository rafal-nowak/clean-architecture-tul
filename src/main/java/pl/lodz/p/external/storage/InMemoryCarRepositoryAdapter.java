package pl.lodz.p.external.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Pageable;
import pl.lodz.p.domain.Car;
import pl.lodz.p.domain.CarRepository;
import pl.lodz.p.domain.PageCar;

import java.util.ArrayList;
import java.util.Collections;
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

    @Override
    public PageCar findAll(final Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Car> list;

        if (cars.values().size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, cars.values().size());

            list = new ArrayList<>(cars.values()).subList(startItem, toIndex)
                    .stream()
                    .map(mapper::toDomain).toList();
        }

        return new PageCar(
                list,
                pageable.getPageNumber() + 1,
                (int) ((double) cars.size() / pageable.getPageSize() + 0.99),
                (long) cars.size()
        );
    }

}
