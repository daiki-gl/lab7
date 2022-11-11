module com.employee_app {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.employee_app to javafx.fxml;
    opens com.employee_app.home to javafx.base;
    exports com.employee_app;
}