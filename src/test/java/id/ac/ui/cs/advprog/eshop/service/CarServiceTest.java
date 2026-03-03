package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("123");
        car.setCarName("Subaru Baja");
    }

    @Test
    void testCreateCarWithId() {
        when(carRepository.create(car)).thenReturn(car);
        Car result = carService.create(car);
        assertEquals("123", result.getCarId());
        verify(carRepository, times(1)).create(car);
    }

    @Test
    void testCreateCarWithoutId() {
        Car newCar = new Car();
        when(carRepository.create(newCar)).thenReturn(newCar);
        Car result = carService.create(newCar);
        assertNotNull(result.getCarId());
        verify(carRepository, times(1)).create(newCar);
    }

    @Test
    void testFindAll() {
        List<Car> carList = new ArrayList<>();
        carList.add(car);
        Iterator<Car> iterator = carList.iterator();

        when(carRepository.findAll()).thenReturn(iterator);

        List<Car> result = carService.findAll();
        assertEquals(1, result.size());
        assertEquals("123", result.get(0).getCarId());
    }

    @Test
    void testFindById() {
        when(carRepository.findById("123")).thenReturn(car);
        Car result = carService.findById("123");
        assertEquals("123", result.getCarId());
    }

    @Test
    void testUpdate() {
        carService.update("123", car);
        verify(carRepository, times(1)).update("123", car);
    }

    @Test
    void testDeleteCarById() {
        carService.deleteCarById("123");
        verify(carRepository, times(1)).delete("123");
    }
}
