package com.integrador.ms_category_product.services;

import com.integrador.ms_category_product.dao.ICategoryDao;
import com.integrador.ms_category_product.dao.IProductDao;
import com.integrador.ms_category_product.model.Category;
import com.integrador.ms_category_product.model.Product;
import com.integrador.ms_category_product.response.ProductResponseRest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ICategoryDao categoryDao;

    @Mock
    private IProductDao productDao;

    @InjectMocks
    private ProductServiceImpl productService;

//    @Test
//    @DisplayName("Should not update the product when the product id is not valid")
//    void updateProductWhenProductIdIsNotValid() {
//        Long productId = 1L;
//        Long categoryId = 2L;
//        Product product = new Product();
//        product.setId(productId);
//        product.setName("Test Product");
//        product.setPrice(BigDecimal.valueOf(10.99));
//        product.setAccount(5);
//        Category category = new Category();
//        category.setId(categoryId);
//        category.setName("Test Category");
//        category.setDescription("Test Category Description");
//
//        when(productDao.findById(productId)).thenReturn(Optional.empty());
//
//        ResponseEntity<ProductResponseRest> response = productService.update(product, categoryId, productId);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals("respuesta nok", response.getBody().getMetadata());
//        assertEquals("-1", response.getBody().getCode());
//        assertEquals("Producto no actualizado", response.getBody().getMessage());
//
//        verify(productDao, times(1)).findById(productId);
//        verify(categoryDao, never()).findById(categoryId);
//        verify(productDao, never()).save(any(Product.class));
//    }

//    @Test
//    @DisplayName("Should not update the product when the category id is not valid")
//    void updateProductWhenCategoryIdIsNotValid() {
//        Long categoryId = 1L;
//        Long productId = 1L;
//        Product product = new Product();
//        product.setId(productId);
//        product.setName("Test Product");
//        product.setPrice(BigDecimal.valueOf(10.0));
//        product.setAccount(100);
//        byte[] picture = new byte[]{1, 2, 3};
//        product.setPicture(picture);
//
//        when(categoryDao.findById(categoryId)).thenReturn(Optional.empty());
//
//        ResponseEntity<ProductResponseRest> response = productService.update(product, categoryId, productId);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals("respuesta nok", response.getBody().getMetadata().getStatus());
//        assertEquals("-1", response.getBody().getMetadata().getCode());
//        assertEquals("Categoria no encontrada asociada al producto", response.getBody().getMetadata().getMessage());
//
//        verify(categoryDao, times(1)).findById(categoryId);
//        verify(productDao, never()).findById(productId);
//        verify(productDao, never()).save(any(Product.class));
//    }

//    @Test
//    @DisplayName("Should throw an exception when an error occurs during product update")
//    void updateProductWhenErrorOccurs() {
//        Long categoryId = 1L;
//        Long productId = 1L;
//        Product product = new Product();
//        product.setId(productId);
//        product.setName("Test Product");
//        product.setPrice(BigDecimal.valueOf(10.0));
//        product.setAccount(5);
//        byte[] picture = new byte[]{1, 2, 3};
//        product.setPicture(picture);
//
//        when(categoryDao.findById(categoryId)).thenReturn(Optional.empty());
//        when(productDao.findById(productId)).thenReturn(Optional.of(product));
//        when(productDao.save(product)).thenReturn(null);
//
//        ResponseEntity<ProductResponseRest> response = productService.update(product, categoryId, productId);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals("respuesta nok", response.getBody().getMetadata().getStatus());
//        assertEquals("-1", response.getBody().getMetadata().getCode());
//        assertEquals("Producto no actualizado", response.getBody().getMetadata().getMessage());
//
//        verify(categoryDao, times(1)).findById(categoryId);
//        verify(productDao, times(1)).findById(productId);
//        verify(productDao, times(1)).save(product);
//    }

    @Test
    @DisplayName("Should update the product when the category and product id are valid")
    void updateProductWhenCategoryIdAndProductIdAreValid() {
        Long categoryId = 1L;
        Long productId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        Product product = new Product();
        product.setId(productId);
        product.setCategory(category);
        product.setName("Test Product");
        product.setPrice(10.0);
        product.setAccount(5);
        byte[] picture = new byte[]{1, 2, 3};
        product.setPicture(picture);

        Optional<Category> optionalCategory = Optional.of(category);
        Optional<Product> optionalProduct = Optional.of(product);

        when(categoryDao.findById(categoryId)).thenReturn(optionalCategory);
        when(productDao.findById(productId)).thenReturn(optionalProduct);
        when(productDao.save(product)).thenReturn(product);

        ResponseEntity<ProductResponseRest> response = productService.update(product, categoryId, productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
//        assertEquals("200 OK", response.getStatusCode());
//        assertEquals("00", response.getBody().getMetadata().);
//        assertEquals("Producto actualizado", response.getBody().getMetadata().getMessage());
        assertEquals(1, response.getBody().getProduct().getProducts().size());
        assertEquals(productId, response.getBody().getProduct().getProducts().get(0).getId());
        assertEquals(categoryId, response.getBody().getProduct().getProducts().get(0).getCategory().getId());
        assertEquals("Test Product", response.getBody().getProduct().getProducts().get(0).getName());
        assertEquals(10.0, response.getBody().getProduct().getProducts().get(0).getPrice());
        assertEquals(5, response.getBody().getProduct().getProducts().get(0).getAccount());
        assertArrayEquals(picture, response.getBody().getProduct().getProducts().get(0).getPicture());

        verify(categoryDao, times(1)).findById(categoryId);
        verify(productDao, times(1)).findById(productId);
        verify(productDao, times(1)).save(product);
    }

}