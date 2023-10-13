package pl.lodz.p.domain;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class PageCar implements Serializable {

    List<Car> cars;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}