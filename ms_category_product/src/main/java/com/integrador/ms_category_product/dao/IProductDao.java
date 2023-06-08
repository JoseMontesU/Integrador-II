package com.integrador.ms_category_product.dao;


import java.util.List;

import com.integrador.ms_category_product.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IProductDao extends CrudRepository<Product, Long>{
	
	
	@Query("select p from Product p where p.name like %?1%")
	List<Product> findByNameLike(String name);
	
	
	List<Product> findByNameContainingIgnoreCase(String name);


}
