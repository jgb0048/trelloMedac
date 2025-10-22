package com.medac.trello.api.model.repository;


import com.medac.trello.api.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Set<Board> findAllByCreatedBy(Long ownerId);
}