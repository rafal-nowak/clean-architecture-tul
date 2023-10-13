package pl.lodz.p.external.storage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarEntity {

    @Id
    @SequenceGenerator(
            name = "car_id_seq",
            sequenceName = "car_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "car_id_seq"
    )
    private Integer id;
    @Column(
            nullable = false
    )
    private String make;
    @Column(
            nullable = false
    )
    private String model;
    @Column(
            nullable = false
    )
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
