package com.integrador.ms_category_product.services;

import com.integrador.ms_category_product.model.Category;
import com.integrador.ms_category_product.response.CategoryResponseRest;
import org.springframework.http.ResponseEntity;

public interface ICategoryService {
	
	public ResponseEntity<CategoryResponseRest> search();
	public ResponseEntity<CategoryResponseRest> searchById(Long id);
	public ResponseEntity<CategoryResponseRest> save(Category category);
	public ResponseEntity<CategoryResponseRest> update(Category category, Long id);
	public ResponseEntity<CategoryResponseRest> deleteById(Long id);
	public ResponseEntity<CategoryResponseRest> searchByName(String name);

}
