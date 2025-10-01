package com.nestorvave.blog.services.Tag;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.nestorvave.blog.domain.entities.Tag.Tag;

public interface TagService {

	List<Tag> getAllTags();
	List<Tag> createTags(Set<String> tagNames);

	void deleteTag(UUID id);

	Tag getTagById(UUID id);
	
	List<Tag> getTagsByIds(Set<UUID> tagIds);

}
