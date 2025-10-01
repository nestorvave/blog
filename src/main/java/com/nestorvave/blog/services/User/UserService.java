package com.nestorvave.blog.services.User;

import java.util.UUID;

import com.nestorvave.blog.domain.entities.User.User;

public interface UserService {
	User getUserById(UUID id);
}
