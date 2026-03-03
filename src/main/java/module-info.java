module com.health.search {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.health.search to javafx.fxml;
    exports com.health.search;
}