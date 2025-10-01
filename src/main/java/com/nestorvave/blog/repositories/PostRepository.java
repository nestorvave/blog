package com.nestorvave.blog.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nestorvave.blog.domain.entities.Category.Category;
import com.nestorvave.blog.domain.entities.Post.Post;
import com.nestorvave.blog.domain.entities.Post.enums.PostStatus;
import com.nestorvave.blog.domain.entities.Tag.Tag;
import com.nestorvave.blog.domain.entities.User.User;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
	List<Post> findAllByStatusAndCategoryAndTags(PostStatus status, Category category, Tag tag);

	List<Post> findAllByStatusAndCategory(PostStatus status, Category category);

	List<Post> findAllByStatusAndTags(PostStatus status, Tag tag);

	List<Post> findAllByStatus(PostStatus status);

	List<Post> findAllByAuthorAndStatus(User user, PostStatus status);

}
