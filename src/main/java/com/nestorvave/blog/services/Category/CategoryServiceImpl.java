package com.nestorvave.blog.services.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.nestorvave.blog.domain.entities.Category.Category;
import com.nestorvave.blog.repositories.CategoryRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	@Override
	public List<Category> listCategories() {
		return categoryRepository.findAllWithPostCount();
	}

	@Override
	@Transactional
	public Category createCategory(Category category) {
		if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
			throw new IllegalArgumentException("Category already exists: " + category.getName());
		}
		return categoryRepository.save(category);
	}

	@Override
	public void deleteCategory(UUID id) {
		Optional<Category> category = categoryRepository.findById(id);
		if (category.get().getPosts().size() > 0) {
			throw new IllegalArgumentException("Category has posts");
		}
		categoryRepository.delete(category.get());
	}

	@Override
	public Category getCategoryById(UUID id) {
		return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
	}

}
