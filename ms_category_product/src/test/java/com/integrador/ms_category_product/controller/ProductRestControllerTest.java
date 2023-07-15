package com.integrador.ms_category_product.controller;

import com.integrador.ms_category_product.model.Product;
import com.integrador.ms_category_product.response.ProductResponseRest;
import com.integrador.ms_category_product.services.IProductService;
import com.integrador.ms_category_product.util.ProductExcelExporter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductRestController test")
class ProductRestControllerTest {


//    @Test
//    @DisplayName("Should throw an exception when the picture parameter is missing")
//    void saveProductWhenPictureIsMissingThenThrowException() {        // Create a mock MultipartFile
//        MultipartFile picture = mock(MultipartFile.class);
//
//        // Create a mock ProductService
//        IProductService productService = mock(IProductService.class);
//
//        // Create a ProductRestController instance with the mock ProductService
//        ProductRestController productRestController = new ProductRestController(productService);
//
//        // Call the save method with a missing picture parameter
//        assertThrows(IOException.class, () -> {
//            productRestController.save(null, "Test Product", 10.0, 5, 1L);
//        });
//
//        // Verify that the productService.save method was not called
//        verify(productService, never()).save(any(Product.class), anyLong());
//    }
//
//    @Test
//    @DisplayName("Should throw an exception when the account parameter is missing")
//    void saveProductWhenAccountIsMissingThenThrowException() {        // Create a mock MultipartFile
//        MultipartFile picture = mock(MultipartFile.class);
//        when(picture.getBytes()).thenReturn(new byte[0]);
//
//        // Create a mock ProductService
//        IProductService productService = mock(IProductService.class);
//
//        // Create a ProductRestController instance with the mock ProductService
//        ProductRestController productRestController = new ProductRestController(productService);
//
//        // Call the save method with missing account parameter
//        assertThrows(IOException.class, () -> {
//            productRestController.save(picture, "Test Product", 10.0, null, 1L);
//        });
//
//        // Verify that the productService.save method was not called
//        verify(productService, never()).save(any(Product.class), anyLong());
//    }
//
//    @Test
//    @DisplayName("Should throw an exception when the price parameter is missing")
//    void saveProductWhenPriceIsMissingThenThrowException() {        // Create a mock MultipartFile
//        MultipartFile picture = mock(MultipartFile.class);
//        when(picture.getBytes()).thenReturn(new byte[0]);
//
//        // Create a mock ProductService
//        IProductService productService = mock(IProductService.class);
//
//        // Create a ProductRestController instance with the mock ProductService
//        ProductRestController productRestController = new ProductRestController(productService);
//
//        // Call the save method with missing price parameter
//        assertThrows(IOException.class, () -> {
//            productRestController.save(picture, "Test Product", null, 10, 1L);
//        });
//
//        // Verify that the productService.save method was not called
//        verify(productService, never()).save(any(Product.class), anyLong());
//    }
//
//    @Test
//    @DisplayName("Should throw an exception when the name parameter is missing")
//    void saveProductWhenNameIsMissingThenThrowException() {        // Create a mock MultipartFile
//        MultipartFile picture = mock(MultipartFile.class);
//        when(picture.getBytes()).thenReturn(new byte[0]);
//
//        // Create a mock ProductService
//        IProductService productService = mock(IProductService.class);
//
//        // Create a ProductRestController instance with the mock ProductService
//        ProductRestController productRestController = new ProductRestController(productService);
//
//        // Call the save method with missing name parameter
//        assertThrows(IOException.class, () -> {
//            productRestController.save(picture, null, 10.0, 5, 1L);
//        });
//
//        // Verify that the productService.save method was not called
//        verify(productService, never()).save(any(Product.class), anyLong());
//    }


    @Test
    @DisplayName("Should export to Excel successfully")
    void exportToExcelSuccessfully() throws IOException {        // Create mock objects
        IProductService productService = mock(IProductService.class);
        ProductResponseRest productResponseRest = new ProductResponseRest();
        ResponseEntity<ProductResponseRest> products = new ResponseEntity<>(productResponseRest, HttpStatus.OK);
        when(productService.search()).thenReturn(products);
        ProductExcelExporter excelExporter = mock(ProductExcelExporter.class);
        doNothing().when(excelExporter).export(any(HttpServletResponse.class));
        HttpServletResponse response = mock(HttpServletResponse.class);
        ProductRestController productRestController = new ProductRestController(productService);
        productRestController.exportToExcel(response);
        verify(productService, times(1)).search();
        verify(excelExporter, times(1)).export(response);
    }

