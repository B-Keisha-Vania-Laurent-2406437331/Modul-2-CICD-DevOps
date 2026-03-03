package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {

    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testCreateAndFind() {
        Car car = new Car();
        car.setCarId("123");
        car.setCarName("Suzuki Jimny");
        carRepository.create(car);

        Iterator<Car> iterator = carRepository.findAll();
        assertTrue(iterator.hasNext());
        Car savedCar = iterator.next();
        assertEquals(car.getCarId(), savedCar.getCarId());
    }

    @Test
    void testFindByIdIfFound() {
        Car car = new Car();
        car.setCarId("123");
        carRepository.create(car);

        Car foundCar = carRepository.findById("123");
        assertNotNull(foundCar);
        assertEquals("123", foundCar.getCarId());
    }

    @Test
    void testFindByIdIfNotFound() {
        Car foundCar = carRepository.findById("999");
        assertNull(foundCar);
    }

    @Test
    void testUpdateIfFound() {
        Car car = new Car();
        car.setCarId("123");
        car.setCarName("Suzuki Jimny");
        carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarName("Mazda RX-7");
        updatedCar.setCarColor("Vintage Red");
        updatedCar.setCarQuantity(2);

        Car result = carRepository.update("123", updatedCar);
        assertNotNull(result);
        assertEquals("Mazda RX-7", result.getCarName());
        assertEquals("Vintage Red", result.getCarColor());
    }

    @Test
    void testUpdateIfNotFound() {
        Car updatedCar = new Car();
        updatedCar.setCarName("Mazda RX-7");

        Car result = carRepository.update("999", updatedCar);
        assertNull(result);
    }

    @Test
    void testDelete() {
        Car car = new Car();
        car.setCarId("123");
        carRepository.create(car);

        carRepository.delete("123");
        assertNull(carRepository.findById("123"));
    }
}