package com.health.search.controller;

import com.health.search.exception.SearchException;
import com.health.search.model.SearchItem;
import com.health.search.service.MockSearchService;
import com.health.search.service.SearchService;
import com.health.search.util.InputValidator;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class HomeController implements Initializable {

  private static final Logger LOG = Logger.getLogger(HomeController.class.getName());

  @FXML private TextField searchField;
  @FXML private Label statusLabel;
  @FXML private Label resultsHeading;
  @FXML private ListView<String> resultsListView;

  private final SearchService searchService = new MockSearchService();

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  @FXML
  private void handleSearch() {
    String query = searchField.getText();
    String validationError = InputValidator.getValidationError(query);
    if (validationError != null) {
      showError(validationError);
      hideResults();
      return;
    }
    try {
      List<SearchItem> results = searchService.search(query.trim());
      displayResults(query.trim(), results);
    } catch (SearchException ex) {
      LOG.log(Level.WARNING, "Search failed", ex);
      showError("Search failed: " + ex.getMessage());
      hideResults();
    } catch (Exception ex) {
      LOG.log(Level.SEVERE, "Unexpected error", ex);
      showError("An unexpected error occurred. Please try again.");
      hideResults();
    }
  }

  @FXML
  private void handleClear() {
    searchField.clear();
    searchField.requestFocus();
    clearStatus();
    hideResults();
  }

  private void displayResults(String query, List<SearchItem> results) {
    if (results.isEmpty()) {
      showStatus("No results found for \"" + query + "\".");
      hideResults();
      return;
    }
    showSuccess("Found " + results.size() + " result(s) for \"" + query + "\":");
    List<String> displayItems = results.stream().map(SearchItem::getDisplayLabel).toList();
    resultsHeading.setText("Results for \"" + query + "\"");
    resultsListView.setItems(FXCollections.observableArrayList(displayItems));
    setVisible(resultsHeading, true);
    setVisible(resultsListView, true);
  }

  private void hideResults() {
    setVisible(resultsHeading, false);
    setVisible(resultsListView, false);
  }

  private void showStatus(String msg) {
    statusLabel.setText(msg);
    statusLabel.getStyleClass().removeAll("error", "success");
  }

  private void showError(String msg) {
    showStatus(msg);
    statusLabel.getStyleClass().add("error");
  }

  private void showSuccess(String msg) {
    showStatus(msg);
    statusLabel.getStyleClass().add("success");
  }

  private void clearStatus() {
    statusLabel.setText("");
    statusLabel.getStyleClass().removeAll("error", "success");
  }

  private void setVisible(Node n, boolean v) {
    n.setVisible(v);
    n.setManaged(v);
  }
}
