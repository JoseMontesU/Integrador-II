package com.integrador.ms_category_product.controller;

import com.integrador.ms_category_product.model.Category;
import com.integrador.ms_category_product.response.CategoryResponse;
import com.integrador.ms_category_product.response.CategoryResponseRest;
import com.integrador.ms_category_product.services.ICategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CategoryRestController")
class CategoryRestControllerTest {

    @Mock
    private ICategoryService service;

    @InjectMocks
    private CategoryRestController controller;


//    @Test
//    @DisplayName("Should throw an exception when the id does not exist")
//    void updateCategoryWhenIdDoesNotExistThenThrowException() {
//        Long id = 1L;
//        Category category = new Category();
//        category.setId(id);
//        category.setName("Test Category");
//        category.setDescription("Test Description");
//
//        when(service.update(category, id)).thenThrow(new RuntimeException("Category not found"));
//
//        ResponseEntity<CategoryResponseRest> response = controller.update(category, id);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertEquals("Category not found", response.getBody().getMessage());
//
//        verify(service, times(1)).update(category, id);
//    }


    @Test
    @DisplayName("Should return not found when the id does not exist")
    void deleteCategoryWhenIdDoesNotExist() {
        Long id = 1L;
        ResponseEntity<CategoryResponseRest> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        when(service.deleteById(id)).thenReturn(response);

        ResponseEntity<CategoryResponseRest> result = controller.delete(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(service, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Should delete the category when the id exists")
    void deleteCategoryWhenIdExists() {
        Long categoryId = 1L;
        ResponseEntity<CategoryResponseRest> expectedResponse = new ResponseEntity<>(HttpStatus.OK);
        when(service.deleteById(categoryId)).thenReturn(expectedResponse);

        ResponseEntity<CategoryResponseRest> actualResponse = controller.delete(categoryId);

        assertEquals(expectedResponse, actualResponse);
        verify(service, times(1)).deleteById(categoryId);
    }

    @Test
    @DisplayName("Should update the category when the id exists")
    void updateCategoryWhenIdExists() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");
        category.setDescription("Test Description");
        CategoryResponseRest categoryResponseRest = new CategoryResponseRest();
        categoryResponseRest.setCategoryResponse(new CategoryResponse());
        categoryResponseRest.getCategoryResponse().setCategory(new ArrayList<>());
        categoryResponseRest.getCategoryResponse().getCategory().add(category);
        ResponseEntity<CategoryResponseRest> responseEntity = new ResponseEntity<>(categoryResponseRest, HttpStatus.OK);
        when(service.update(category, category.getId())).thenReturn(responseEntity);
        ResponseEntity<CategoryResponseRest> response = controller.update(category, category.getId());
        verify(service, times(1)).update(category, category.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseEntity.getBody(), response.getBody());
    }

    @Test
    @DisplayName("Should return updated category response when the category is successfully updated")
    void updateCategoryReturnUpdatedCategoryResponse() {
        // Create a sample category
        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");
        category.setDescription("Test Description");

        // Create a sample updated category
        Category updatedCategory = new Category();
        updatedCategory.setId(1L);
        updatedCategory.setName("Updated Category");
        updatedCategory.setDescription("Updated Description");

        // Create a sample response entity
        ResponseEntity<CategoryResponseRest> responseEntity = new ResponseEntity<>(HttpStatus.OK);

        // Mock the service method to return the sample response entity
        when(service.update(updatedCategory, category.getId())).thenReturn(responseEntity);

        // Call the update method of the controller
        ResponseEntity<CategoryResponseRest> response = controller.update(updatedCategory, category.getId());

        // Verify that the service method was called with the correct parameters
        verify(service, times(1)).update(updatedCategory, category.getId());

        // Verify that the response entity returned by the controller is the same as the mocked response entity
        assertEquals(responseEntity, response);
    }

    @Test
    @DisplayName("Should save the category and return a response entity with the saved category")
    void saveCategory() {
        Category category = new Category();
        category.setName("Test Category");
        category.setDescription("Test Description");
        CategoryResponseRest expectedResponse = new CategoryResponseRest();
        expectedResponse.setCategoryResponse(new CategoryResponse());
        expectedResponse.getCategoryResponse().getCategory().add(category);
        when(service.save(category)).thenReturn(ResponseEntity.ok(expectedResponse));
        ResponseEntity<CategoryResponseRest> response = controller.save(category);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(service, times(1)).save(category);
    }
}