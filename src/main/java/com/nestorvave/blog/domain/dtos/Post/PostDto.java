package com.nestorvave.blog.domain.dtos.Post;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.nestorvave.blog.domain.dtos.Author.AuthorDto;
import com.nestorvave.blog.domain.dtos.Category.CategoryDto;
import com.nestorvave.blog.domain.dtos.Tag.TagDto;
import com.nestorvave.blog.domain.entities.Post.enums.PostStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {

	private UUID id;
	private String title;
	private String content;

	private AuthorDto author;
	private CategoryDto category;
	private Set<TagDto> tags;
	private Integer readingTime;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private PostStatus status;

}
