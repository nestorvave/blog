package com.nestorvave.blog.services.Post;

import java.util.List;
import java.util.UUID;

import com.nestorvave.blog.domain.entities.Post.Post;
import com.nestorvave.blog.domain.entities.User.User;

public interface PostService {
	List<Post> getAllPost(UUID categoryId, UUID tagId);

	List<Post> getDraftPost(User user);
	
	Post createPost(User user, CreatePostRequest createPostRequest);

	Post updatePost(UUID id, UpdatePostRequest updatePostRequest);

	Post getPostById(UUID id);
	
	void deletePost(UUID id);

}
