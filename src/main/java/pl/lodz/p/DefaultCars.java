package pl.lodz.p;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.lodz.p.domain.Car;
import pl.lodz.p.domain.CarService;

@Component
public class DefaultCars implements CommandLineRunner {

    private final CarService carService;

    public DefaultCars(CarService carService) {
        this.carService = carService;
    }

    private final Car exampleCar = new Car(
        1,
        "Tesla",
        "Model S",
        2023);


    @Override
    public void run(String... args) {
        try {
            addCar(exampleCar);
            System.out.println("cars:  " + carService.findAll());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addCar(Car car) {
        carService.save(car);
    }
}
