package com.exelaration.abstractmemery.constants;

public class SecurityConstants {
  public static final String SECRET = "SecretKeyToGenJWTs";
  public static final long EXPIRATION_TIME = 864_000_000; // 10 days
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public static final String SIGN_UP_URL = "/users/sign-up";
  public static final String SETTINGS_URL = "/users/settings";
  public static final String GALLERY_URL = "/meme";
  public static final String DIRECT_MEME_LINK = "/meme/*";
}
