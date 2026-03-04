package com.health.search;

import java.io.IOException;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HealthApp extends Application {

  private static final Logger LOG = Logger.getLogger(HealthApp.class.getName());

  @Override
  public void start(Stage primaryStage) {
    try {
      FXMLLoader loader =
          new FXMLLoader(getClass().getResource("/com/health/search/HomeView.fxml"));
      Scene scene = new Scene(loader.load(), 700, 520);
      primaryStage.setTitle("HealthConnect — Search");
      primaryStage.setScene(scene);
      primaryStage.setMinWidth(500);
      primaryStage.setMinHeight(400);
      primaryStage.show();
    } catch (IOException ex) {
      LOG.severe("Failed to load FXML: " + ex.getMessage());
      throw new RuntimeException("Cannot start application — FXML missing", ex);
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
