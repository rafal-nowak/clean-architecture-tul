package pl.lodz.p.external.storage;

import org.mapstruct.Mapper;
import pl.lodz.p.domain.Car;


@Mapper(componentModel = "spring")
public interface CarEntityMapper {

    CarEntity toEntity(Car domain);

    Car toDomain(CarEntity entity);

}
