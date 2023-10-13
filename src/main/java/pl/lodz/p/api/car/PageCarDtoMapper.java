
package pl.lodz.p.api.car;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.lodz.p.domain.Car;
import pl.lodz.p.domain.PageCar;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageCarDtoMapper {

    @Mapping(target = "cars", qualifiedByName = "toCarDtoList")
    PageCarDto toPageDto(PageCar domain);

    @Named("toCarDtoList")
    @IterableMapping(qualifiedByName = "carToCarDto")
    List<CarDto> toListDto(List<Car> cars);

    @Named("carToCarDto")
    CarDto toDto(Car domain);
}