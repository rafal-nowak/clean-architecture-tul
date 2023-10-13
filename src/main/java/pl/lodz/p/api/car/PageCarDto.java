package pl.lodz.p.api.car;

import java.util.List;

public record PageCarDto(
        List<CarDto> cars,
        Integer currentPage,
        Integer totalPages,
        Long totalElements
) {
}
