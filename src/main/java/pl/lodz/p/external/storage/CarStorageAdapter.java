package pl.lodz.p.external.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lodz.p.domain.Car;
import pl.lodz.p.domain.CarAlreadyExistsException;
import pl.lodz.p.domain.CarRepository;
import pl.lodz.p.domain.PageCar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log
public class CarStorageAdapter implements CarRepository {

    private final Connection connection; // Inject your JDBC connection here
    private final CarEntityMapper mapper;


    @Override
    public Car save(final Car car) {
        try {
            String insertQuery = "INSERT INTO car_entity (id, make, model, year) VALUES (nextval('car_id_seq'), ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, car.getMake());
                stmt.setString(2, car.getModel());
                stmt.setInt(3, car.getYear());
                stmt.executeUpdate();

                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    car.setId(generatedKeys.getInt(1));
                }
            }
            log.info("Saved entity: " + car);
            return car;
        } catch (SQLException ex) {
            if (ex.getSQLState().equals("23505")) {
                log.warning("DataIntegrityViolationException: " + ex);
                log.warning("Car " + car.getId() + " already exists in db");
                throw new CarAlreadyExistsException();
            } else {
                throw new RuntimeException("Failed to save car", ex);
            }
        }
    }


    @Override
    public void update(final Car car) {
        String updateQuery = "UPDATE car_entity SET make=?, model=?, year=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
            stmt.setString(1, car.getMake());
            stmt.setString(2, car.getModel());
            stmt.setInt(3, car.getYear());
            stmt.setInt(4, car.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to update car", ex);
        }
    }

    @Override
    public void remove(final Integer id) {
        String deleteQuery = "DELETE FROM car_entity WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(deleteQuery)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to delete car", ex);
        }
    }

    @Override
    public Optional<Car> findById(final Integer id) {
        String selectQuery = "SELECT id, make, model, year FROM car_entity WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(selectQuery)) {
            stmt.setInt(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    Car car = new Car(
                            resultSet.getInt("id"),
                            resultSet.getString("make"),
                            resultSet.getString("model"),
                            resultSet.getInt("year")
                    );
                    return Optional.of(car);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to fetch car", ex);
        }
    }

    @Override
    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();
        String selectQuery = "SELECT id, make, model, year FROM car_entity";
        try (PreparedStatement stmt = connection.prepareStatement(selectQuery);
             ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                Car car = new Car(
                        resultSet.getInt("id"),
                        resultSet.getString("make"),
                        resultSet.getString("model"),
                        resultSet.getInt("year")
                );
                cars.add(car);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to fetch cars", ex);
        }
        return cars;
    }

    @Override
    public PageCar findAll(final Pageable pageable) {
        List<Car> carsOnCurrentPage = new ArrayList<>();
        String selectPageQuery = "SELECT id, make, model, year FROM car_entity LIMIT ? OFFSET ?";
        try (PreparedStatement stmt = connection.prepareStatement(selectPageQuery)) {
            stmt.setInt(1, pageable.getPageSize());
            stmt.setInt(2, pageable.getPageNumber() * pageable.getPageSize());
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    Car car = new Car(
                            resultSet.getInt("id"),
                            resultSet.getString("make"),
                            resultSet.getString("model"),
                            resultSet.getInt("year")
                    );
                    carsOnCurrentPage.add(car);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to fetch cars for page", ex);
        }

        int totalCount = getTotalCarCount();
        int totalPages = (int) Math.ceil((double) totalCount / pageable.getPageSize());

        return new PageCar(
                carsOnCurrentPage,
                pageable.getPageNumber() + 1,
                totalPages,
                (long) totalCount
        );
    }

    private int getTotalCarCount() {
        String countQuery = "SELECT COUNT(*) FROM car_entity";
        try (PreparedStatement stmt = connection.prepareStatement(countQuery);
             ResultSet resultSet = stmt.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new RuntimeException("Failed to fetch car count");
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to fetch car count", ex);
        }
    }
}
