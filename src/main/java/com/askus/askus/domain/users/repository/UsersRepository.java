package com.askus.askus.domain.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.askus.askus.domain.users.domain.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
