package com.nestorvave.blog.domain.dtos.Tag;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTagsRequest {

	@NotEmpty(message = "Name is required")
	@Size(max = 10, message = "Name must be less than 10 characters")

	private Set<@NotEmpty(message = "Name is required") @Size(max = 30, message = "Name must be less than 30 characters") String> names;

}
