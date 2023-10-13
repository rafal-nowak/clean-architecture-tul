package pl.lodz.p.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lodz.p.domain.car.CarRepository;
import pl.lodz.p.domain.car.CarService;
import pl.lodz.p.external.storage.car.CarEntityMapper;
import pl.lodz.p.external.storage.car.InMemoryCarRepositoryAdapter;

@Configuration
@ConfigurationProperties("domain.properties")
public class DomainConfiguration {

    @Bean
    public CarRepository carRepository(CarEntityMapper mapper) {
        return new InMemoryCarRepositoryAdapter(mapper);
    }

    @Bean
    public CarService carService(CarRepository carRepository)  {
        return new CarService(carRepository);
    }

}
