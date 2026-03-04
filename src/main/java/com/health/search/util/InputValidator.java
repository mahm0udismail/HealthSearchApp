package com.health.search.util;

public final class InputValidator {

  public static final int MAX_QUERY_LENGTH = 100;

  private InputValidator() {}

  public static boolean isValidQuery(String query) {
    return query != null && !query.isBlank() && query.trim().length() <= MAX_QUERY_LENGTH;
  }

  public static String getValidationError(String query) {
    if (query == null || query.isBlank()) return "Please enter a search term.";
    if (query.trim().length() > MAX_QUERY_LENGTH)
      return "Search term is too long (max " + MAX_QUERY_LENGTH + " characters).";
    return null;
  }
}
