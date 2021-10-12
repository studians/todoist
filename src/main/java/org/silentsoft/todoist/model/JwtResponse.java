package org.silentsoft.todoist.model;

public class JwtResponse {

    private String accessToken;

    public JwtResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

}
