package pl.lodz.p.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lodz.p.domain.CarRepository;
import pl.lodz.p.domain.CarService;
import pl.lodz.p.external.storage.CarEntityMapper;
import pl.lodz.p.external.storage.CarStorageAdapter;
import pl.lodz.p.external.storage.InMemoryCarRepositoryAdapter;
import pl.lodz.p.external.storage.JpaCarRepository;

@Configuration
@ConfigurationProperties("domain.properties")
public class DomainConfiguration {

//    @Bean
//    public CarRepository carRepository(CarEntityMapper mapper) {
//        return new InMemoryCarRepositoryAdapter(mapper);
//    }

    @Bean
    public CarRepository carRepository(JpaCarRepository jpaCarRepository, CarEntityMapper mapper) {
        return new CarStorageAdapter(jpaCarRepository, mapper);
    }

    @Bean
    public CarService carService(CarRepository carRepository)  {
        return new CarService(carRepository);
    }

}
