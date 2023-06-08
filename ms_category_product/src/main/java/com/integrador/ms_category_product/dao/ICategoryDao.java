package com.integrador.ms_category_product.dao;

import com.integrador.ms_category_product.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICategoryDao extends CrudRepository<Category, Long>{

    List<Category> findCategoriesByNameContainingIgnoreCase(String name);

}
