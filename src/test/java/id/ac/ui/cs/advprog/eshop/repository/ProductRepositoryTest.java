package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    void testCreateAndFind() {
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        productRepository.create(product);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditProductPositive() {
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName("Sampo Cap Bambang Baru");
        updatedProduct.setProductQuantity(200);

        Product result = productRepository.edit(updatedProduct);

        assertNotNull(result);
        assertEquals("Sampo Cap Bambang Baru", result.getProductName());
        assertEquals(200, result.getProductQuantity());

        Product storedProduct = productRepository.findById(product.getProductId());
        assertEquals("Sampo Cap Bambang Baru", storedProduct.getProductName());
        assertEquals(200, storedProduct.getProductQuantity());
    }

    @Test
    void testEditProductNegative_NotFound() {
        Product notFoundProduct = new Product();
        notFoundProduct.setProductId("random-id-yang-tidak-ada");
        notFoundProduct.setProductName("Produk Gaib");
        notFoundProduct.setProductQuantity(10);

        Product result = productRepository.edit(notFoundProduct);

        assertNull(result);
    }

    @Test
    void testEditProduct_WhenProductIsNotFirstInList() {
        productRepository.create(product);

        Product product2 = new Product();
        product2.setProductId("id-2");
        product2.setProductName("Sampo Akhir");
        product2.setProductQuantity(20);
        productRepository.create(product2);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("id-2");
        updatedProduct.setProductName("Sampo Akhir Diedit");
        updatedProduct.setProductQuantity(99);

        Product result = productRepository.edit(updatedProduct);

        assertNotNull(result);
        assertEquals("Sampo Akhir Diedit", result.getProductName());

        Product checkProduct1 = productRepository.findById(product.getProductId());
        assertEquals(product.getProductName(), checkProduct1.getProductName());
    }

    @Test
    void testDeleteProductPositive() {
        productRepository.create(product);

        Product deletedProduct = productRepository.delete(product.getProductId());

        assertNotNull(deletedProduct);
        assertEquals(product.getProductName(), deletedProduct.getProductName());

        Product findResult = productRepository.findById(product.getProductId());
        assertNull(findResult);

        Iterator<Product> iterator = productRepository.findAll();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testDeleteProductNegative_NotFound() {
        productRepository.create(product);

        Product result = productRepository.delete("id-ngasal-yang-tidak-ada");

        assertNull(result);

        assertNotNull(productRepository.findById(product.getProductId()));
    }

    @Test
    void testDelete_WhenProductIsNotFirstInList() {
        productRepository.create(product);

        Product product2 = new Product();
        product2.setProductId("id-2");
        product2.setProductName("Sampo Akhir");
        product2.setProductQuantity(20);
        productRepository.create(product2);

        Product result = productRepository.delete("id-2");

        assertNotNull(result);
        assertEquals("id-2", result.getProductId());

        Iterator<Product> iterator = productRepository.findAll();
        assertTrue(iterator.hasNext());
        assertEquals(product.getProductId(), iterator.next().getProductId());
    }

    @Test
    void testCreateProductWithNoId_ShouldGenerateId() {
        Product productNoId = new Product();
        productNoId.setProductName("Sampo Tanpa ID");
        productNoId.setProductQuantity(10);

        productRepository.create(productNoId);

        assertNotNull(productNoId.getProductId());
        assertFalse(productNoId.getProductId().isEmpty());
        assertEquals("Sampo Tanpa ID", productNoId.getProductName());
    }
}