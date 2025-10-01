package com.nestorvave.blog.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nestorvave.blog.domain.entities.Category.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

	@Query("SELECT c FROM Category c LEFT JOIN FETCH c.posts")
	List<Category> findAllWithPostCount();

	boolean existsByNameIgnoreCase(String name);

}
