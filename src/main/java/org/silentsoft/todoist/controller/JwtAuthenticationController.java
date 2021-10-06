package org.silentsoft.todoist.controller;

import org.silentsoft.todoist.core.userdetails.UserDetailsService;
import org.silentsoft.todoist.model.JwtRequest;
import org.silentsoft.todoist.model.JwtResponse;
import org.silentsoft.todoist.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        String token = generateTokenByUserDetails(loadUserByUsername(authenticationRequest.getUsername()));
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private UserDetails loadUserByUsername(String username) {
        return userDetailsService.loadUserByUsername(username);
    }

    private String generateTokenByUserDetails(UserDetails userDetails) {
        return jwtTokenUtil.generateToken(userDetails);
    }
}