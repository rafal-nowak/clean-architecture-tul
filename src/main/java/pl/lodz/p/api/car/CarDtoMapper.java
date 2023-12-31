package pl.lodz.p.api.car;

import org.mapstruct.Mapper;
import pl.lodz.p.domain.Car;

@Mapper(componentModel = "spring")
interface CarDtoMapper {

    CarDto toDto(Car car);

    Car toDomain(CarDto carDto);
}
