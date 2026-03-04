package com.health.search.model;

public final class SearchItem {

  public enum Type {
    DOCTOR,
    SERVICE
  }

  private final String name;
  private final String description;
  private final Type type;

  public SearchItem(String name, String description, Type type) {
    if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be blank");
    if (type == null) throw new IllegalArgumentException("Type cannot be null");
    this.name = name.trim();
    this.description = description != null ? description.trim() : "";
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Type getType() {
    return type;
  }

  public String getDisplayLabel() {
    return String.format("[%s] %s — %s", type.name(), name, description);
  }

  @Override
  public String toString() {
    return getDisplayLabel();
  }
}
