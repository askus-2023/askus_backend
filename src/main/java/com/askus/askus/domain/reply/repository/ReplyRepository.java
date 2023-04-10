package com.askus.askus.domain.reply.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.askus.askus.domain.reply.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
