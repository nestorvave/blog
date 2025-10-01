package com.nestorvave.blog.domain.dtos.Tag;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagDto {
	private String name;
	private UUID id;
	private Integer postCount;

}
