package org.silentsoft.todoist.repository;

import org.silentsoft.todoist.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {

    UsersEntity findByUsernameOrEmail(String usernameOrEmail);

}
