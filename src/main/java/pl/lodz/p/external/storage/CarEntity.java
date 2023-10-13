package pl.lodz.p.external.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Objects;

@Data
@AllArgsConstructor
@ToString
public class CarEntity {
    private Integer id;
    private String make;
    private String model;
    private Integer year;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final CarEntity carEntity = (CarEntity) o;

        return Objects.equals(id, carEntity.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
