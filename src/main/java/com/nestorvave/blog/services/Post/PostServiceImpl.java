package com.nestorvave.blog.services.Post;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nestorvave.blog.domain.entities.Category.Category;
import com.nestorvave.blog.domain.entities.Post.Post;
import com.nestorvave.blog.domain.entities.Post.enums.PostStatus;
import com.nestorvave.blog.domain.entities.Tag.Tag;
import com.nestorvave.blog.domain.entities.User.User;
import com.nestorvave.blog.repositories.PostRepository;
import com.nestorvave.blog.services.Category.CategoryService;
import com.nestorvave.blog.services.Tag.TagService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final CategoryService categoryService;
	private final TagService tagService;
	private static final int WORDS_PER_MINUTE = 200;

	@Override
	@Transactional(readOnly = true)
	public List<Post> getAllPost(UUID categoryId, UUID tagId) {
		if (categoryId != null && tagId != null) {
			Category category = categoryService.getCategoryById(categoryId);
			Tag tag = tagService.getTagById(tagId);
			return postRepository.findAllByStatusAndCategoryAndTags(PostStatus.PUBLISHED, category, tag);
		}
		if (categoryId != null) {
			Category category = categoryService.getCategoryById(categoryId);
			return postRepository.findAllByStatusAndCategory(PostStatus.PUBLISHED, category);
		}
		if (tagId != null) {
			Tag tag = tagService.getTagById(tagId);
			return postRepository.findAllByStatusAndTags(PostStatus.PUBLISHED, tag);
		}
		return postRepository.findAllByStatus(PostStatus.PUBLISHED);
	}

	@Override
	public List<Post> getDraftPost(User user) {
		return postRepository.findAllByAuthorAndStatus(user, PostStatus.DRAFT);
	}

	@Override
	@Transactional
	public Post createPost(User user, CreatePostRequest createPostRequest) {
		Category category = categoryService.getCategoryById(createPostRequest.getCategoryId());
		Set<UUID> tags = createPostRequest.getTagIds();
		List<Tag> tagSet = tagService.getTagsByIds(tags);

		Post post = Post.builder()
				.title(createPostRequest.getTitle())
				.content(createPostRequest.getContent())
				.status(createPostRequest.getStatus())
				.author(user)
				.readTime(calculateReadingTime(createPostRequest.getContent()))
				.category(category)
				.tags(new HashSet<>(tagSet))
				.build();
		return postRepository.save(post);
	}

	private Integer calculateReadingTime(String content) {
		if (content == null || content.isEmpty()) {
			return 0;
		}
		int readingTime = content.trim().split("\\s+").length;
		return (int) Math.ceil((double) readingTime / WORDS_PER_MINUTE);
	}

	@Override
	@Transactional
	public Post updatePost(UUID id, UpdatePostRequest updatePostRequest) {
		Post existingPost = postRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));

		existingPost.setTitle(updatePostRequest.getTitle());
		existingPost.setContent(updatePostRequest.getContent());
		existingPost.setStatus(updatePostRequest.getStatus());
		existingPost.setReadTime(calculateReadingTime(updatePostRequest.getContent()));

		UUID updatePostRequestCategoryId = updatePostRequest.getCategoryId();
		if (!existingPost.getCategory().getId().equals(updatePostRequestCategoryId)) {
			Category category = categoryService.getCategoryById(updatePostRequestCategoryId);
			existingPost.setCategory(category);
		}
		Set<UUID> existingPostTagIds = existingPost.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
		Set<UUID> updatePostRequestTagIds = updatePostRequest.getTagIds();

		if (!existingPostTagIds.equals(updatePostRequestTagIds)) {
			List<Tag> tags = tagService.getTagsByIds(updatePostRequestTagIds);
			existingPost.setTags(new HashSet<>(tags));
		}
		return postRepository.save(existingPost);

	}

	@Override
	public Post getPostById(UUID id) {
		return postRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
	}

	@Override
	public void deletePost(UUID id) {
		Post post = getPostById(id);
		postRepository.delete(post);
	}
}
