package org.silentsoft.todoist.repository;

import org.silentsoft.todoist.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findAllByUserId(Long userId);

    TaskEntity findByIdAndUserId(Long id, Long userId);

}
