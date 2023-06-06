package com.company.inventory.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.services.ICategoryService;
import com.company.inventory.util.CategoryExcelExporter;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class CategoryRestController {
	
	@Autowired
	private ICategoryService service;

	@GetMapping("/categories")
	public ResponseEntity<CategoryResponseRest> searchCategories() {
		
		ResponseEntity<CategoryResponseRest> response = service.search();
		return response;
	}

	@GetMapping("/categories/filter/{name}")
	public ResponseEntity<CategoryResponseRest> searchByName(@PathVariable String name){
		ResponseEntity<CategoryResponseRest> response = service.searchByName(name);
		return response;
	}

	@GetMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseRest> searchCategoriesById(@PathVariable Long id) {
		
		ResponseEntity<CategoryResponseRest> response = service.searchById(id);
		return response;
	}

	@PostMapping("/categories")
	public ResponseEntity<CategoryResponseRest> save(@RequestBody Category category) {
		
		ResponseEntity<CategoryResponseRest> response = service.save(category);
		return response;
	}

	@PutMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseRest> update(@RequestBody Category category, @PathVariable Long id) {
		
		ResponseEntity<CategoryResponseRest> response = service.update(category, id);
		return response;
	}

	@DeleteMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseRest> delete(@PathVariable Long id) {
		
		ResponseEntity<CategoryResponseRest> response = service.deleteById(id);
		return response;
	}

	@GetMapping("/categories/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		
		response.setContentType("application/octet-stream");
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=result_category";
		response.setHeader(headerKey, headerValue);
		
		ResponseEntity<CategoryResponseRest> categoryResponse = service.search();
		
		CategoryExcelExporter excelExporter = new CategoryExcelExporter(
				categoryResponse.getBody().getCategoryResponse().getCategory());
		
		excelExporter.export(response);
				
		
	}

}