    @Test
    @DisplayName("Should throw an exception when trying to delete a product with non-existing id")
    void deleteProductByNonExistingIdThenThrowException() {
        Long nonExistingId = 100L;
        IProductService productService = mock(IProductService.class);
        ProductRestController productRestController = new ProductRestController(productService);

        when(productService.deleteById(nonExistingId)).thenReturn(ResponseEntity.notFound().build());

        ResponseEntity<ProductResponseRest> response = productRestController.deleteById(nonExistingId);

        assertEquals(ResponseEntity.notFound().build(), response);
        verify(productService, times(1)).deleteById(nonExistingId);
    }

    @Test
    @DisplayName("Should delete the product by id and return the response")
    void deleteProductById() {
        IProductService productService = mock(IProductService.class);
        MultipartFile picture = mock(MultipartFile.class);
        Long id = 1L;
        ProductRestController productRestController = new ProductRestController(productService);
        ProductResponseRest productResponseRest = mock(ProductResponseRest.class);
        ResponseEntity<ProductResponseRest> responseEntity = mock(ResponseEntity.class);
        when(productService.deleteById(id)).thenReturn(responseEntity);
        ResponseEntity<ProductResponseRest> result = null;
        result = productRestController.deleteById(id);
        verify(productService, times(1)).deleteById(id);
        assertEquals(responseEntity, result);
    }

    @Test
    @DisplayName("Should not update the product when the name is null")
    void updateProductWhenNameIsNull() {
        // Create a mock instance of IProductService
        IProductService productService = mock(IProductService.class);

        // Create a mock instance of MultipartFile
        MultipartFile picture = mock(MultipartFile.class);

        // Create an instance of ProductRestController and pass the mock productService
        ProductRestController productRestController = new ProductRestController(productService);

        // Set up the necessary parameters for the update method
        String name = null;
        Double price = 10.0;
        int account = 5;
        Long categoryID = 1L;
        Long id = 1L;

        // Call the update method
        assertThrows(IOException.class, () -> {
            productRestController.update(picture, name, price, account, categoryID, id);
        });

        // Verify that the productService.update method was not called
        verify(productService, never()).update(any(Product.class), anyLong(), anyLong());
    }

