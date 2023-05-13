package com.ftn.sbnz.tim5.service.security;

public class JwtProperties {
    public static final String SECRET = "pass";
    public static final int EXPIRATION_TIME = 14_400_000; // 4 hours
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
