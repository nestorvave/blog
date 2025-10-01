package com.nestorvave.blog.mappers.Category;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.nestorvave.blog.domain.dtos.Category.CategoryDto;
import com.nestorvave.blog.domain.dtos.Category.CreateCategoryRequest;
import com.nestorvave.blog.domain.entities.Category.Category;
import com.nestorvave.blog.domain.entities.Post.Post;
import com.nestorvave.blog.domain.entities.Post.enums.PostStatus;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

	@Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
	CategoryDto toDto(Category category);

	Category toEntity(CreateCategoryRequest createCategoryRequest);

	@Named("calculatePostCount")
	default long calculatePostCount(List<Post> post) {
		if (null == post) {
			return 0;
		}
		return post.stream().filter(p -> PostStatus.PUBLISHED.equals(p.getStatus())).count();
	}

}
