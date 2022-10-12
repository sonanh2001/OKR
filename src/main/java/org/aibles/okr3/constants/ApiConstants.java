package org.aibles.okr3.constants;

public class ApiConstants {
  public static final String VERSION = "v1";
  public static final String OBJECTIVE_RESOURCES = "objectives";
  public static final String KEY_RESULTS_RESOURCES = "key-results";
  public static final String API_SOURCES = "api";
  public static final String OBJECTIVES_URI =
      '/' + API_SOURCES + '/' + VERSION + '/' + OBJECTIVE_RESOURCES;

  public static final String KEY_RESULTS_URI =
      '/' + API_SOURCES + '/' + VERSION + '/' + KEY_RESULTS_RESOURCES;

  public static final String USER_RESOURCES = "users";

  public static final String REFRESH_TOKEN_RESOURCES = "refresh-tokens";
  public static final String LOGIN_URI =
      '/' + API_SOURCES + '/' + VERSION + '/' + USER_RESOURCES + '/' + "login";
  public static final String USERS_API_URI =
      '/' + API_SOURCES + '/' + VERSION + '/' + USER_RESOURCES;

  public static final String REFRESH_TOKEN_URI =
      '/' + API_SOURCES + '/' + VERSION + '/' + REFRESH_TOKEN_RESOURCES;
}
