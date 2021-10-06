package org.silentsoft.todoist.repository;

import org.silentsoft.todoist.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    boolean existsByUserIdAndRefreshToken(Long userId, String refreshToken);

}
