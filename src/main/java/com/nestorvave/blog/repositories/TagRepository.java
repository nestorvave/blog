package com.nestorvave.blog.repositories;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nestorvave.blog.domain.entities.Tag.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {

	@Query("SELECT tag from Tag tag LEFT JOIN FETCH tag.posts ")
	List<Tag> findAllWithPostCount();

	List<Tag> findTagsByNameIn(Set<String> names);
}
