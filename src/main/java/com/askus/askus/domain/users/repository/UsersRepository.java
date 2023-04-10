package com.askus.askus.domain.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.askus.askus.domain.users.domain.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

	Optional<Users> findByEmail(String email);
}
