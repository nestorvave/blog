package com.nestorvave.blog.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nestorvave.blog.domain.dtos.Category.CategoryDto;
import com.nestorvave.blog.domain.dtos.Category.CreateCategoryRequest;
import com.nestorvave.blog.domain.entities.Category.Category;
import com.nestorvave.blog.mappers.Category.CategoryMapper;
import com.nestorvave.blog.services.Category.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;
	private final CategoryMapper categoryMapper;

	@GetMapping
	public ResponseEntity<List<CategoryDto>> listCategories() {
		List<Category> categories = categoryService.listCategories();
		return ResponseEntity.ok(categories.stream().map(categoryMapper::toDto).toList());

	}

	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
		Category categoryToCreate = categoryMapper.toEntity(createCategoryRequest);
		Category createdCategory = categoryService.createCategory(categoryToCreate);
		return new ResponseEntity<>(categoryMapper.toDto(createdCategory), HttpStatus.CREATED);
	}
	

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategory(@Valid @PathVariable UUID id) {
		categoryService.deleteCategory(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
