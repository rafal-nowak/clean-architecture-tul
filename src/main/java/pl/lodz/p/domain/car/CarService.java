package pl.lodz.p.domain.car;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public Car findById(Integer id) {
        return carRepository.findById(id)
                .orElseThrow(CarNotFoundException::new);
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car save(final Car car) {
        return carRepository.save(car);
    }

    public void removeById(final Integer id) {
        findById(id);
        carRepository.remove(id);
    }
}
