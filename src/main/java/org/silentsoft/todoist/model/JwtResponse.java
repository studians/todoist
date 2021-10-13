package org.silentsoft.todoist.model;

public class JwtResponse {

    private String accessToken;

    private long expiry;

    public JwtResponse(String accessToken, long expiry) {
        this.accessToken = accessToken;
        this.expiry = expiry;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public long getExpiry() {
        return expiry;
    }
}
