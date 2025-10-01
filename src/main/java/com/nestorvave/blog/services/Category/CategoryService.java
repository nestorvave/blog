package com.nestorvave.blog.services.Category;

import java.util.List;
import java.util.UUID;

import com.nestorvave.blog.domain.entities.Category.Category;

public interface CategoryService {

	List<Category> listCategories();

	Category createCategory(Category category);

	void deleteCategory(UUID id);

	Category getCategoryById(UUID id);

}
