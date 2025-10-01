package com.nestorvave.blog.services.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.nestorvave.blog.domain.entities.Tag.Tag;
import com.nestorvave.blog.repositories.TagRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

	private final TagRepository tagRepository;

	@Override
	public List<Tag> getAllTags() {
		return tagRepository.findAllWithPostCount();
	}

	@Transactional
	@Override
	public List<Tag> createTags(Set<String> tagNames) {
		List<Tag> existingTags = tagRepository.findTagsByNameIn(tagNames);
		Set<String> existingTagNames = existingTags.stream().map(Tag::getName).collect(Collectors.toSet());
		List<Tag> newTagNames = tagNames.stream().filter(name -> !existingTagNames.contains(name))
				.map(name -> Tag.builder().name(name).build()).toList();

		List<Tag> savedTags = new ArrayList<>();
		if (!newTagNames.isEmpty()) {
			savedTags = tagRepository.saveAll(newTagNames);
		}
		savedTags.addAll(existingTags);
		return savedTags;
	}

	@Override
	@Transactional
	public void deleteTag(UUID id) {
		tagRepository.findById(id).ifPresent(tag -> {
			if (!tag.getPosts().isEmpty()) {
				throw new IllegalStateException("Tag is associated with posts");
			}
			tagRepository.deleteById(id);
		});
	}

	@Override
	public Tag getTagById(UUID id) {
		return tagRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Tag not found with id: " + id));
	}

	@Override
	public List<Tag> getTagsByIds(Set<UUID> tagIds) {
		List<Tag> foundTags = tagRepository.findAllById(tagIds);
		if (foundTags.size() != tagIds.size()) {
			throw new EntityNotFoundException("Not all tags found with ids: " + tagIds);
		}
		return foundTags;
	}

}
