package pl.lodz.p.domain.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Car {
    private Integer id;
    private String make;
    private String model;
    private Integer year;
}
