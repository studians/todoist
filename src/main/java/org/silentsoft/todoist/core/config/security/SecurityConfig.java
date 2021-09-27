package org.silentsoft.todoist.core.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin();

        http.authorizeRequests()
                .antMatchers("/login/*").permitAll()
                .antMatchers("/logout/*").permitAll()
                .antMatchers("/h2-console/*").hasRole("ADMIN")
                .anyRequest().authenticated();

        http.formLogin()
                .loginProcessingUrl("/login")
                .failureUrl("/login?error")
                .defaultSuccessUrl("/")
                .usernameParameter("username")
                .passwordParameter("password");

        http.httpBasic();

        http.csrf().disable();
    }

}