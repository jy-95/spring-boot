package net.dsa.web6.repository;

import org.springframework.stereotype.Repository;

import net.dsa.web6.entity.CommentEntity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 글 정보 Repository
 */
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

}
