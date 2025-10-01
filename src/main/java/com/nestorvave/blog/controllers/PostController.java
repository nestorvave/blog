package com.nestorvave.blog.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nestorvave.blog.domain.dtos.Post.CreatePostRequestDto;
import com.nestorvave.blog.domain.dtos.Post.PostDto;
import com.nestorvave.blog.domain.dtos.Post.UpdatePostRequestDto;
import com.nestorvave.blog.domain.entities.Post.Post;
import com.nestorvave.blog.domain.entities.User.User;
import com.nestorvave.blog.mappers.Post.PostMapper;
import com.nestorvave.blog.services.Post.CreatePostRequest;
import com.nestorvave.blog.services.Post.PostService;
import com.nestorvave.blog.services.Post.UpdatePostRequest;
import com.nestorvave.blog.services.User.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;
	private final PostMapper postMapper;
	private final UserService userService;

	@GetMapping()
	public ResponseEntity<List<PostDto>> getAllPost(
			@RequestParam(required = false) UUID categoryId,
			@RequestParam(required = false) UUID tagId) {

		List<Post> posts = postService.getAllPost(categoryId, tagId);
		List<PostDto> postDtos = posts.stream().map(postMapper::toDto).toList();
		return ResponseEntity.ok(postDtos);
	}

	@GetMapping("/drafts")
	public ResponseEntity<List<PostDto>> getDrafts(@RequestAttribute UUID userId) {
		User loggedInUser = userService.getUserById(userId);
		List<Post> draftPosts = postService.getDraftPost(loggedInUser);
		List<PostDto> postDtos = draftPosts.stream().map(postMapper::toDto).toList();
		return ResponseEntity.ok(postDtos);
	}

	@PostMapping
	public ResponseEntity<PostDto> createPost(
			@Valid @RequestBody CreatePostRequestDto createPostRequestDto,
			@RequestAttribute UUID userId) {

		CreatePostRequest createPostRequest = postMapper.toCreatePostRequest(createPostRequestDto);
		Post createdPost = postService.createPost(userService.getUserById(userId), createPostRequest);
		PostDto createdPostDto = postMapper.toDto(createdPost);
		return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);

	}

	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(
			@PathVariable UUID id,
			@Valid @RequestBody UpdatePostRequestDto updatePostRequestDto) {

		UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDto);
		Post updatedPost = postService.updatePost(id, updatePostRequest);
		PostDto updatedPostDto = postMapper.toDto(updatedPost);
		return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable UUID id) {
		Post post = postService.getPostById(id);
		PostDto postDto = postMapper.toDto(post);
		return ResponseEntity.ok(postDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
		postService.deletePost(id);
		return ResponseEntity.ok().build();
	}

}
