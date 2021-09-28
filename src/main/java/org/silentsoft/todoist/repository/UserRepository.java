package org.silentsoft.todoist.repository;

import org.silentsoft.todoist.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsernameOrEmail(String username, String email);

}
