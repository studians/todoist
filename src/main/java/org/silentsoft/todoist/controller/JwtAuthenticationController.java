package org.silentsoft.todoist.controller;

import org.silentsoft.todoist.core.userdetails.UserDetails;
import org.silentsoft.todoist.core.userdetails.UserDetailsService;
import org.silentsoft.todoist.entity.TokenEntity;
import org.silentsoft.todoist.model.JwtRequest;
import org.silentsoft.todoist.model.JwtResponse;
import org.silentsoft.todoist.repository.TokenRepository;
import org.silentsoft.todoist.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenRepository tokenRepository;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }

        UserDetails userDetails = (UserDetails) userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
        String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);
        if (tokenRepository.existsByUserIdAndRefreshToken(userDetails.getUserId(), refreshToken) == false) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            TokenEntity tokenEntity = new TokenEntity();
            tokenEntity.setUserId(userDetails.getUserId());
            tokenEntity.setRefreshToken(refreshToken);
            tokenEntity.setDevice(jwtRequest.getDevice());
            tokenEntity.setCreatedAt(timestamp);
            tokenEntity.setUpdatedAt(timestamp);

            tokenRepository.save(tokenEntity);
        }

        return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken));
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}