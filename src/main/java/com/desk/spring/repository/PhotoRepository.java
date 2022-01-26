package com.desk.spring.repository;

import com.desk.spring.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    List<Photo> findAllByBoardId(Long boardId);
}