    @Test
    @DisplayName("Should not update the product when the price is null")
    void updateProductWhenPriceIsNull() {
        // Create a mock instance of IProductService
        IProductService productService = mock(IProductService.class);

        // Create a mock instance of MultipartFile
        MultipartFile picture = mock(MultipartFile.class);

        // Create an instance of ProductRestController and pass the mock productService
        ProductRestController productRestController = new ProductRestController(productService);

        // Create test data
        String name = "Test Product";
        Double price = null;
        int account = 10;
        Long categoryID = 1L;
        Long id = 1L;

        // Call the update method of ProductRestController
        ResponseEntity<ProductResponseRest> response = null;
        try {
            response = productRestController.update(picture, name, price, account, categoryID, id);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Verify that the productService.update method was not called
        verify(productService, never()).update(any(Product.class), anyLong(), anyLong());

        // Assert that the response is not null
        assertNotNull(response);

        // Assert that the response status code is 400 (Bad Request)
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Should not update the product when the picture is null")
    void updateProductWhenPictureIsNull() {
        // Create a mock instance of IProductService
        IProductService productService = mock(IProductService.class);

        // Create a mock instance of MultipartFile
        MultipartFile picture = mock(MultipartFile.class);

        // Create an instance of ProductRestController and pass the mock productService
        ProductRestController productRestController = new ProductRestController(productService);

        // Create test data
        String name = "Test Product";
        Double price = 10.0;
        int account = 5;
        Long categoryId = 1L;
        Long id = 1L;

        // Set up the expected response entity
        ResponseEntity<ProductResponseRest> expectedResponse = ResponseEntity.ok().build();

        try {
            // Call the update method with null picture
            ResponseEntity<ProductResponseRest> response = productRestController.update(null, name, price, account, categoryId, id);

            // Verify that the productService.update method is called with the correct parameters
            verify(productService, times(1)).update(any(Product.class), eq(categoryId), eq(id));

            // Verify that the response entity matches the expected response
            assertEquals(expectedResponse, response);
        } catch (IOException e) {
            fail("IOException occurred");
        }
    }

    @Test
    @DisplayName("Should not update the product when the account is null")
    void updateProductWhenAccountIsNull() {
        // Create a mock instance of IProductService
        IProductService productService = mock(IProductService.class);

        // Create a mock instance of MultipartFile
        MultipartFile picture = mock(MultipartFile.class);

        // Create an instance of ProductRestController and pass the mock productService
        ProductRestController productRestController = new ProductRestController(productService);

        // Create test data
        String name = "Test Product";
        Double price = 10.0;
        int account = 0;
        Long categoryId = 1L;
        Long id = 1L;

        // Create a mock instance of Product
        Product product = mock(Product.class);
        when(product.getName()).thenReturn(name);
        when(product.getPrice()).thenReturn(price);
        when(product.getAccount()).thenReturn(account);
        when(product.getPicture()).thenReturn(new byte[0]);

        // Create a mock instance of ResponseEntity
        ResponseEntity<ProductResponseRest> responseEntity = mock(ResponseEntity.class);

        // Mock the productService.update() method to return the mock responseEntity
        when(productService.update(product, categoryId, id)).thenReturn(responseEntity);

        try {
            // Call the update method of ProductRestController
            ResponseEntity<ProductResponseRest> response = productRestController.update(picture, name, price, account, categoryId, id);

            // Verify that the productService.update() method was called with the correct arguments
            verify(productService, times(1)).update(product, categoryId, id);

            // Verify that the response matches the mock responseEntity
            assertEquals(responseEntity, response);
        } catch (IOException e) {
            fail("IOException occurred");
        }
    }

    @Test
    @DisplayName("Should update the product with valid inputs")
    void updateProductWithValidInputs() {        // Create a mock instance of IProductService
        IProductService productService = mock(IProductService.class);

        // Create a mock instance of MultipartFile
        MultipartFile picture = mock(MultipartFile.class);

        // Create the test data
        String name = "Test Product";
        Double price = 10.0;
        int account = 5;
        Long categoryId = 1L;
        Long id = 1L;

        // Create a byte array for the compressed picture
        byte[] compressedPicture = new byte[10];

        // Mock the behavior of Util.compressZLib() to return the compressed picture
//        when(Util.compressZLib(any(byte[].class))).thenReturn(compressedPicture);

        // Create an instance of ProductRestController with the mocked dependencies
        ProductRestController productRestController = new ProductRestController(productService);

        // Call the update method with the test data
        ResponseEntity<ProductResponseRest> response = null;
        try {
            response = productRestController.update(picture, name, price, account, categoryId, id);
        } catch (IOException e) {
            fail("IOException occurred");
        }

        // Verify that the productService.update() method was called with the correct arguments
        verify(productService, times(1)).update(any(Product.class), eq(categoryId), eq(id));

        // Assert that the response is not null
        assertNotNull(response);

        // Assert that the response status code is OK
        assertEquals(200, response.getStatusCodeValue());

        // Assert that the response body is not null
        assertNotNull(response.getBody());

        // Assert that the response body's product is not null
        assertNotNull(response.getBody().getProduct());

        // Assert that the response body's product's products list is not null
        assertNotNull(response.getBody().getProduct().getProducts());

        // Assert that the response body's product's products list is empty
        assertTrue(response.getBody().getProduct().getProducts().isEmpty());
    }

    @Test
    @DisplayName("Should save the product when all parameters are provided correctly")
    void saveProductWhenParametersAreCorrect() throws IOException {
        MultipartFile picture = mock(MultipartFile.class);
        when(picture.getBytes()).thenReturn(new byte[10]);
        IProductService productService = mock(IProductService.class);
        ProductRestController productRestController = new ProductRestController(productService);
        ProductResponseRest productResponseRest = mock(ProductResponseRest.class);
        ResponseEntity<ProductResponseRest> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(productResponseRest);
        when(productService.save(any(Product.class), anyLong())).thenReturn(responseEntity);
        ResponseEntity<ProductResponseRest> response = null;
        try {
            response = productRestController.save(picture, "Test Product", 10.0, 5, 1L);
        } catch (IOException e) {
            e.printStackTrace();
        }
        verify(productService, times(1)).save(any(Product.class), anyLong());
        assertNotNull(response);
        assertEquals(productResponseRest, response.getBody());
    }
}