package com.company.inventory.dao;

import org.springframework.data.repository.CrudRepository;

import com.company.inventory.model.Category;

import java.util.List;

public interface ICategoryDao extends CrudRepository<Category, Long>{

    List<Category> findCategoriesByNameContainingIgnoreCase(String name);

}
