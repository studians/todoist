package org.silentsoft.todoist.controller;

import org.silentsoft.todoist.core.userdetails.UserDetails;
import org.silentsoft.todoist.core.userdetails.UserDetailsService;
import org.silentsoft.todoist.entity.TokenEntity;
import org.silentsoft.todoist.model.JwtRequest;
import org.silentsoft.todoist.model.JwtResponse;
import org.silentsoft.todoist.repository.TokenRepository;
import org.silentsoft.todoist.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest, HttpServletResponse response) throws Exception {
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
        setRefreshTokenToCookie(refreshToken, response);

        return ResponseEntity.ok(new JwtResponse(accessToken, jwtTokenUtil.getExpiryFromNow(accessToken)));
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public ResponseEntity<?> refreshAuthenticationToken(@CookieValue(name = "refreshToken") String refreshToken, HttpServletResponse response) throws Exception {
        if (jwtTokenUtil.notExpiredToken(refreshToken)) {
            String username = jwtTokenUtil.getUsernameFromToken(refreshToken); // TODO userId
            UserDetails userDetails = (UserDetails) userDetailsService.loadUserByUsername(username);
            long userId = userDetails.getUserId();
            TokenEntity tokenEntity = tokenRepository.getByUserIdAndRefreshToken(userId, refreshToken);
            if (tokenEntity != null) {
                String newAccessToken = jwtTokenUtil.generateAccessToken(userDetails);
                return ResponseEntity.ok(new JwtResponse(newAccessToken, jwtTokenUtil.getExpiryFromNow(newAccessToken)));
            }
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private void setRefreshTokenToCookie(String refreshToken, HttpServletResponse response) {
        int maxAge = (int)((jwtTokenUtil.getExpirationDateFromToken(refreshToken).getTime() - System.currentTimeMillis()) / 1000);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        // TODO cookie.setHttpOnly(true);

        response.addCookie(cookie);
    }

}