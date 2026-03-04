module com.health.search {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.logging;

  opens com.health.search to
      javafx.fxml;
  opens com.health.search.controller to
      javafx.fxml;

  exports com.health.search;
  exports com.health.search.controller;
  exports com.health.search.model;
  exports com.health.search.service;
  exports com.health.search.exception;
  exports com.health.search.util;
}
