package com.nestorvave.blog.mappers.Post;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.nestorvave.blog.domain.dtos.Post.CreatePostRequestDto;
import com.nestorvave.blog.domain.dtos.Post.PostDto;
import com.nestorvave.blog.domain.dtos.Post.UpdatePostRequestDto;
import com.nestorvave.blog.domain.entities.Post.Post;
import com.nestorvave.blog.services.Post.CreatePostRequest;
import com.nestorvave.blog.services.Post.UpdatePostRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

	@Mapping(target = "category", source = "category")
	@Mapping(target = "tags", source = "tags")
	@Mapping(target = "author", source = "author")
	PostDto toDto(Post post);

	CreatePostRequest toCreatePostRequest(CreatePostRequestDto createPostRequestDto);

	UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto updatePostRequestDto);
}
