module com.villywebb.antline {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.villywebb.antline to javafx.fxml;
    exports com.villywebb.antline;
}