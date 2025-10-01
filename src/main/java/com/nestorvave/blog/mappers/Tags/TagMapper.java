package com.nestorvave.blog.mappers.Tags;


import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.nestorvave.blog.domain.dtos.Tag.TagDto;
import com.nestorvave.blog.domain.entities.Post.Post;
import com.nestorvave.blog.domain.entities.Post.enums.PostStatus;
import com.nestorvave.blog.domain.entities.Tag.Tag;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

	@Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
	TagDto toTagDto(Tag tag);

	@Named("calculatePostCount")
	default Integer calculatePostCount(Set<Post> post) {
		if (null == post) {
			return 0;
		}
		return (int) post.stream().filter(p -> PostStatus.PUBLISHED.equals(p.getStatus()))
				.count();
	}

}
