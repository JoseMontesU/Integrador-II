package com.integrador.ms_category_product.response;

import java.util.List;

import com.integrador.ms_category_product.model.Product;
import lombok.Data;

@Data
public class ProductResponse {
	
	List<Product> products;

}
