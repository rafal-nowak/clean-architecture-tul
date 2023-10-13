package pl.lodz.p.api.car;

public record CarDto(
        Integer id,
        String make,
        String model,
        Integer year
) {
}
