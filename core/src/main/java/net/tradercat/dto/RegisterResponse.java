package net.tradercat.dto;

public class RegisterResponse {

    private String accessTokenString;

    public RegisterResponse() {

    }

    public RegisterResponse(String accessTokenString) {
        this.accessTokenString = accessTokenString;
    }

    public String getAccessTokenString() {
        return accessTokenString;
    }

    public void setAccessTokenString(String accessTokenString) {
        this.accessTokenString = accessTokenString;
    }
}
