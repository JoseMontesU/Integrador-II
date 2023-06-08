package com.integrador.ms_category_product.response;

import java.util.List;

import com.integrador.ms_category_product.model.Category;
import lombok.Data;

@Data
public class CategoryResponse {
	
	private List<Category> category;

}
