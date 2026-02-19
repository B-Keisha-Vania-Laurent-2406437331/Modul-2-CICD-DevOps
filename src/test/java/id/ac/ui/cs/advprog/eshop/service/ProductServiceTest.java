package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private ProductServiceImpl productService;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
        productService = new ProductServiceImpl();

        ReflectionTestUtils.setField(productService, "productRepository", productRepository);
    }

    @Test
    void testCreateAndFindAll() {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Sampo");
        product.setProductQuantity(10);

        productService.create(product);
        List<Product> list = productService.findAll();

        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
        assertEquals("Sampo", list.get(0).getProductName());
    }

    @Test
    void testFindById() {
        Product product = new Product();
        product.setProductId("2");
        productService.create(product);

        Product found = productService.findById("2");
        assertNotNull(found);
        assertEquals("2", found.getProductId());
    }

    @Test
    void testEdit() {
        Product product = new Product();
        product.setProductId("3");
        product.setProductName("Lama");
        productService.create(product);

        product.setProductName("Baru");
        productService.edit(product);

        Product edited = productService.findById("3");
        assertEquals("Baru", edited.getProductName());
    }

    @Test
    void testDelete() {
        Product product = new Product();
        product.setProductId("4");
        productService.create(product);

        productService.delete("4");
        Product deleted = productService.findById("4");
        assertNull(deleted);
    }
}
