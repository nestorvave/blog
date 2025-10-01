package com.nestorvave.blog.services.User;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.nestorvave.blog.domain.entities.User.User;
import com.nestorvave.blog.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public User getUserById(UUID id) {
		return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
	}

}
