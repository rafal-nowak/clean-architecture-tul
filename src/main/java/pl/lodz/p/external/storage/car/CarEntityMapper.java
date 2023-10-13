package pl.lodz.p.external.storage.car;

import org.mapstruct.Mapper;
import pl.lodz.p.domain.car.Car;


@Mapper(componentModel = "spring")
public interface CarEntityMapper {

    CarEntity toEntity(Car domain);

    Car toDomain(CarEntity entity);

}
