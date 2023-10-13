package pl.lodz.p.api.car;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.domain.Car;
import pl.lodz.p.domain.CarService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(
        path = "/api/v1/cars",
        produces = "application/json",
        consumes = "application/json"
)
class CarController {

    private final CarService carService;
    private final CarDtoMapper carDtoMapper;
    private final PageCarDtoMapper pageCarDtoMapper;


    @GetMapping
    public ResponseEntity<List<CarDto>> getCars() {

        return ResponseEntity.ok(
                carService.findAll()
                        .stream()
                        .map(carDtoMapper::toDto)
                        .toList());
    }

//    @GetMapping
//    public ResponseEntity<PageCarDto> getCars(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "6") int size
//    ) {
//        Pageable pageable = PageRequest.of(page, size);
//        PageCarDto pageCars = pageCarDtoMapper.toPageDto(carService.findAll(pageable));
//
//        return ResponseEntity.ok(pageCars);
//    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CarDto> getCar(@PathVariable Integer id) {

        Car car = carService.findById(id);

        return ResponseEntity
                .ok(carDtoMapper.toDto(car));

    }

    @PostMapping
    public ResponseEntity<CarDto> saveCar(@RequestBody Car dto) {

        Car car = carService.save(dto);
        return ResponseEntity
                .ok(carDtoMapper.toDto(car));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeCar(@PathVariable Integer id){
        carService.removeById(id);
        return ResponseEntity.noContent().build();
    }

}
