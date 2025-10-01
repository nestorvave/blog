package com.nestorvave.blog.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nestorvave.blog.domain.dtos.Tag.CreateTagsRequest;
import com.nestorvave.blog.domain.dtos.Tag.TagDto;
import com.nestorvave.blog.domain.entities.Tag.Tag;
import com.nestorvave.blog.mappers.Tags.TagMapper;
import com.nestorvave.blog.services.Tag.TagService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

	private final TagService tagService;
	private final TagMapper tagMapper;

	@GetMapping
	public ResponseEntity<List<TagDto>> getAllTags() {
		List<Tag> tags = tagService.getAllTags();
		List<TagDto> tagResponses = tags.stream().map(tagMapper::toTagDto).toList();
		return ResponseEntity.ok(tagResponses);
	}

	@PostMapping()
	public ResponseEntity<List<TagDto>> createTags(@RequestBody CreateTagsRequest createTagsRequest) {
		List<Tag> saved = tagService.createTags(createTagsRequest.getNames());
		List<TagDto> tagResponses = saved.stream().map(tagMapper::toTagDto).toList();
		return new ResponseEntity<>(tagResponses, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
		tagService.deleteTag(id);
		return ResponseEntity.noContent().build();
	}

}
