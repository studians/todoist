package org.silentsoft.todoist.core.userdetails;

import org.silentsoft.todoist.entity.UserEntity;
import org.silentsoft.todoist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsernameOrEmail(username, username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("404 - User Not Found");
        }

        return new org.silentsoft.todoist.core.userdetails.UserDetails(userEntity);
    }

}
