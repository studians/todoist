package org.silentsoft.todoist.model;

public class JwtResponse {

    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

}
