package com.nestorvave.blog.services.Post;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.nestorvave.blog.domain.entities.Post.enums.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePostRequest {

	private String title;

	private String content;

	private UUID categoryId;

	@Builder.Default
	private Set<UUID> tagIds = new HashSet<>();

	private PostStatus status;
}
