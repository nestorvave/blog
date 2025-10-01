package com.nestorvave.blog.domain.dtos.Category;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

	private UUID id;
	private String name;
	private long postCount;
}
