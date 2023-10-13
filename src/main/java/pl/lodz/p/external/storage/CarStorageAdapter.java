package pl.lodz.p.external.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lodz.p.domain.Car;
import pl.lodz.p.domain.CarAlreadyExistsException;
import pl.lodz.p.domain.CarRepository;
import pl.lodz.p.domain.PageCar;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log
public class CarStorageAdapter implements CarRepository {

    private final JpaCarRepository carRepository;
    private final CarEntityMapper mapper;
    @Override
    public Car save(final Car car) {
        try {
            CarEntity saved = carRepository.save(mapper.toEntity(car));
            log.info("Saved entity " + saved);
            return mapper.toDomain(saved);
        } catch (DataIntegrityViolationException ex) {
            log.warning("DataIntegrityViolationException: " + ex);
            log.warning("Car " + car.getId() + " already exits in db");
            throw new CarAlreadyExistsException();
        }
    }

    @Override
    public void update(final Car car) {
        carRepository.findById(car.getId()).ifPresent(carEntity -> carRepository.save(mapper.toEntity(car)));
    }

    @Override
    public void remove(final Integer id) {
        carRepository.deleteById(id);
    }

    @Override
    public Optional<Car> findById(final Integer id) {
        return carRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public PageCar findAll(final Pageable pageable) {
        Page<CarEntity> pageOfCarsEntity = carRepository.findAll(pageable);
        List<Car> carsOnCurrentPage = pageOfCarsEntity.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
        return new PageCar(
                carsOnCurrentPage,
                pageable.getPageNumber() +1,
                pageOfCarsEntity.getTotalPages(),
                pageOfCarsEntity.getTotalElements()
        );
    }
}
