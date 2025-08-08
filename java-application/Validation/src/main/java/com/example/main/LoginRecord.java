package com.example.main;

import java.time.Instant;

public class LoginRecord {
    private final String email;
    private final String password; // demo only; do not do this in real apps
    private final Instant time;
    private final String ip;
    private final String userAgent;

    public LoginRecord(String email, String password, Instant time, String ip, String userAgent) {
        this.email = email;
        this.password = password;
        this.time = time;
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Instant getTime() { return time; }
    public String getIp() { return ip; }
    public String getUserAgent() { return userAgent; }
}
